
package com.bernardomg.association.fee.usecase.service;

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

import com.bernardomg.association.configuration.usecase.AssociationConfigurationSource;
import com.bernardomg.association.fee.domain.exception.MissingFeeIdException;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.model.FeePayment;
import com.bernardomg.association.fee.domain.model.FeePaymentTransaction;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.usecase.validation.CreateFeeValidator;
import com.bernardomg.association.member.domain.exception.MissingMemberIdException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
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

    private final FeeRepository                  feeRepository;

    private final MemberRepository               memberRepository;

    private final MessageSource                  messageSource;

    private final TransactionRepository          transactionRepository;

    private final Validator<FeePayment>          validatorPay;

    public DefaultFeeService(final FeeRepository feeRepo, final MemberRepository memberRepo,
            final TransactionRepository transactionRepo, final AssociationConfigurationSource configSource,
            final MessageSource msgSource) {
        super();

        feeRepository = feeRepo;
        memberRepository = memberRepo;
        transactionRepository = transactionRepo;

        configurationSource = configSource;
        messageSource = msgSource;

        // TODO: Test validation
        validatorPay = new CreateFeeValidator(memberRepository, feeRepository);
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
        final Collection<Fee>  newFees;
        final Collection<Fee>  fees;
        final Optional<Member> member;

        log.debug("Paying fees for {} in {}. Months paid: {}", payment.getMember()
            .getNumber(),
            payment.getTransaction()
                .getDate(),
            payment.getFeeDates());

        member = memberRepository.findOne(payment.getMember()
            .getNumber());
        if (member.isEmpty()) {
            // TODO: Change exception
            throw new MissingMemberIdException(payment.getMember()
                .getNumber());
        }

        validatorPay.validate(payment);

        newFees = payment.getFeeDates()
            .stream()
            .map(d -> toFee(member.get(), payment.getTransaction(), d))
            .toList();
        fees = feeRepository.save(newFees);

        pay(member.get(), fees, payment.getTransaction()
            .getDate());

        return feeRepository.findAll(payment.getMember()
            .getNumber(), payment.getFeeDates());
    }

    private final void pay(final Member member, final Collection<Fee> fees, final LocalDate payDate) {
        final Transaction           transaction;
        final Transaction           savedTransaction;
        final Float                 feeAmount;
        final String                name;
        final String                dates;
        final String                message;
        final Object[]              messageArguments;
        final Long                  index;
        final Collection<YearMonth> feeDates;

        feeDates = fees.stream()
            .map(Fee::getDate)
            .toList();

        // Calculate amount
        feeAmount = configurationSource.getFeeAmount() * feeDates.size();

        // Register transaction
        index = transactionRepository.findNextIndex();

        name = member.getName()
            .getFullName();

        dates = feeDates.stream()
            .map(f -> messageSource.getMessage("fee.payment.month." + f.getMonthValue(), null,
                LocaleContextHolder.getLocale()) + " " + f.getYear())
            .collect(Collectors.joining(", "));

        messageArguments = List.of(name, dates)
            .toArray();
        message = messageSource.getMessage("fee.payment.message", messageArguments, LocaleContextHolder.getLocale());

        transaction = Transaction.builder()
            .withAmount(feeAmount)
            .withDate(payDate)
            .withDescription(message)
            .withIndex(index)
            .build();

        savedTransaction = transactionRepository.save(transaction);

        feeRepository.pay(member, fees, savedTransaction);
    }

    private final Fee toFee(final Member member, final FeePaymentTransaction transaction, final YearMonth date) {
        final FeeMember      feeMember;
        final FeeTransaction feeTransaction;

        feeMember = FeeMember.builder()
            // TODO
            .withFullName(null)
            .withNumber(member.getNumber())
            .build();
        feeTransaction = FeeTransaction.builder()
            .withDate(transaction.getDate())
            .withIndex(null)
            .build();
        return Fee.builder()
            .withMember(feeMember)
            .withTransaction(feeTransaction)
            .withDate(date)
            .withPaid(false)
            .build();
    }

}
