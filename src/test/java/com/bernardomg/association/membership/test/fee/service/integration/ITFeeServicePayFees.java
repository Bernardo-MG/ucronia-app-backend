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

package com.bernardomg.association.membership.test.fee.service.integration;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.funds.test.transaction.config.factory.TransactionConstants;
import com.bernardomg.association.funds.test.transaction.config.factory.TransactionEntities;
import com.bernardomg.association.funds.test.transaction.util.assertion.TransactionAssertions;
import com.bernardomg.association.membership.test.fee.config.NotPaidFee;
import com.bernardomg.association.membership.test.fee.config.PaidFee;
import com.bernardomg.association.membership.test.fee.config.factory.FeeConstants;
import com.bernardomg.association.membership.test.fee.config.factory.FeeEntities;
import com.bernardomg.association.membership.test.fee.config.factory.Fees;
import com.bernardomg.association.membership.test.fee.util.assertion.FeeAssertions;
import com.bernardomg.association.membership.test.member.config.ValidMember;
import com.bernardomg.association.membership.test.member.config.factory.MemberConstants;
import com.bernardomg.association.model.fee.Fee;
import com.bernardomg.association.persistence.fee.model.FeeEntity;
import com.bernardomg.association.persistence.fee.repository.FeePaymentRepository;
import com.bernardomg.association.persistence.fee.repository.FeeRepository;
import com.bernardomg.association.persistence.transaction.model.TransactionEntity;
import com.bernardomg.association.persistence.transaction.repository.TransactionRepository;
import com.bernardomg.association.service.fee.FeeService;
import com.bernardomg.configuration.test.configuration.FeeAmountConfiguration;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee service - pay fees")
class ITFeeServicePayFees {

    @Autowired
    private FeePaymentRepository  feePaymentRepository;

    @Autowired
    private FeeRepository         repository;

    @Autowired
    private FeeService            service;

    @Autowired
    private TransactionRepository transactionRepository;

    public ITFeeServicePayFees() {
        super();
    }

    @Test
    @DisplayName("When a fee is paid and the fee exists but is not paid, it is set to paid")
    @ValidMember
    @NotPaidFee
    @FeeAmountConfiguration
    void testCreate_Existing_NotPaid_PersistedFee() {
        final List<FeeEntity> entities;

        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE, List.of(FeeConstants.DATE));

        // THEN
        entities = repository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        FeeAssertions.isEqualTo(entities.iterator()
            .next(), FeeEntities.atDate());
    }

    @Test
    @DisplayName("When a fee is paid and the fee exists but is not paid, a single transaction is persisted")
    @ValidMember
    @NotPaidFee
    @FeeAmountConfiguration
    void testCreate_Existing_NotPaid_PersistedTransaction() {
        final List<TransactionEntity> entities;

        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE, List.of(FeeConstants.DATE));

        // THEN
        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        TransactionAssertions.isEqualTo(entities.iterator()
            .next(), TransactionEntities.singleFee());
    }

    @Test
    @DisplayName("When a fee is paid and the fee exists but is not paid, it returns the created data")
    @ValidMember
    @NotPaidFee
    @FeeAmountConfiguration
    void testCreate_Existing_NotPaid_ReturnedData() {
        final Collection<Fee> fees;

        // WHEN
        fees = service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE, List.of(FeeConstants.DATE));

        // THEN
        Assertions.assertThat(fees)
            .containsExactly(Fees.paidWithIndex(1));
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates and a fee exists but is not paid, it is set to paid")
    @ValidMember
    @NotPaidFee
    @FeeAmountConfiguration
    void testCreate_MultipleDates_OneExisting_NotPaid_PersistedFee() {
        final List<FeeEntity>     entities;
        final Iterator<FeeEntity> entitiesItr;

        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE));

        // THEN
        entities = repository.findAll();

        Assertions.assertThat(entities)
            .hasSize(2);

        entitiesItr = entities.iterator();

        FeeAssertions.isEqualTo(entitiesItr.next(), FeeEntities.atDate());
        FeeAssertions.isEqualTo(entitiesItr.next(), FeeEntities.nextDate());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates and a fee exists but is not paid, a single transaction is persisted")
    @ValidMember
    @NotPaidFee
    @FeeAmountConfiguration
    void testCreate_MultipleDates_OneExisting_NotPaid_PersistedTransaction() {
        final List<TransactionEntity> entities;

        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE));

        // THEN
        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        TransactionAssertions.isEqualTo(entities.iterator()
            .next(), TransactionEntities.multipleFees());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates and a fee exists but is not paid, a single transaction is persisted")
    @ValidMember
    @NotPaidFee
    @FeeAmountConfiguration
    void testCreate_MultipleDates_OneExisting_NotPaid_ReturnedData() {
        final Collection<Fee> fees;

        // WHEN
        fees = service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE));

        // THEN
        Assertions.assertThat(fees)
            .containsExactly(Fees.paidWithIndex(1), Fees.paidNextDateWithIndex(1));
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, multiple fees are persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_MultipleDates_PersistedFee() {
        final List<FeeEntity>     entities;
        final Iterator<FeeEntity> entitiesItr;

        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE));

        // THEN
        entities = repository.findAll();

        Assertions.assertThat(entities)
            .hasSize(2);

        entitiesItr = entities.iterator();

        FeeAssertions.isEqualTo(entitiesItr.next(), FeeEntities.atDate());
        FeeAssertions.isEqualTo(entitiesItr.next(), FeeEntities.nextDate());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, multiple fee payments are persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_MultipleDates_PersistedRelationship() {
        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE));

        // THEN
        Assertions.assertThat(feePaymentRepository.count())
            .isEqualTo(2);
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, a single transaction is persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_MultipleDates_PersistedTransaction() {
        final List<TransactionEntity> entities;

        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE));

        // THEN
        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        TransactionAssertions.isEqualTo(entities.iterator()
            .next(), TransactionEntities.multipleFees());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, it returns the created data")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_MultipleDates_ReturnedData() {
        final Collection<Fee> fees;

        // WHEN
        fees = service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE));

        // THEN
        Assertions.assertThat(fees)
            .containsExactly(Fees.paidWithIndex(1), Fees.paidNextDateWithIndex(1));
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, spanning two years, multiple fees are persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_MultipleDates_TwoYears_PersistedFee() {
        final List<FeeEntity>     entities;
        final Iterator<FeeEntity> entitiesItr;

        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.LAST_YEAR_DATE, FeeConstants.FIRST_NEXT_YEAR_DATE));

        // THEN
        entities = repository.findAll();

        Assertions.assertThat(entities)
            .hasSize(2);

        entitiesItr = entities.iterator();

        FeeAssertions.isEqualTo(entitiesItr.next(), FeeEntities.lastInYear());
        FeeAssertions.isEqualTo(entitiesItr.next(), FeeEntities.firstNextYear());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, a single transaction is persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_MultipleDates_TwoYears_PersistedTransaction() {
        final List<TransactionEntity> entities;

        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.LAST_YEAR_DATE, FeeConstants.FIRST_NEXT_YEAR_DATE));

        // THEN
        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        TransactionAssertions.isEqualTo(entities.iterator()
            .next(), TransactionEntities.multipleFeesSpanYears());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, spanning two years, it returns the created data")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_MultipleDates_TwoYears_ReturnedData() {
        final Collection<Fee> fees;

        // WHEN
        fees = service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.LAST_YEAR_DATE, FeeConstants.FIRST_NEXT_YEAR_DATE));

        // THEN
        Assertions.assertThat(fees)
            .containsExactly(Fees.paidLastInYear(1), Fees.paidFirstNextYear(1));
    }

    @Test
    @DisplayName("When a fee is paid and no fee amount is registered a single transaction is persisted with no amount")
    @ValidMember
    void testCreate_NoAmount_PersistedTransaction() {
        final List<TransactionEntity> entities;

        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE, List.of(FeeConstants.DATE));

        // THEN
        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        TransactionAssertions.isEqualTo(entities.iterator()
            .next(), TransactionEntities.singleFeeNoAmount());
    }

    @Test
    @DisplayName("When a fee is paid the fee is persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_PersistedFee() {
        final List<FeeEntity> entities;

        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE, List.of(FeeConstants.DATE));

        // THEN
        entities = repository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        FeeAssertions.isEqualTo(entities.iterator()
            .next(), FeeEntities.atDate());
    }

    @Test
    @DisplayName("When a fee is paid a fee payment is registered")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_PersistedRelationship() {
        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE, List.of(FeeConstants.DATE));

        // THEN
        Assertions.assertThat(feePaymentRepository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("When a fee is paid a single transaction is persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_PersistedTransaction() {
        final List<TransactionEntity> entities;

        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE, List.of(FeeConstants.DATE));

        // THEN
        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        TransactionAssertions.isEqualTo(entities.iterator()
            .next(), TransactionEntities.singleFee());
    }

    @Test
    @DisplayName("When a fee is paid  a single transaction is persisted and it uses the next index")
    @ValidMember
    @PaidFee
    @FeeAmountConfiguration
    void testCreate_PersistedTransaction_IncreaseIndex() {
        final Optional<TransactionEntity> entity;

        // WHEN
        service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE, List.of(FeeConstants.NEXT_DATE));

        // THEN
        entity = transactionRepository.findOneByIndex(TransactionConstants.INDEX + 1);

        Assertions.assertThat(entity)
            .isNotEmpty();
    }

    @Test
    @DisplayName("When a fee is paid it returns the created data")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_ReturnedData() {
        final Collection<Fee> fees;

        // WHEN
        fees = service.payFees(MemberConstants.NUMBER, FeeConstants.PAYMENT_DATE, List.of(FeeConstants.DATE));

        // THEN
        Assertions.assertThat(fees)
            .containsExactly(Fees.paidWithIndex(1));
    }

}
