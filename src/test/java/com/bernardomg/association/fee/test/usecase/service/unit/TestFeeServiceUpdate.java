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

package com.bernardomg.association.fee.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.bernardomg.association.fee.domain.exception.MissingFeeException;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.association.settings.usecase.source.AssociationSettingsSource;
import com.bernardomg.association.transaction.domain.exception.MissingTransactionException;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fee service - update")
class TestFeeServiceUpdate {

    @Mock
    private EventEmitter              eventEmitter;

    @Mock
    private FeeRepository             feeRepository;

    @Mock
    private MessageSource             messageSource;

    @Mock
    private PersonRepository          personRepository;

    @InjectMocks
    private DefaultFeeService         service;

    @Mock
    private AssociationSettingsSource settingsSource;

    @Mock
    private TransactionRepository     transactionRepository;

    public TestFeeServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("When the transaction is changed, an exception is thrown")
    void testUpdate_ChangedTransaction() {
        final ThrowingCallable execution;
        final FieldFailure     failure;

        // GIVEN
        given(feeRepository.findOne(PersonConstants.NUMBER, FeeConstants.DATE))
            .willReturn(Optional.of(Fees.alternativeTransaction()));
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        execution = () -> service.update(Fees.paid());

        // THEN
        failure = new FieldFailure("modified", "transaction.modified", "transaction", TransactionConstants.INDEX);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

    @Test
    @DisplayName("With a not existing fee, an exception is thrown")
    void testUpdate_NotExistingFee() {
        final ThrowingCallable execution;

        // GIVEN
        given(feeRepository.findOne(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.update(Fees.paid());

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingFeeException.class);
    }

    @Test
    @DisplayName("With a not existing person, an exception is thrown")
    void testUpdate_NotExistingPerson() {
        final ThrowingCallable execution;

        // GIVEN
        given(feeRepository.findOne(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(Fees.paid());

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingPersonException.class);
    }

    @Test
    @DisplayName("With a not existing transaction, an exception is thrown")
    void testUpdate_NotExistingTransaction() {
        final ThrowingCallable execution;

        // GIVEN
        given(feeRepository.findOne(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(false);

        // WHEN
        execution = () -> service.update(Fees.paid());

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingTransactionException.class);
    }

    @Test
    @DisplayName("When the payment date is changed, a transaction is saved")
    void testUpdate_PaymentDateChanged() {
        final LocalDate date;

        // GIVEN
        given(feeRepository.findOne(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(true);
        given(transactionRepository.findOne(TransactionConstants.INDEX)).willReturn(Optional.of(Transactions.positive()));

        date = FeeConstants.PAYMENT_DATE.plusMonths(1);

        // WHEN
        service.update(Fees.paidAtDate(date));

        // THEN
        verify(transactionRepository).save(Transactions.forDate(date));
    }

    @Test
    @DisplayName("When the payment date is not changed, no transaction is saved")
    void testUpdate_PaymentDateNotChanged() {
        // GIVEN
        given(feeRepository.findOne(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        service.update(Fees.paid());

        // THEN
        verify(transactionRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("When updating a fee, the change is persisted")
    void testUpdate_PersistedData() {
        // GIVEN
        given(feeRepository.findOne(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        service.update(Fees.paid());

        // THEN
        verify(feeRepository).save(Fees.paid());
    }

    @Test
    @DisplayName("When the person is changed, an exception is thrown")
    void testUpdate_PersonTransaction() {
        final ThrowingCallable execution;
        final FieldFailure     failure;

        // GIVEN
        given(feeRepository.findOne(PersonConstants.NUMBER, FeeConstants.DATE))
            .willReturn(Optional.of(Fees.alternativePerson()));
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        execution = () -> service.update(Fees.paid());

        // THEN
        failure = new FieldFailure("modified", "person.modified", "person", TransactionConstants.INDEX);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

    @Test
    @DisplayName("When the transaction is removed, an exception is thrown")
    void testUpdate_RemovedTransaction() {
        final ThrowingCallable execution;
        final FieldFailure     failure;

        // GIVEN
        given(feeRepository.findOne(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.update(Fees.notPaid());

        // THEN
        failure = new FieldFailure("removed", "transaction.removed", "transaction", null);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

    @Test
    @DisplayName("When updating a fee, the change is returned")
    void testUpdate_ReturnedData() {
        final Fee updated;

        // GIVEN
        given(feeRepository.findOne(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(true);
        given(feeRepository.save(Fees.paid())).willReturn(Fees.paid());

        // WHEN
        updated = service.update(Fees.paid());

        // THEN
        Assertions.assertThat(updated)
            .as("fee")
            .isEqualTo(Fees.paid());
    }

}
