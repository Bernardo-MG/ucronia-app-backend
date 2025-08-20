/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

import java.time.Instant;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
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
import com.bernardomg.association.fee.domain.exception.MissingFeeException;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.usecase.validation.FeeDateNotExistingRule;
import com.bernardomg.association.fee.usecase.validation.FeeNoDuplicatedDatesRule;
import com.bernardomg.association.fee.usecase.validation.FeePaymentNotChangedRule;
import com.bernardomg.association.fee.usecase.validation.FeePersonNotChangedRule;
import com.bernardomg.association.fee.usecase.validation.PaidFeeMonthsNotExistingRule;
import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.model.Person;
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
    private static final Logger              log = LoggerFactory.getLogger(DefaultFeeService.class);

    private final EventEmitter               eventEmitter;

    private final FeeRepository              feeRepository;

    private final MessageSource              messageSource;

    private final PersonRepository           personRepository;

    private final AssociationSettingsSource  settingsSource;

    private final TransactionRepository      transactionRepository;

    private final Validator<Fee>             validatorCreate;

    private final Validator<Collection<Fee>> validatorPay;

    private final Validator<Fee>             validatorUpdate;

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
        validatorPay = new FieldRuleValidator<>(new FeeNoDuplicatedDatesRule(),
            new PaidFeeMonthsNotExistingRule(personRepository, feeRepository));

        // TODO: Test validation
        validatorCreate = new FieldRuleValidator<>(new FeeDateNotExistingRule(personRepository, feeRepository));
        validatorUpdate = new FieldRuleValidator<>(new FeePaymentNotChangedRule(feeRepository),
            new FeePersonNotChangedRule(feeRepository));
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
    public final Collection<Fee> payFees(final Collection<YearMonth> months, final Long personNumber,
            final Instant payDate) {
        final Collection<Fee> newFees;
        final Collection<Fee> fees;
        final Person          person;
        final Collection<Fee> created;

        log.info("Paying fees for {} for months {}, paid in {}", personNumber, months, payDate);

        person = personRepository.findOne(personNumber)
            .orElseThrow(() -> {
                log.error("Missing person {}", personNumber);
                throw new MissingPersonException(personNumber);
            });

        newFees = months.stream()
            .map(d -> toUnpaidFee(person, d))
            .toList();

        // TODO: reject paying in the future
        validatorPay.validate(newFees);

        fees = feeRepository.save(newFees);

        // TODO: why not create paid?
        pay(person, fees, payDate);

        // TODO: Why can't just return the created fees?
        created = feeRepository.findAllForPersonInDates(personNumber, months);

        // Send events for paid fees
        created.stream()
            .filter(Fee::paid)
            .forEach(this::sendFeePaidEvent);

        log.debug("Paid fees for {} for months {}, paid in {}: created", personNumber, months, payDate);

        return created;
    }

    @Override
    public final Fee update(final Fee fee) {
        final Fee         existing;
        final Transaction existingPayment;
        final Transaction updatedPayment;
        final Fee         saved;
        final Fee         updated;
        final Person      person;
        final Fee         toSave;

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

        if ((fee.payment()
            .isPresent())
                && (fee.payment()
                    .get()
                    .index() != null)
                && (!transactionRepository.exists(fee.payment()
                    .get()
                    .index()))) {
            log.error("Missing transaction {}", fee.payment()
                .get()
                .index());
            throw new MissingTransactionException(fee.payment()
                .get()
                .index());
        }

        validatorUpdate.validate(fee);

        if (addedPayment(fee)) {
            // Added payment
            toSave = new Fee(fee.month(), false, fee.member(), java.util.Optional.empty());
            saved = feeRepository.save(toSave);
            updated = pay(person, List.of(saved), fee.payment()
                .get()
                .date()).iterator()
                    .next();
        } else {
            if (changedPayment(fee, existing)) {
                // Changed payment date
                // Update transaction
                existingPayment = transactionRepository.findOne(fee.payment()
                    .get()
                    .index())
                    .get();
                updatedPayment = new Transaction(existingPayment.index(), fee.payment()
                    .get()
                    .date(), existingPayment.amount(), existingPayment.description());
                transactionRepository.save(updatedPayment);
            }
            updated = feeRepository.save(fee);
        }

        if ((updated.payment()
            .isPresent())
                && (existing.payment()
                    .isEmpty())) {
            // Added payment
            sendFeePaidEvent(updated);
        }

        return updated;
    }

    private final boolean addedPayment(final Fee received) {
        return ((received.payment()
            .isPresent())
                && (received.payment()
                    .get()
                    .index() == null));
    }

    private final boolean changedPayment(final Fee received, final Fee existing) {
        final Instant existingDate;
        final Instant receivedDate;
        final boolean changed;

        if (existing.payment()
            .isPresent()) {
            receivedDate = received.payment()
                .get()
                .date();
            existingDate = existing.payment()
                .get()
                .date();
            changed = !existingDate.equals(receivedDate);
        } else {
            changed = true;
        }

        return changed;
    }

    private final Collection<Fee> pay(final Person person, final Collection<Fee> fees, final Instant payDate) {
        final Transaction           payment;
        final Transaction           savedPayment;
        final Float                 feeAmount;
        final String                name;
        final String                dates;
        final String                message;
        final Object[]              messageArguments;
        final Long                  index;
        final Collection<YearMonth> feeMonths;
        final Collection<Fee>       paid;

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
        payment = new Transaction(index, payDate, feeAmount, message);

        savedPayment = transactionRepository.save(payment);

        paid = feeRepository.pay(person, fees, savedPayment);

        log.debug("Paid fee {} for {} with payment {}", person, fees, savedPayment);

        return paid;
    }

    private final void sendFeePaidEvent(final Fee fee) {
        eventEmitter.emit(new FeePaidEvent(fee, fee.month(), fee.member()
            .number()));
    }

    private final Fee toUnpaidFee(final Person person, final YearMonth date) {
        final Fee.Member feePerson;

        feePerson = new Fee.Member(person.number(), person.name());
        return Fee.unpaid(date, feePerson);
    }

}
