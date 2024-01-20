
package com.bernardomg.association.fee.usecase;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.association.configuration.source.AssociationConfigurationSource;
import com.bernardomg.association.fee.domain.exception.MissingFeeIdException;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.model.FeePayment;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.infra.jpa.model.FeeEntity;
import com.bernardomg.association.fee.infra.jpa.model.FeePaymentEntity;
import com.bernardomg.association.fee.infra.jpa.model.MemberFeeEntity;
import com.bernardomg.association.fee.infra.jpa.repository.FeePaymentSpringRepository;
import com.bernardomg.association.fee.infra.jpa.repository.MemberFeeSpringRepository;
import com.bernardomg.association.fee.validation.CreateFeeValidator;
import com.bernardomg.association.member.domain.exception.MissingMemberIdException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.transaction.infra.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.infra.jpa.repository.TransactionSpringRepository;
import com.bernardomg.validation.Validator;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the fee service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Slf4j
public final class DefaultFeeService implements FeeService {

    private final AssociationConfigurationSource configurationSource;

    private final FeePaymentSpringRepository     feePaymentRepository;

    private final FeeRepository                  feeRepository;

    private final MemberFeeSpringRepository      memberFeeRepository;

    private final MemberRepository               memberRepository;

    private final MessageSource                  messageSource;

    private final TransactionSpringRepository    transactionRepository;

    private final Validator<FeePayment>          validatorPay;

    public DefaultFeeService(final MessageSource msgSource, final FeeRepository feeRepo,
            final TransactionSpringRepository transactionRepo, final MemberFeeSpringRepository memberFeeRepo,
            final FeePaymentSpringRepository feePaymentRepo, final MemberRepository memberRepo,
            final AssociationConfigurationSource confSource) {
        super();

        messageSource = msgSource;
        feeRepository = feeRepo;
        transactionRepository = transactionRepo;
        memberFeeRepository = memberFeeRepo;
        memberRepository = memberRepo;
        feePaymentRepository = feePaymentRepo;
        configurationSource = confSource;

        // TODO: Test validation
        validatorPay = new CreateFeeValidator(memberRepository, memberFeeRepository);
    }

    @Override
    public final void delete(final long memberNumber, final YearMonth date) {
        final boolean feeExists;
        final boolean memberExists;

        log.debug("Deleting fee for {} in {}", memberNumber, date);

        memberExists = memberRepository.exists(memberNumber);
        if (!memberExists) {
            // TODO: Change exception
            throw new MissingMemberIdException(memberNumber);
        }

        feeExists = feeRepository.exists(memberNumber, date);
        if (!feeExists) {
            throw new MissingFeeIdException(memberNumber + " " + date.toString());
        }

        feeRepository.delete(memberNumber, date);
    }

    @Override
    public final Iterable<Fee> getAll(final FeeQuery query, final Pageable pageable) {
        return feeRepository.findAll(query, pageable);
    }

    @Override
    public final Optional<Fee> getOne(final long memberNumber, final YearMonth date) {
        final boolean feeExists;
        final boolean memberExists;

        log.debug("Reading fee for {} in {}", memberNumber, date);

        memberExists = memberRepository.exists(memberNumber);
        if (!memberExists) {
            // TODO: Change exception
            throw new MissingMemberIdException(memberNumber);
        }

        feeExists = feeRepository.exists(memberNumber, date);
        if (!feeExists) {
            throw new MissingFeeIdException(memberNumber + " " + date.toString());
        }

        return feeRepository.findOne(memberNumber, date);
    }

    @Override
    public final Collection<Fee> payFees(final FeePayment payment) {
        final Collection<FeeEntity> fees;
        final Optional<Member>      member;
        final Collection<Long>      ids;
        final boolean               memberExists;

        log.debug("Paying fees for {} in {}. Months paid: {}", payment.getMember()
            .getNumber(),
            payment.getTransaction()
                .getDate(),
            payment.getFeeDates());

        memberExists = memberRepository.exists(payment.getMember()
            .getNumber());
        if (!memberExists) {
            // TODO: Change exception
            throw new MissingMemberIdException(payment.getMember()
                .getNumber());
        }

        validatorPay.validate(payment);

        member = memberRepository.findOne(payment.getMember()
            .getNumber());
        fees = feeRepository.save(payment.getMember()
            .getNumber(), payment.getFeeDates());
        registerTransaction(member.get(), fees, payment.getTransaction()
            .getDate(), payment.getFeeDates());

        // Read fees to return names
        ids = fees.stream()
            .map(FeeEntity::getId)
            .toList();
        return readAll(ids);
    }

    private final List<Fee> readAll(final Collection<Long> ids) {
        final List<MemberFeeEntity> found;

        found = memberFeeRepository.findAllById(ids);

        return found.stream()
            .map(this::toDto)
            .toList();
    }

    private final void registerTransaction(final Member member, final Collection<FeeEntity> fees,
            final LocalDate payDate, final Collection<YearMonth> feeDates) {
        final TransactionEntity          transaction;
        final Float                      feeAmount;
        final String                     name;
        final String                     dates;
        final String                     message;
        final Object[]                   messageArguments;
        final Long                       index;
        final Iterable<FeePaymentEntity> payments;

        // Calculate amount
        feeAmount = configurationSource.getFeeAmount() * feeDates.size();

        // Register transaction
        transaction = new TransactionEntity();
        transaction.setAmount(feeAmount);
        transaction.setDate(payDate);

        index = transactionRepository.findNextIndex();
        transaction.setIndex(index);

        name = member.getName()
            .getFullName();

        dates = feeDates.stream()
            .map(f -> messageSource.getMessage("fee.payment.month." + f.getMonthValue(), null,
                LocaleContextHolder.getLocale()) + " " + f.getYear())
            .collect(Collectors.joining(", "));

        messageArguments = List.of(name, dates)
            .toArray();
        message = messageSource.getMessage("fee.payment.message", messageArguments, LocaleContextHolder.getLocale());
        transaction.setDescription(message);

        transactionRepository.save(transaction);

        // Register payments
        payments = fees.stream()
            .map(FeeEntity::getId)
            .map(id -> FeePaymentEntity.builder()
                .feeId(id)
                .transactionId(transaction.getId())
                .build())
            .toList();
        feePaymentRepository.saveAll(payments);
    }

    private final Fee toDto(final MemberFeeEntity entity) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName(entity.getFullName())
            .number(entity.getMemberNumber())
            .build();
        transaction = FeeTransaction.builder()
            .index(entity.getTransactionIndex())
            .date(entity.getPaymentDate())
            .build();
        return Fee.builder()
            .date(entity.getDate())
            .paid(entity.getPaid())
            .member(member)
            .transaction(transaction)
            .build();
    }

}
