
package com.bernardomg.association.membership.fee.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bernardomg.association.configuration.source.AssociationConfigurationSource;
import com.bernardomg.association.funds.transaction.persistence.model.TransactionEntity;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.membership.fee.exception.MissingFeeIdException;
import com.bernardomg.association.membership.fee.model.FeeQuery;
import com.bernardomg.association.membership.fee.model.FeeUpdate;
import com.bernardomg.association.membership.fee.model.FeesPayment;
import com.bernardomg.association.membership.fee.model.MemberFee;
import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.model.FeePaymentEntity;
import com.bernardomg.association.membership.fee.persistence.model.MemberFeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.FeePaymentRepository;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.fee.persistence.repository.MemberFeeRepository;
import com.bernardomg.association.membership.fee.persistence.repository.MemberFeeSpecifications;
import com.bernardomg.association.membership.fee.validation.CreateFeeValidator;
import com.bernardomg.association.membership.member.existence.MissingMemberIdException;
import com.bernardomg.association.membership.member.persistence.model.MemberEntity;
import com.bernardomg.association.membership.member.persistence.repository.MemberRepository;
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

    private final FeePaymentRepository           feePaymentRepository;

    private final FeeRepository                  feeRepository;

    private final MemberFeeRepository            memberFeeRepository;

    private final MemberRepository               memberRepository;

    private final MessageSource                  messageSource;

    private final TransactionRepository          transactionRepository;

    private final Validator<FeesPayment>         validatorPay;

    public DefaultFeeService(final MessageSource msgSource, final FeeRepository feeRepo,
            final TransactionRepository transactionRepo, final MemberFeeRepository memberFeeRepo,
            final FeePaymentRepository feePaymentRepo, final MemberRepository memberRepo,
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
        validatorPay = new CreateFeeValidator(feeRepository, memberRepository);
    }

    @Override
    public final void delete(final long memberId, final YearMonth date) {
        final Optional<FeeEntity> fee;

        log.debug("Deleting fee for {} in {}", memberId, date);

        fee = feeRepository.findOneByMemberIdAndDate(memberId, date);

        if (fee.isEmpty()) {
            throw new MissingFeeIdException(memberId + " " + date.toString());
        }

        feeRepository.deleteById(fee.get()
            .getId());
    }

    @Override
    public final Iterable<MemberFee> getAll(final FeeQuery query, final Pageable pageable) {
        final Page<MemberFeeEntity>                    page;
        final Optional<Specification<MemberFeeEntity>> spec;
        // TODO: Test reading with no name or surname

        log.debug("Reading fees with sample {} and pagination {}", query, pageable);

        spec = MemberFeeSpecifications.fromQuery(query);

        if (spec.isEmpty()) {
            page = memberFeeRepository.findAll(pageable);
        } else {
            page = memberFeeRepository.findAll(spec.get(), pageable);
        }

        return page.map(this::toDto);
    }

    @Override
    public final Optional<MemberFee> getOne(final long memberId, final YearMonth date) {
        final Optional<MemberFeeEntity> found;
        final Optional<FeeEntity>       fee;

        log.debug("Reading fee for {} in {}", memberId, date);

        fee = feeRepository.findOneByMemberIdAndDate(memberId, date);

        if (fee.isEmpty()) {
            throw new MissingFeeIdException(memberId + " " + date.toString());
        }

        found = memberFeeRepository.findById(fee.get()
            .getId());

        return found.map(this::toDto);
    }

    @Override
    public final Collection<MemberFee> payFees(final long memberNumber, final LocalDate payDate,
            final Collection<YearMonth> feeDates) {
        final Collection<FeeEntity>  fees;
        final Optional<MemberEntity> member;
        final Collection<Long>       ids;
        final FeesPayment            payment;

        log.debug("Paying fees for {} in {}. Months paid: {}", memberNumber, payDate, feeDates);

        member = memberRepository.findByNumber(memberNumber);
        if (member.isEmpty()) {
            // TODO: Change exception
            throw new MissingMemberIdException(memberNumber);
        }

        payment = FeesPayment.builder()
            .memberNumber(memberNumber)
            .feeDates(feeDates)
            .build();
        validatorPay.validate(payment);

        fees = registerFees(memberNumber, feeDates);
        registerTransaction(member.get(), fees, payDate, feeDates);

        // Read fees to return names
        feeRepository.flush();
        ids = fees.stream()
            .map(FeeEntity::getId)
            .toList();
        return readAll(ids);
    }

    @Override
    public final MemberFee update(final long memberNumber, final YearMonth date, final FeeUpdate fee) {
        final Optional<FeeEntity>    found;
        final Optional<MemberEntity> member;
        final FeeEntity              entity;
        final FeeEntity              updated;
        final Optional<MemberFee>    read;
        final MemberFee              result;

        log.debug("Updating fee for {} in {} using data {}", memberNumber, date, fee);

        member = memberRepository.findByNumber(memberNumber);
        if (member.isEmpty()) {
            // TODO: Change exception
            throw new MissingMemberIdException(memberNumber);
        }

        found = feeRepository.findOneByMemberIdAndDate(member.get()
            .getId(), date);
        if (found.isEmpty()) {
            throw new MissingFeeIdException(memberNumber + " " + date.toString());
        }

        entity = toEntity(fee);
        entity.setId(found.get()
            .getId());
        entity.setMemberId(member.get()
            .getId());
        entity.setDate(date);

        updated = feeRepository.save(entity);

        // Read updated fee with name
        // FIXME: read directly from the repository
        read = getOne(updated.getMemberId(), updated.getDate());
        if (read.isPresent()) {
            result = read.get();
        } else {
            result = MemberFee.builder()
                .build();
        }

        return result;
    }

    private final void loadId(final FeeEntity fee) {
        final Long                id;
        final Optional<FeeEntity> read;

        read = feeRepository.findOneByMemberIdAndDate(fee.getMemberId(), fee.getDate());
        if (read.isPresent()) {
            id = read.get()
                .getId();
            fee.setId(id);
        }
    }

    private final List<MemberFee> readAll(final Collection<Long> ids) {
        final List<MemberFeeEntity> found;

        found = memberFeeRepository.findAllById(ids);

        return found.stream()
            .map(this::toDto)
            .toList();
    }

    private final Collection<FeeEntity> registerFees(final Long memberId, final Collection<YearMonth> feeDates) {
        final Collection<FeeEntity>          fees;
        final Function<YearMonth, FeeEntity> toPersistentFee;

        // Register fees
        toPersistentFee = (date) -> toPersistentFee(memberId, date);
        fees = feeDates.stream()
            .map(toPersistentFee)
            .toList();

        // Update fees on fees to update
        fees.stream()
            .forEach(this::loadId);

        return feeRepository.saveAll(fees);
    }

    private final void registerTransaction(final MemberEntity member, final Collection<FeeEntity> fees,
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

        name = List.of(member.getName(), member.getSurname())
            .stream()
            .collect(Collectors.joining(" "))
            .trim();

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

    private final MemberFee toDto(final MemberFeeEntity entity) {
        return MemberFee.builder()
            .memberNumber(entity.getMemberId())
            .memberName(entity.getMemberName())
            .date(entity.getDate())
            .paid(entity.getPaid())
            .build();
    }

    private final FeeEntity toEntity(final FeeUpdate update) {
        return FeeEntity.builder()
            .build();
    }

    private final FeeEntity toPersistentFee(final Long memberNumber, final YearMonth date) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(memberNumber);
        fee.setDate(date);

        return fee;
    }

}
