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

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.bernardomg.association.fee.usecase.validation.PaidFeeDatesNotExistingRule;
import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.settings.usecase.source.AssociationSettingsSource;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the fee service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Slf4j
@Service
@Transactional
public final class DefaultFeeService implements FeeService {

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
            new PaidFeeDatesNotExistingRule(personRepository, feeRepository));

        // TODO: Test validation
        validatorCreate = new FieldRuleValidator<>(new FeeDateNotExistingRule(personRepository, feeRepository));
        validatorUpdate = new FieldRuleValidator<>();
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
    public final void delete(final long personNumber, final YearMonth date) {
        final boolean memberExists;
        final Fee     fee;

        log.info("Deleting fee for {} in {}", personNumber, date);

        memberExists = personRepository.exists(personNumber);
        if (!memberExists) {
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
    }

    @Override
    public final Iterable<Fee> getAll(final FeeQuery query, final Pagination pagination, final Sorting sorting) {
        final Iterable<Fee> fees;

        log.info("Getting all fees with query {}", query);

        fees = feeRepository.findAll(query, pagination, sorting);

        log.debug("Got all fees with query {}: {}", query, fees);

        return fees;
    }

    @Override
    public final Optional<Fee> getOne(final long personNumber, final YearMonth date) {
        final boolean       memberExists;
        final Optional<Fee> fee;

        log.info("Getting fee for {} in {}", personNumber, date);

        memberExists = personRepository.exists(personNumber);
        if (!memberExists) {
            throw new MissingPersonException(personNumber);
        }

        fee = feeRepository.findOne(personNumber, date);
        if (fee.isEmpty()) {
            throw new MissingFeeException(personNumber, date);
        }

        log.debug("Got fee for {} in {}: fee", personNumber, date);

        return fee;
    }

    @Override
    public final Collection<Fee> payFees(final Collection<YearMonth> feeDates, final Long personNumber,
            final LocalDate payDate) {
        final Collection<Fee> newFees;
        final Collection<Fee> fees;
        final Person          person;
        final Collection<Fee> created;

        log.info("Paying fees for {} for months {}, paid in {}", personNumber, feeDates, payDate);

        person = personRepository.findOne(personNumber)
            .orElseThrow(() -> {
                log.error("Missing person {}", personNumber);
                throw new MissingPersonException(personNumber);
            });

        newFees = feeDates.stream()
            .map(d -> toUnpaidFee(person, d))
            .toList();

        // TODO: reject paying in the future
        validatorPay.validate(newFees);

        fees = feeRepository.save(newFees);

        // TODO: why not create saved?
        pay(person, fees, payDate);

        // TODO: Why can't just return the created fees?
        created = feeRepository.findAllForMemberInDates(personNumber, feeDates);

        // Send events for paid fees
        created.stream()
            .filter(Fee::paid)
            .forEach(this::sendFeePaidEvent);

        log.debug("Paid fees for {} for months {}, paid in {}: created", personNumber, feeDates, payDate);

        return created;
    }

    @Override
    public final Fee update(final Fee fee) {
        log.debug("Updating fee for {} in {} using data {}", fee.person()
            .number(), fee.date(), fee);

        if (!feeRepository.exists(fee.person()
            .number(), fee.date())) {
            log.error("Missing fee for {} in {}", fee.person()
                .number(), fee.date());
            throw new MissingFeeException(fee.person()
                .number(), fee.date());
        }

        // TODO: load person id
        // TODO: load transaction id

        // TODO: Can't remove transaction
        // TODO: Can't remove person
        validatorUpdate.validate(fee);

        return feeRepository.save(fee);
    }

    private final void pay(final Person person, final Collection<Fee> fees, final LocalDate payDate) {
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
            .map(Fee::date)
            .toList();

        // Calculate amount
        feeAmount = settingsSource.getFeeAmount() * feeDates.size();

        // Register transaction
        index = transactionRepository.findNextIndex();

        name = person.name()
            .fullName();

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

        feeRepository.pay(person, fees, savedTransaction);

        log.debug("Paid fee {} for {} with transaction {}", person, fees, savedTransaction);
    }

    private final void sendFeePaidEvent(final Fee fee) {
        eventEmitter.emit(new FeePaidEvent(fee, fee.date(), fee.person()
            .number()));
    }

    private final Fee toUnpaidFee(final Person person, final YearMonth date) {
        final Fee.Person      feePerson;
        final Fee.Transaction feeTransaction;

        feePerson = new Fee.Person(person.number(), person.name());
        feeTransaction = new Fee.Transaction(null, null);
        return new Fee(date, false, feePerson, feeTransaction);
    }

}
