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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.funds.test.transaction.util.assertion.TransactionAssertions;
import com.bernardomg.association.funds.test.transaction.util.model.PersistentTransactions;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.membership.fee.model.MemberFee;
import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.fee.service.FeeService;
import com.bernardomg.association.membership.test.fee.config.NotPaidFee;
import com.bernardomg.association.membership.test.fee.util.assertion.FeeAssertions;
import com.bernardomg.association.membership.test.fee.util.model.FeeEntities;
import com.bernardomg.association.membership.test.fee.util.model.Fees;
import com.bernardomg.association.membership.test.fee.util.model.MemberFees;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.configuration.test.configuration.FeeAmountConfiguration;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee service - pay fees")
class ITFeeServicePayFees {

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
    void testCreate_ExistingNotPaid_PersistedFee() {
        final List<FeeEntity> entities;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        entities = repository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        FeeAssertions.isEqualTo(entities.iterator().next(), FeeEntities.paid());
    }

    @Test
    @DisplayName("When a fee is paid and the fee exists but is not paid, a single transaction is persisted")
    @ValidMember
    @NotPaidFee
    @FeeAmountConfiguration
    void testCreate_ExistingNotPaid_PersistedTransaction() {
        final List<PersistentTransaction> entities;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        TransactionAssertions.isEqualTo(entities.iterator()
            .next(), PersistentTransactions.singleFee());
    }

    @Test
    @DisplayName("When a fee is paid and the fee exists but is not paid, it returns the created data")
    @ValidMember
    @NotPaidFee
    @FeeAmountConfiguration
    void testCreate_ExistingNotPaid_ReturnedData() {
        final Collection<MemberFee> fees;

        fees = service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        Assertions.assertThat(fees)
            .containsExactly(MemberFees.paid());
    }

    @Test
    @DisplayName("When a fee is paidwith multiple dates and a fee exists but is not paid, it is set to paid")
    @ValidMember
    @NotPaidFee
    @FeeAmountConfiguration
    void testCreate_MultipleDates_ExistingNotPaid_PersistedFee() {
        final List<FeeEntity>     entities;
        final Iterator<FeeEntity> entitiesItr;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE, Fees.NEXT_DATE));

        entities = repository.findAll();

        entitiesItr = entities.iterator();

        FeeAssertions.isEqualTo(entitiesItr.next(), FeeEntities.paid());
        FeeAssertions.isEqualTo(entitiesItr.next(), FeeEntities.paidNextDate());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates and a fee exists but is not paid, a single transaction is persisted")
    @ValidMember
    @NotPaidFee
    @FeeAmountConfiguration
    void testCreate_MultipleDates_ExistingNotPaid_PersistedTransaction() {
        final List<PersistentTransaction> entities;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE, Fees.NEXT_DATE));

        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        TransactionAssertions.isEqualTo(entities.iterator()
            .next(), PersistentTransactions.multipleFees());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, multiple fees are persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_MultipleDates_PersistedFee() {
        final List<FeeEntity>     entities;
        final Iterator<FeeEntity> entitiesItr;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE, Fees.NEXT_DATE));

        entities = repository.findAll();

        entitiesItr = entities.iterator();

        FeeAssertions.isEqualTo(entitiesItr.next(), FeeEntities.paid());
        FeeAssertions.isEqualTo(entitiesItr.next(), FeeEntities.paidNextDate());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates, a single transaction is persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_MultipleDates_PersistedTransaction() {
        final List<PersistentTransaction> entities;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE, Fees.NEXT_DATE));

        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        TransactionAssertions.isEqualTo(entities.iterator()
            .next(), PersistentTransactions.multipleFees());
    }

    @Test
    @DisplayName("When a fee is paid and no fee amount is registered a single transaction is persisted with no amount")
    @ValidMember
    void testCreate_NoAmount_PersistedTransaction() {
        final List<PersistentTransaction> entities;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        TransactionAssertions.isEqualTo(entities.iterator()
            .next(), PersistentTransactions.singleFeeNoAmount());
    }

    @Test
    @DisplayName("When a fee is paid the fee is persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_PersistedFee() {
        final List<FeeEntity> entities;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        entities = repository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        FeeAssertions.isEqualTo(entities.iterator().next(), FeeEntities.paid());
    }

    @Test
    @DisplayName("When a fee is paid a single transaction is persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_PersistedTransaction() {
        final List<PersistentTransaction> entities;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        entities = transactionRepository.findAll();

        Assertions.assertThat(entities)
            .hasSize(1);

        TransactionAssertions.isEqualTo(entities.iterator()
            .next(), PersistentTransactions.singleFee());
    }

    @Test
    @DisplayName("When a fee is paid it returns the created data")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_ReturnedData() {
        final Collection<MemberFee> fees;

        fees = service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        Assertions.assertThat(fees)
            .containsExactly(MemberFees.paid());
    }

}
