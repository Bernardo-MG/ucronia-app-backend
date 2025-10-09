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
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.model.YearsRange;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.usecase.validation.FeeMemberNotChangedRule;
import com.bernardomg.association.fee.usecase.validation.FeeMonthNotExistingRule;
import com.bernardomg.association.fee.usecase.validation.FeeNotPaidInFutureRule;
import com.bernardomg.association.fee.usecase.validation.FeePaymentsMonthsNotExistingRule;
import com.bernardomg.association.fee.usecase.validation.FeePaymentsNoDuplicatedMonthsRule;
import com.bernardomg.association.fee.usecase.validation.FeePaymentsNotPaidInFutureRule;
import com.bernardomg.association.fee.usecase.validation.FeeTransactionNotChangedRule;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.settings.usecase.source.AssociationSettingsSource;
import com.bernardomg.association.transaction.domain.exception.MissingTransactionException;
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
    private static final Logger             log = LoggerFactory.getLogger(DefaultFeeService.class);

    private final EventEmitter              eventEmitter;

    private final FeeRepository             feeRepository;

    private final MessageSource             messageSource;

    private final PersonRepository          personRepository;

    private final AssociationSettingsSource settingsSource;

    private final TransactionRepository     transactionRepository;

    private final Validator<Fee>            validatorCreate;

    private final Validator<FeePayments>    validatorPay;

    private final Validator<Fee>            validatorUpdate;

    public DefaultFeeService(final FeeRepository feeRepo, final PersonRepository personRepo,
            final TransactionRepository transactionRepo, final EventEmitter evntEmitter,
            final AssociationSettingsSource configSource, final MessageSource msgSource) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        personRepository = Objects.requireNonNull(personRepo);
        transactionRepository = Objects.requireNonNull(transactionRepo);
        eventEmitter = Objects.requireNonNull(evntEmitter);

        settingsSource = Objects.requireNonNull(configSource);
        messageSource = Objects.requireNonNull(msgSource);

        // TODO: Test validation
        validatorPay = new FieldRuleValidator<>(new FeePaymentsNoDuplicatedMonthsRule(),
            new FeePaymentsNotPaidInFutureRule(),
            new FeePaymentsMonthsNotExistingRule(personRepository, feeRepository));

        // TODO: Test validation
        validatorCreate = new FieldRuleValidator<>(new FeeMonthNotExistingRule(personRepository, feeRepository));
        validatorUpdate = new FieldRuleValidator<>(new FeeNotPaidInFutureRule(),
            new FeeTransactionNotChangedRule(feeRepository), new FeeMemberNotChangedRule(feeRepository));
    }

    @Override
    public final Fee createUnpaidFee(final YearMonth feeDate, final Long personNumber) {
        final Fee    newFee;
        final Fee    fee;
        final Person person;

        log.info("Creating unpaid fee for {} for month {}", personNumber, feeDate);

        person = personRepository.findOne(personNumber)
            .orElseThrow(() -> {
                log.error("Missing person {}", personNumber);
                throw new MissingPersonException(personNumber);
            });

        newFee = toUnpaidFee(person, feeDate);

        validatorCreate.validate(newFee);

        fee = feeRepository.save(newFee);

        log.info("Created unpaid fee for {} for month {}", personNumber, feeDate);

        return fee;
    }

    @Override
    public final Fee delete(final long personNumber, final YearMonth date) {
        final boolean personExists;
        final Fee     fee;

        log.info("Deleting fee for {} in {}", personNumber, date);

        personExists = personRepository.exists(personNumber);
        if (!personExists) {
            log.error("Missing person {}", personNumber);
            throw new MissingPersonException(personNumber);
        }

        fee = feeRepository.findOne(personNumber, date)
            .orElseThrow(() -> {
                log.error("Missing fee for {} in {}", personNumber, date);
                throw new MissingFeeException(personNumber, date);
            });

        feeRepository.delete(personNumber, date);

        // Send events for deleted fees
        eventEmitter.emit(new FeeDeletedEvent(fee, date, personNumber));

        return fee;
    }

    @Override
    public final Page<Fee> getAll(final FeeQuery query, final Pagination pagination, final Sorting sorting) {
        final Page<Fee> fees;

        log.info("Getting all fees with query {}", query);

        fees = feeRepository.findAll(query, pagination, sorting);

        log.debug("Got all fees with query {}: {}", query, fees);

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
        PersonName                   name;

        log.info("Getting fee calendar for year {} and status {}", year, status);

        // Select query based on status
        readFees = switch (status) {
            case ACTIVE -> feeRepository.findAllInYearForActiveMembers(year, sorting);
            case INACTIVE -> feeRepository.findAllInYearForInactiveMembers(year, sorting);
            default -> feeRepository.findAllInYear(year, sorting);
        };

        log.debug("Read fees: {}", readFees);

        // Member fees grouped by id
        memberFees = readFees.stream()
            .collect(Collectors.groupingBy(f -> f.member()
                .number()));
        log.debug("Member fees: {}", memberFees);

        // Sorted ids
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
    public final Optional<Fee> getOne(final long personNumber, final YearMonth date) {
        final boolean       personExists;
        final Optional<Fee> fee;

        log.info("Getting fee for {} in {}", personNumber, date);

        personExists = personRepository.exists(personNumber);
        if (!personExists) {
            log.error("Missing person {}", personNumber);
            throw new MissingPersonException(personNumber);
        }

        fee = feeRepository.findOne(personNumber, date);
        if (fee.isEmpty()) {
            log.error("Missing fee for {} in {}", personNumber, date);
            throw new MissingFeeException(personNumber, date);
        }

        log.debug("Got fee for {} in {}: {}", personNumber, date, fee);

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
        final Collection<Fee> newFees;
        final Collection<Fee> feesToSave;
        final Person          person;
        final Collection<Fee> created;
        final Transaction     transaction;

        log.info("Paying fees for {} for months {}, paid in {}", feesPayments.member(), feesPayments.months(),
            feesPayments.paymentDate());

        person = personRepository.findOne(feesPayments.member())
            .orElseThrow(() -> {
                log.error("Missing person {}", feesPayments.member());
                throw new MissingPersonException(feesPayments.member());
            });

        validatorPay.validate(feesPayments);

        newFees = feesPayments.months()
            .stream()
            .map(d -> toUnpaidFee(person, d))
            .toList();

        transaction = savePaymentTransaction(person, newFees, feesPayments.paymentDate());

        feesToSave = feesPayments.months()
            .stream()
            .map(month -> toPaidFee(person, month, transaction))
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
        final Fee         existing;
        final Transaction existingPayment;
        final Transaction updatedPayment;
        final Fee         updated;
        final Person      person;
        final Fee         toSave;
        final Transaction transaction;

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

        person = personRepository.findOne(fee.member()
            .number())
            .orElseThrow(() -> {
                log.error("Missing person {}", fee.member()
                    .number());
                throw new MissingPersonException(fee.member()
                    .number());
            });

        if ((fee.transaction()
            .isPresent())
                && (fee.transaction()
                    .get()
                    .index() != null)
                && (!transactionRepository.exists(fee.transaction()
                    .get()
                    .index()))) {
            log.error("Missing transaction {}", fee.transaction()
                .get()
                .index());
            throw new MissingTransactionException(fee.transaction()
                .get()
                .index());
        }

        validatorUpdate.validate(fee);

        if (addedPayment(fee)) {
            // Added payment
            transaction = savePaymentTransaction(person, List.of(fee), fee.transaction()
                .get()
                .date());
            toSave = toPaidFee(person, fee.month(), transaction);
            updated = feeRepository.save(toSave);
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
            updated = feeRepository.save(fee);
        }

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

    private final String normalizeString(final String input) {
        // TODO: test this
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "");
    }

    private final Transaction savePaymentTransaction(final Person person, final Collection<Fee> fees,
            final Instant payDate) {
        final Transaction           transaction;
        final Float                 feeAmount;
        final String                name;
        final String                dates;
        final String                message;
        final Object[]              messageArguments;
        final Long                  index;
        final Collection<YearMonth> feeMonths;

        feeMonths = fees.stream()
            .map(Fee::month)
            .toList();

        // Calculate amount
        feeAmount = settingsSource.getFeeAmount() * feeMonths.size();

        name = person.name()
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

    private final MemberFees toFeeYear(final Long personNumber, final PersonName name, final MemberStatus status,
            final Collection<MemberFees.Fee> fees) {
        final boolean           active;
        final MemberFees.Member person;

        active = switch (status) {
            case ACTIVE -> true;
            case INACTIVE -> false;
            // TODO: get all active in a single query
            default -> personRepository.isActive(personNumber);
        };

        person = new MemberFees.Member(personNumber, name, active);
        return new MemberFees(person, fees);
    }

    private final MemberFees.Fee toMemberFee(final Fee fee) {
        return new MemberFees.Fee(fee.month(), fee.paid());
    }

    private final Fee toPaidFee(final Person person, final YearMonth month, final Transaction transaction) {
        final Fee.Member      feePerson;
        final Fee.Transaction feeTransaction;

        feePerson = new Fee.Member(person.number(), person.name());
        feeTransaction = new Fee.Transaction(transaction.date(), transaction.index());
        return Fee.paid(month, feePerson, feeTransaction);
    }

    private final Fee toUnpaidFee(final Person person, final YearMonth date) {
        final Fee.Member feePerson;

        feePerson = new Fee.Member(person.number(), person.name());
        return Fee.unpaid(date, feePerson);
    }

}
