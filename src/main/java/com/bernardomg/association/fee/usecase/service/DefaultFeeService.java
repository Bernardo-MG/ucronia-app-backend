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
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.exception.MissingFeeException;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.usecase.validation.FeeDateNotRegisteredRule;
import com.bernardomg.association.fee.usecase.validation.FeeNoDuplicatedDatesRule;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.PublicPerson;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.settings.usecase.source.AssociationSettingsSource;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the fee service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Slf4j
@Transactional
public final class DefaultFeeService implements FeeService {

    private final FeeRepository              feeRepository;

    private final MemberRepository           memberRepository;

    private final MessageSource              messageSource;

    private final PersonRepository           personRepository;

    private final AssociationSettingsSource  settingsSource;

    private final TransactionRepository      transactionRepository;

    private final Validator<Collection<Fee>> validatorPay;

    public DefaultFeeService(final FeeRepository feeRepo, final PersonRepository personRepo,
            final MemberRepository memberRepo, final TransactionRepository transactionRepo,
            final AssociationSettingsSource configSource, final MessageSource msgSource) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        personRepository = Objects.requireNonNull(personRepo);
        memberRepository = Objects.requireNonNull(memberRepo);
        transactionRepository = Objects.requireNonNull(transactionRepo);

        settingsSource = Objects.requireNonNull(configSource);
        messageSource = Objects.requireNonNull(msgSource);

        // TODO: Test validation
        validatorPay = new FieldRuleValidator<>(new FeeNoDuplicatedDatesRule(),
            new FeeDateNotRegisteredRule(personRepository, feeRepository));
    }

    @Override
    public final void delete(final long personNumber, final YearMonth date) {
        final boolean feeExists;
        final boolean memberExists;

        log.info("Deleting fee for {} in {}", personNumber, date);

        memberExists = personRepository.exists(personNumber);
        if (!memberExists) {
            throw new MissingPersonException(personNumber);
        }

        feeExists = feeRepository.exists(personNumber, date);
        if (!feeExists) {
            throw new MissingFeeException(personNumber + " " + date.toString());
        }

        feeRepository.delete(personNumber, date);

        // TODO: use an event
        if (date.equals(YearMonth.now())) {
            // If deleting for the current month, the user is set to active
            log.debug("Deactivating member status for person {}", personNumber);
            memberRepository.deactivate(personNumber);
        }
    }

    @Override
    public final Iterable<Fee> getAll(final FeeQuery query, final Pageable pageable) {
        final Iterable<Fee> fees;

        log.info("Getting all fees with query {}", query);

        fees = feeRepository.findAll(query, pageable);

        log.debug("Got all fees with query {}: {}", query, fees);

        return fees;
    }

    @Override
    public final Optional<Fee> getOne(final long personNumber, final YearMonth date) {
        final boolean       feeExists;
        final boolean       memberExists;
        final Optional<Fee> fee;

        log.info("Getting fee for {} in {}", personNumber, date);

        memberExists = personRepository.exists(personNumber);
        if (!memberExists) {
            throw new MissingPersonException(personNumber);
        }

        feeExists = feeRepository.exists(personNumber, date);
        if (!feeExists) {
            throw new MissingFeeException(personNumber + " " + date.toString());
        }

        fee = feeRepository.findOne(personNumber, date);

        log.debug("Got fee for {} in {}: fee", personNumber, date);

        return fee;
    }

    @Override
    public final Collection<Fee> payFees(final Collection<YearMonth> feeDates, final Long personNumber,
            final LocalDate transactionDate) {
        final Collection<Fee> newFees;
        final Collection<Fee> fees;
        final Person          person;
        final Collection<Fee> created;

        log.info("Paying fees for {} for months {}, paid in {}", personNumber, feeDates, transactionDate);

        person = personRepository.findOne(personNumber)
            .orElseThrow(() -> {
                log.error("Missing person {}", personNumber);
                throw new MissingPersonException(personNumber);
            });

        newFees = feeDates.stream()
            .map(d -> toFee(person, transactionDate, d))
            .toList();

        validatorPay.validate(newFees);

        fees = feeRepository.save(newFees);

        pay(person, fees, transactionDate);

        // TODO: Why can't just return the created fees?
        created = feeRepository.findAllForMemberInDates(personNumber, feeDates);

        if (feeDates.contains(YearMonth.now())) {
            // If paying for the current month, the user is set to active
            memberRepository.activate(personNumber);
        }

        log.debug("Paid fees for {} for months {}, paid in {}: created", personNumber, feeDates, transactionDate);

        return created;
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

    private final Fee toFee(final Person person, final LocalDate transaction, final YearMonth date) {
        final PublicPerson   feePerson;
        final FeeTransaction feeTransaction;

        feePerson = new PublicPerson(person.number(), person.name());
        feeTransaction = new FeeTransaction(transaction, null);
        return new Fee(date, false, feePerson, feeTransaction);
    }

}
