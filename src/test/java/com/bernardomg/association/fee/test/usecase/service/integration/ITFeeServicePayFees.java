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

package com.bernardomg.association.fee.test.usecase.service.integration;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeePaymentEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeePaymentSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.FeeEntities;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.test.configuration.initializer.FeeInitializer;
import com.bernardomg.association.fee.usecase.service.FeeService;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActivePerson;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEntities;
import com.bernardomg.settings.test.configuration.data.annotation.FeeAmountSetting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee service - pay fees")
class ITFeeServicePayFees {

    @Autowired
    private FeeInitializer              feeInitializer;

    @Autowired
    private FeePaymentSpringRepository  feePaymentRepository;

    @Autowired
    private FeeSpringRepository         feeRepository;

    @Autowired
    private FeeService                  service;

    @Autowired
    private TransactionSpringRepository transactionRepository;

    public ITFeeServicePayFees() {
        super();

        // TODO: make sure other tests cover these cases and use only unit tests
    }

    @Test
    @DisplayName("When a fee is paid and the fee exists but is not paid, it is set to paid")
    @MembershipActivePerson
    @NotPaidFee
    @FeeAmountSetting
    void testPayFees_Existing_NotPaid_PersistedFee() {
        final List<FeeEntity> entities;

        // WHEN
        service.payFees(List.of(FeeConstants.DATE), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        entities = feeRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "person.id", "personId",
                "person.membership.person")
            .containsExactlyInAnyOrder(FeeEntities.atDate());
    }

    @Test
    @DisplayName("When a fee is paid and the fee exists but is not paid, a single transaction is persisted")
    @MembershipActivePerson
    @NotPaidFee
    @FeeAmountSetting
    void testPayFees_Existing_NotPaid_PersistedTransaction() {
        final List<TransactionEntity> entities;

        // WHEN
        service.payFees(List.of(FeeConstants.DATE), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "index")
            .containsExactlyInAnyOrder(TransactionEntities.singleFee());
    }

    @Test
    @DisplayName("When a fee is paid and the fee exists but is not paid, it returns the created data")
    @MembershipActivePerson
    @NotPaidFee
    @FeeAmountSetting
    void testPayFees_Existing_NotPaid_ReturnedData() {
        final Collection<Fee> fees;

        // WHEN
        fees = service.payFees(List.of(FeeConstants.DATE), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        Assertions.assertThat(fees)
            .containsExactlyInAnyOrder(Fees.paidWithIndex(1));
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates and a fee exists but is not paid, it is set to paid")
    @MembershipActivePerson
    @NotPaidFee
    @FeeAmountSetting
    void testPayFees_MultipleDates_OneExisting_NotPaid_PersistedFee() {
        final List<FeeEntity> entities;

        // WHEN
        service.payFees(List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE), PersonConstants.NUMBER,
            FeeConstants.PAYMENT_DATE);

        // THEN
        entities = feeRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "person.id", "personId",
                "person.membership.person")
            .containsExactlyInAnyOrder(FeeEntities.atDate(), FeeEntities.nextDate());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates and a fee exists but is not paid, a single transaction is persisted")
    @MembershipActivePerson
    @NotPaidFee
    @FeeAmountSetting
    void testPayFees_MultipleDates_OneExisting_NotPaid_PersistedTransaction() {
        final List<TransactionEntity> entities;

        // WHEN
        service.payFees(List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE), PersonConstants.NUMBER,
            FeeConstants.PAYMENT_DATE);

        // THEN
        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "index")
            .containsExactlyInAnyOrder(TransactionEntities.multipleFees());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates and a fee exists but is not paid, a single transaction is returned")
    @MembershipActivePerson
    @NotPaidFee
    @FeeAmountSetting
    void testPayFees_MultipleDates_OneExisting_NotPaid_ReturnedData() {
        final Collection<Fee> fees;

        // WHEN
        fees = service.payFees(List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE), PersonConstants.NUMBER,
            FeeConstants.PAYMENT_DATE);

        // THEN
        Assertions.assertThat(fees)
            .containsExactlyInAnyOrder(Fees.paidWithIndex(1), Fees.paidNextDateWithIndex(1));
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, multiple fees are persisted")
    @MembershipActivePerson
    @FeeAmountSetting
    void testPayFees_MultipleDates_PersistedFee() {
        final List<FeeEntity> entities;

        // WHEN
        service.payFees(List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE), PersonConstants.NUMBER,
            FeeConstants.PAYMENT_DATE);

        // THEN
        entities = feeRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "person.id", "personId",
                "person.membership.person")
            .containsExactlyInAnyOrder(FeeEntities.atDate(), FeeEntities.nextDate());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, multiple fee payments are persisted")
    @MembershipActivePerson
    @FeeAmountSetting
    void testPayFees_MultipleDates_PersistedRelationship() {

        // WHEN
        service.payFees(List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE), PersonConstants.NUMBER,
            FeeConstants.PAYMENT_DATE);

        // THEN
        Assertions.assertThat(feePaymentRepository.count())
            .isEqualTo(2);
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, a single transaction is persisted")
    @MembershipActivePerson
    @FeeAmountSetting
    void testPayFees_MultipleDates_PersistedTransaction() {
        final List<TransactionEntity> entities;

        // WHEN
        service.payFees(List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE), PersonConstants.NUMBER,
            FeeConstants.PAYMENT_DATE);

        // THEN
        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "index")
            .containsExactlyInAnyOrder(TransactionEntities.multipleFees());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, it returns the created data")
    @MembershipActivePerson
    @FeeAmountSetting
    void testPayFees_MultipleDates_ReturnedData() {
        final Collection<Fee> fees;

        // WHEN
        fees = service.payFees(List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE), PersonConstants.NUMBER,
            FeeConstants.PAYMENT_DATE);

        // THEN
        Assertions.assertThat(fees)
            .containsExactlyInAnyOrder(Fees.paidWithIndex(1), Fees.paidNextDateWithIndex(1));
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, spanning two years, multiple fees are persisted")
    @MembershipActivePerson
    @FeeAmountSetting
    void testPayFees_MultipleDates_TwoYears_PersistedFee() {
        final List<FeeEntity> entities;

        // WHEN
        service.payFees(List.of(FeeConstants.LAST_YEAR_DATE, FeeConstants.FIRST_NEXT_YEAR_DATE), PersonConstants.NUMBER,
            FeeConstants.PAYMENT_DATE);

        // THEN
        entities = feeRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "person.id", "personId",
                "person.membership.person")
            .containsExactlyInAnyOrder(FeeEntities.lastInYear(), FeeEntities.firstNextYear());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, a single transaction is persisted")
    @MembershipActivePerson
    @FeeAmountSetting
    void testPayFees_MultipleDates_TwoYears_PersistedTransaction() {
        final List<TransactionEntity> entities;

        // WHEN
        service.payFees(List.of(FeeConstants.LAST_YEAR_DATE, FeeConstants.FIRST_NEXT_YEAR_DATE), PersonConstants.NUMBER,
            FeeConstants.PAYMENT_DATE);

        // THEN
        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "index")
            .containsExactlyInAnyOrder(TransactionEntities.multipleFeesSpanYears());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, spanning two years, it returns the created data")
    @MembershipActivePerson
    @FeeAmountSetting
    void testPayFees_MultipleDates_TwoYears_ReturnedData() {
        final Collection<Fee> fees;

        // WHEN
        fees = service.payFees(List.of(FeeConstants.LAST_YEAR_DATE, FeeConstants.FIRST_NEXT_YEAR_DATE),
            PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        Assertions.assertThat(fees)
            .containsExactlyInAnyOrder(Fees.paidLastInYear(1), Fees.paidFirstNextYear(1));
    }

    @Test
    @DisplayName("When a fee is paid and no fee amount is registered a single transaction is persisted with no amount")
    @MembershipActivePerson
    void testPayFees_NoAmount_PersistedTransaction() {
        final List<TransactionEntity> entities;

        // WHEN
        service.payFees(List.of(FeeConstants.DATE), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "index")
            .containsExactlyInAnyOrder(TransactionEntities.singleFeeNoAmount());
    }

    @Test
    @DisplayName("When a fee is paid the fee is persisted")
    @MembershipActivePerson
    @FeeAmountSetting
    void testPayFees_PersistedFee() {
        final List<FeeEntity> entities;

        // WHEN
        service.payFees(List.of(FeeConstants.DATE), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        entities = feeRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "person.id", "personId",
                "person.membership.person")
            .containsExactlyInAnyOrder(FeeEntities.atDate());
    }

    @Test
    @DisplayName("When a fee is paid a fee payment is registered")
    @MembershipActivePerson
    @FeeAmountSetting
    void testPayFees_PersistedRelationship() {
        final FeePaymentEntity  relationship;
        final FeeEntity         fee;
        final TransactionEntity transaction;

        // WHEN
        service.payFees(List.of(FeeConstants.DATE), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        Assertions.assertThat(feePaymentRepository.count())
            .isEqualTo(1);

        fee = feeRepository.findAll()
            .iterator()
            .next();
        transaction = transactionRepository.findAll()
            .iterator()
            .next();
        relationship = feePaymentRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(relationship.getFeeId())
            .isEqualTo(fee.getId());
        Assertions.assertThat(relationship.getTransactionId())
            .isEqualTo(transaction.getId());
    }

    @Test
    @DisplayName("When a fee is paid a single transaction is persisted")
    @MembershipActivePerson
    @FeeAmountSetting
    void testPayFees_PersistedTransaction() {
        final List<TransactionEntity> entities;

        // WHEN
        service.payFees(List.of(FeeConstants.DATE), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "index")
            .containsExactlyInAnyOrder(TransactionEntities.singleFee());
    }

    @Test
    @DisplayName("When a fee is paid a transaction is persisted, and there is another transaction, it is persisted with the next index")
    @MembershipActivePerson
    @PaidFee
    @FeeAmountSetting
    void testPayFees_PersistedTransaction_IncreaseIndex() {
        final Optional<TransactionEntity> entity;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        // WHEN
        service.payFees(List.of(FeeConstants.NEXT_DATE), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        entity = transactionRepository.findByIndex(2);

        Assertions.assertThat(entity)
            .isNotEmpty();
    }

    @Test
    @DisplayName("When a fee is paid a transaction is persisted with the initial index")
    @MembershipActivePerson
    @FeeAmountSetting
    void testPayFees_PersistedTransaction_InitialIndex() {
        final Optional<TransactionEntity> entity;

        // WHEN
        service.payFees(List.of(FeeConstants.NEXT_DATE), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        entity = transactionRepository.findByIndex(1);

        Assertions.assertThat(entity)
            .isNotEmpty();
    }

    @Test
    @DisplayName("When a fee is paid it returns the created data")
    @MembershipActivePerson
    @FeeAmountSetting
    void testPayFees_ReturnedData() {
        final Collection<Fee> fees;

        // WHEN
        fees = service.payFees(List.of(FeeConstants.DATE), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        Assertions.assertThat(fees)
            .containsExactlyInAnyOrder(Fees.paidWithIndex(1));
    }

}
