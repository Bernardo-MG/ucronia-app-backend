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

import com.bernardomg.association.configuration.usecase.source.AssociationConfigurationSource;
import com.bernardomg.association.fee.domain.exception.MissingFeeException;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeePerson;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.usecase.validation.CreateFeeValidator;
import com.bernardomg.association.member.domain.exception.MissingPersonException;
import com.bernardomg.association.member.domain.model.Person;
import com.bernardomg.association.member.domain.repository.PersonRepository;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.validation.Validator;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the fee service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Slf4j
@Transactional
public final class DefaultFeeService implements FeeService {

    private final AssociationConfigurationSource configurationSource;

    private final FeeRepository                  feeRepository;

    private final MessageSource                  messageSource;

    private final PersonRepository               personRepository;

    private final TransactionRepository          transactionRepository;

    private final Validator<Collection<Fee>>     validatorPay;

    public DefaultFeeService(final FeeRepository feeRepo, final PersonRepository personRepo,
            final TransactionRepository transactionRepo, final AssociationConfigurationSource configSource,
            final MessageSource msgSource) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        personRepository = Objects.requireNonNull(personRepo);
        transactionRepository = Objects.requireNonNull(transactionRepo);

        configurationSource = Objects.requireNonNull(configSource);
        messageSource = Objects.requireNonNull(msgSource);

        // TODO: Test validation
        validatorPay = new CreateFeeValidator(personRepository, feeRepository);
    }

    @Override
    public final void delete(final long personNumber, final YearMonth date) {
        final boolean feeExists;
        final boolean memberExists;

        log.debug("Deleting fee for {} in {}", personNumber, date);

        memberExists = personRepository.exists(personNumber);
        if (!memberExists) {
            throw new MissingPersonException(personNumber);
        }

        feeExists = feeRepository.exists(personNumber, date);
        if (!feeExists) {
            throw new MissingFeeException(personNumber + " " + date.toString());
        }

        feeRepository.delete(personNumber, date);
    }

    @Override
    public final Iterable<Fee> getAll(final FeeQuery query, final Pageable pageable) {
        return feeRepository.findAll(query, pageable);
    }

    @Override
    public final Optional<Fee> getOne(final long personNumber, final YearMonth date) {
        final boolean feeExists;
        final boolean memberExists;

        log.debug("Reading fee for {} in {}", personNumber, date);

        memberExists = personRepository.exists(personNumber);
        if (!memberExists) {
            throw new MissingPersonException(personNumber);
        }

        feeExists = feeRepository.exists(personNumber, date);
        if (!feeExists) {
            throw new MissingFeeException(personNumber + " " + date.toString());
        }

        return feeRepository.findOne(personNumber, date);
    }

    @Override
    public final Collection<Fee> payFees(final Collection<YearMonth> feeDates, final Long personNumber,
            final LocalDate transactionDate) {
        final Collection<Fee>  newFees;
        final Collection<Fee>  fees;
        final Optional<Person> person;

        log.debug("Paying fees for {} in {}. Months paid: {}", personNumber, feeDates, transactionDate);

        person = personRepository.findOne(personNumber);
        if (person.isEmpty()) {
            throw new MissingPersonException(personNumber);
        }

        newFees = feeDates.stream()
            .map(d -> toFee(person.get(), transactionDate, d))
            .toList();

        validatorPay.validate(newFees);

        fees = feeRepository.save(newFees);

        pay(person.get(), fees, transactionDate);

        // TODO: Why can't just return the created fees?
        return feeRepository.findAllForMemberInDates(personNumber, feeDates);
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
            .map(Fee::getDate)
            .toList();

        // Calculate amount
        feeAmount = configurationSource.getFeeAmount() * feeDates.size();

        // Register transaction
        index = transactionRepository.findNextIndex();

        name = person.getName()
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

        feeRepository.pay(person, fees, savedTransaction);
    }

    private final Fee toFee(final Person person, final LocalDate transaction, final YearMonth date) {
        final FeePerson      feePerson;
        final FeeTransaction feeTransaction;

        feePerson = FeePerson.builder()
            // TODO
            .withFullName(null)
            .withNumber(person.getNumber())
            .build();
        feeTransaction = FeeTransaction.builder()
            .withDate(transaction)
            .withIndex(null)
            .build();
        return Fee.builder()
            .withPerson(feePerson)
            .withTransaction(feeTransaction)
            .withDate(date)
            .withPaid(false)
            .build();
    }

}
