/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.fee.usecase.service;

import java.text.Normalizer;
import java.time.Instant;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.event.domain.FeeDeletedEvent;
import com.bernardomg.association.event.domain.FeePaidEvent;
import com.bernardomg.association.fee.domain.dto.FeePayments;
import com.bernardomg.association.fee.domain.exception.MissingFeeException;
import com.bernardomg.association.fee.domain.exception.MissingFeeTypeException;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.model.YearsRange;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.fee.usecase.validation.FeeFeeTypeNotChangedRule;
import com.bernardomg.association.fee.usecase.validation.FeeMonthNotExistingRule;
import com.bernardomg.association.fee.usecase.validation.FeeNotPaidInFutureRule;
import com.bernardomg.association.fee.usecase.validation.FeePaymentsMonthsNotExistingRule;
import com.bernardomg.association.fee.usecase.validation.FeePaymentsNoDuplicatedMonthsRule;
import com.bernardomg.association.fee.usecase.validation.FeePaymentsNotPaidInFutureRule;
import com.bernardomg.association.fee.usecase.validation.FeeTransactionNotChangedRule;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

/**
 * Default implementation of the fee service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Service
@Transactional
public final class DefaultFeeService implements FeeService {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(DefaultFeeService.class);

    private final EventEmitter            eventEmitter;

    private final FeeRepository           feeRepository;

    private final FeeTypeRepository       feeTypeRepository;

    private final MemberProfileRepository memberProfileRepository;

    private final MessageSource           messageSource;

    private final TransactionRepository   transactionRepository;

    private final Validator<Fee>          validatorCreate;

    private final Validator<FeePayments>  validatorPay;

    private final Validator<Fee>          validatorUpdate;

    public DefaultFeeService(final FeeRepository feeRepo, final FeeTypeRepository feeTypeRepo,
            final MemberProfileRepository memberProfileRepo, final TransactionRepository transactionRepo,
            final EventEmitter evntEmitter, final MessageSource msgSource) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        feeTypeRepository = Objects.requireNonNull(feeTypeRepo);
        memberProfileRepository = Objects.requireNonNull(memberProfileRepo);
        transactionRepository = Objects.requireNonNull(transactionRepo);
        eventEmitter = Objects.requireNonNull(evntEmitter);

        messageSource = Objects.requireNonNull(msgSource);

        // TODO: Test validation
        validatorPay = new FieldRuleValidator<>(new FeePaymentsNoDuplicatedMonthsRule(),
            new FeePaymentsNotPaidInFutureRule(),
            new FeePaymentsMonthsNotExistingRule(memberProfileRepository, feeRepository));

        // TODO: Test validation
        validatorCreate = new FieldRuleValidator<>(new FeeMonthNotExistingRule(memberProfileRepository, feeRepository));
        validatorUpdate = new FieldRuleValidator<>(new FeeNotPaidInFutureRule(),
            new FeeTransactionNotChangedRule(feeRepository), new FeeFeeTypeNotChangedRule(feeRepository));
    }

    @Override
    public final Fee createFee(final YearMonth date, final Long number) {
        final Fee           newFee;
        final Fee           created;
        final MemberProfile member;
        final FeeType       feeType;
        final Fee.FeeType   feeFeeType;

        log.info("Creating unpaid fee for {} for month {}", number, date);

        member = memberProfileRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing member {}", number);
                throw new MissingMemberException(number);
            });
        feeType = feeTypeRepository.findOne(member.feeType()
            .number())
            .orElseThrow(() -> {
                log.error("Missing fee type {}", member.feeType()
                    .number());
                throw new MissingFeeTypeException(member.feeType()
                    .number());
            });

        feeFeeType = new Fee.FeeType(member.feeType()
            .number(),
            member.feeType()
                .name(),
            member.feeType()
                .amount());

        if (feeType.amount() == 0) {
            // No amount
            // Set to paid automatically
            newFee = Fee.paid(date, member.number(), member.name(), feeFeeType);
        } else {
            newFee = Fee.unpaid(date, member.number(), member.name(), feeFeeType);
        }

        validatorCreate.validate(newFee);

        created = feeRepository.save(newFee);

        log.info("Created unpaid fee for {} for month {}", number, date);

        return created;
    }

    @Override
    public final Fee delete(final long number, final YearMonth date) {
        final Fee fee;

        log.info("Deleting fee for {} in {}", number, date);

        fee = feeRepository.findOne(number, date)
            .orElseThrow(() -> {
                log.error("Missing fee for {} in {}", number, date);
                throw new MissingFeeException(number, date);
            });

        feeRepository.delete(number, date);

        // Send events for deleted fees
        eventEmitter.emit(new FeeDeletedEvent(fee, date, number));

        log.info("Deleted fee for {} in {}", number, date);

        return fee;
    }

    @Override
    public final Page<Fee> getAll(final FeeQuery query, final Pagination pagination, final Sorting sorting) {
        final Page<Fee> fees;

        log.info("Getting all fees with query {}, pagination {} and sorting {}", query, pagination, sorting);

        fees = feeRepository.findAll(query, pagination, sorting);

        log.debug("Got all fees with query {}, pagination {} and sorting {}: {}", query, pagination, sorting, fees);

        return fees;
    }

    @Override
    public final Collection<MemberFees> getForYear(final Year year, final MemberStatus status, final Sorting sorting) {
        final Collection<Fee>        readFees;
        final Map<Object, List<Fee>> memberFees;
        final Collection<MemberFees> calendarFees;
        final Collection<MemberFees> sortedCalendarFees;
        final Collection<Long>       memberNumbers;
        final Comparator<MemberFees> feeCalendarComparator;
        List<Fee>                    fees;
        MemberFees                   calendarFee;
        Collection<MemberFees.Fee>   membFees;
        ProfileName                  name;

        log.info("Getting fee calendar for year {} and status {}", year, status);

        // Select query based on status
        readFees = switch (status) {
            case ACTIVE -> feeRepository.findAllInYearForActiveMembers(year, sorting);
            case INACTIVE -> feeRepository.findAllInYearForInactiveMembers(year, sorting);
            default -> feeRepository.findAllInYear(year, sorting);
        };

        log.debug("Read fees: {}", readFees);

        // Member fees grouped by member number
        memberFees = readFees.stream()
            .collect(Collectors.groupingBy(f -> f.member()
                .number()));
        log.debug("Member fees: {}", memberFees);

        memberNumbers = readFees.stream()
            .map(Fee::member)
            .map(Fee.Member::number)
            .distinct()
            .sorted()
            .toList();
        log.debug("Member numbers: {}", memberNumbers);

        calendarFees = new ArrayList<>();
        for (final Long memberNumber : memberNumbers) {
            fees = memberFees.get(memberNumber);
            membFees = fees.stream()
                .map(this::toMemberFee)
                .sorted(Comparator.comparing(MemberFees.Fee::month))
                .toList();
            name = fees.iterator()
                .next()
                .member()
                .name();
            calendarFee = toFeeYear(memberNumber, name, status, membFees);
            calendarFees.add(calendarFee);
        }
        feeCalendarComparator = Comparator.comparing(fc -> normalizeString(fc.member()
            .name()
            .fullName()));
        sortedCalendarFees = calendarFees.stream()
            .sorted(feeCalendarComparator)
            .collect(Collectors.toList());

        log.debug("Got fee calendar for year {} and status {}: {}", year, status, sortedCalendarFees);

        return sortedCalendarFees;
    }

    @Override
    public final Optional<Fee> getOne(final long number, final YearMonth date) {
        final Optional<Fee> fee;

        log.info("Getting fee for {} in {}", number, date);

        fee = feeRepository.findOne(number, date);
        if (fee.isEmpty()) {
            log.error("Missing fee for {} in {}", number, date);
            throw new MissingFeeException(number, date);
        }

        log.debug("Got fee for {} in {}: {}", number, date, fee);

        return fee;
    }

    @Override
    public final YearsRange getRange() {
        final YearsRange range;

        log.info("Getting fee calendar range");

        range = feeRepository.findRange();

        log.debug("Got fee calendar range: {}", range);

        return range;
    }

    @Override
    public final Collection<Fee> payFees(final FeePayments feesPayments) {
        final Collection<Fee> feesToSave;
        final MemberProfile   member;
        final Collection<Fee> created;
        final Transaction     transaction;

        log.info("Paying fees for {} for months {}, paid in {}", feesPayments.member(), feesPayments.months(),
            feesPayments.paymentDate());

        member = memberProfileRepository.findOne(feesPayments.member())
            .orElseThrow(() -> {
                log.error("Missing member {}", feesPayments.member());
                throw new MissingMemberException(feesPayments.member());
            });

        validatorPay.validate(feesPayments);

        transaction = savePaymentTransaction(member, feesPayments.months(), feesPayments.paymentDate());

        feesToSave = feesPayments.months()
            .stream()
            .map(month -> toPaidFee(member.feeType(), member, month, transaction))
            .toList();

        created = feeRepository.save(feesToSave);

        // Send events for paid fees
        created.stream()
            .filter(Fee::paid)
            .forEach(this::sendFeePaidEvent);

        log.debug("Paid fees for {} for months {}, paid in {}: {}", feesPayments.member(), feesPayments.months(),
            feesPayments.paymentDate(), created);

        return created;
    }

    @Override
    public final Fee update(final Fee fee) {
        final Fee           existing;
        final Transaction   existingPayment;
        final Transaction   updatedPayment;
        final Fee           updated;
        final MemberProfile member;
        final Fee           toSave;
        final Transaction   transaction;

        log.debug("Updating fee for {} in {} using data {}", fee.member()
            .number(), fee.month(), fee);

        existing = feeRepository.findOne(fee.member()
            .number(), fee.month())
            .orElseThrow(() -> {
                log.error("Missing fee for {} in {}", fee.member()
                    .number(), fee.month());
                throw new MissingFeeException(fee.member()
                    .number(), fee.month());
            });

        member = memberProfileRepository.findOne(fee.member()
            .number())
            .orElseThrow(() -> {
                log.error("Missing member {}", fee.member()
                    .number());
                throw new MissingMemberException(fee.member()
                    .number());
            });

        validatorUpdate.validate(fee);

        if (addedPayment(fee)) {
            // Added payment
            transaction = savePaymentTransaction(member, List.of(fee.month()), fee.transaction()
                .get()
                .date());
            toSave = toPaidFee(fee.feeType(), member, fee.month(), transaction);
        } else {
            if (changedPayment(fee, existing)) {
                // Changed payment date
                // Update transaction
                existingPayment = transactionRepository.findOne(fee.transaction()
                    .get()
                    .index())
                    .get();
                updatedPayment = new Transaction(existingPayment.index(), fee.transaction()
                    .get()
                    .date(), existingPayment.amount(), existingPayment.description());
                transactionRepository.save(updatedPayment);
            }
            toSave = fee;
        }
        updated = feeRepository.save(copyToUpdate(toSave, existing));

        if ((updated.transaction()
            .isPresent())
                && (existing.transaction()
                    .isEmpty())) {
            // Added payment
            sendFeePaidEvent(updated);
        }

        return updated;
    }

    private final boolean addedPayment(final Fee received) {
        return ((received.transaction()
            .isPresent())
                && (received.transaction()
                    .get()
                    .index() == null));
    }

    private final boolean changedPayment(final Fee received, final Fee existing) {
        final Instant existingDate;
        final Instant receivedDate;
        final boolean changed;

        if (existing.transaction()
            .isPresent()) {
            receivedDate = received.transaction()
                .get()
                .date();
            existingDate = existing.transaction()
                .get()
                .date();
            changed = !existingDate.equals(receivedDate);
        } else {
            changed = true;
        }

        return changed;
    }

    private final Fee copyToUpdate(final Fee fee, final Fee original) {
        final Optional<Fee.Transaction> transaction;
        final Fee.Transaction           existingTransaction;

        if (original.transaction()
            .isPresent()) {
            existingTransaction = original.transaction()
                .get();
            transaction = Optional.of(new Fee.Transaction(existingTransaction.index(), fee.transaction()
                .get()
                .date()));
        } else if (fee.transaction()
            .isPresent()) {
            transaction = Optional.of(new Fee.Transaction(-1L, fee.transaction()
                .get()
                .date()));
        } else {
            transaction = Optional.empty();
        }

        return new Fee(fee.month(), fee.paid(), fee.member(), original.feeType(), transaction);
    }

    private final String normalizeString(final String input) {
        // TODO: test this
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "");
    }

    private final Transaction savePaymentTransaction(final MemberProfile member, final Collection<YearMonth> feeMonths,
            final Instant payDate) {
        final Transaction transaction;
        final Float       feeAmount;
        final String      name;
        final String      dates;
        final String      message;
        final Object[]    messageArguments;
        final Long        index;
        final FeeType     feeType;

        // Calculate amount
        feeType = feeTypeRepository.findOne(member.feeType()
            .number())
            .orElseThrow(() -> {
                log.error("Missing fee type {}", member.feeType()
                    .number());
                throw new MissingFeeTypeException(member.feeType()
                    .number());
            });
        feeAmount = feeType.amount() * feeMonths.size();

        name = member.name()
            .fullName();

        dates = feeMonths.stream()
            .map(f -> messageSource.getMessage("fee.payment.month." + f.getMonthValue(), null,
                LocaleContextHolder.getLocale()) + " " + f.getYear())
            .collect(Collectors.joining(", "));

        messageArguments = List.of(name, dates)
            .toArray();
        message = messageSource.getMessage("fee.payment.message", messageArguments, LocaleContextHolder.getLocale());

        // Register payment
        index = transactionRepository.findNextIndex();
        transaction = new Transaction(index, payDate, feeAmount, message);

        return transactionRepository.save(transaction);
    }

    private final void sendFeePaidEvent(final Fee fee) {
        eventEmitter.emit(new FeePaidEvent(fee, fee.month(), fee.member()
            .number()));
    }

    private final MemberFees toFeeYear(final Long number, final ProfileName name, final MemberStatus status,
            final Collection<MemberFees.Fee> fees) {
        final boolean           active;
        final MemberFees.Member member;

        active = switch (status) {
            case ACTIVE -> true;
            case INACTIVE -> false;
            // TODO: get all active in a single query
            default -> memberProfileRepository.isActive(number);
        };

        member = new MemberFees.Member(number, name, active);
        return new MemberFees(member, fees);
    }

    private final MemberFees.Fee toMemberFee(final Fee fee) {
        return new MemberFees.Fee(fee.month(), fee.paid());
    }

    private final Fee toPaidFee(final Fee.FeeType feeFeeType, final MemberProfile member, final YearMonth month,
            final Transaction transaction) {
        final Fee.FeeType     feeType;
        final Fee.Transaction feeTransaction;

        feeType = new Fee.FeeType(feeFeeType.number(), feeFeeType.name(), feeFeeType.amount());
        feeTransaction = new Fee.Transaction(transaction.index(), transaction.date());
        return Fee.paid(month, member.number(), member.name(), feeType, feeTransaction);
    }

    private final Fee toPaidFee(final MemberProfile.FeeType memberFeeType, final MemberProfile member,
            final YearMonth month, final Transaction transaction) {
        final Fee.FeeType     feeType;
        final Fee.Transaction feeTransaction;

        feeType = new Fee.FeeType(memberFeeType.number(), memberFeeType.name(), memberFeeType.amount());
        feeTransaction = new Fee.Transaction(transaction.index(), transaction.date());
        return Fee.paid(month, member.number(), member.name(), feeType, feeTransaction);
    }

}
