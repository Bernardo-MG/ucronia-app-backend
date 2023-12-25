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

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.funds.test.transaction.util.assertion.TransactionAssertions;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.membership.fee.model.MemberFee;
import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.fee.service.FeeService;
import com.bernardomg.association.membership.test.fee.configuration.NotPaidFee;
import com.bernardomg.association.membership.test.fee.util.assertion.FeeAssertions;
import com.bernardomg.association.membership.test.fee.util.model.Fees;
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
        final FeeEntity entity;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
        FeeAssertions.isEqualTo(entity, FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(YearMonth.of(2020, Month.FEBRUARY))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("When a fee is paid and the fee exists but is not paid, a single transaction is persisted")
    @ValidMember
    @NotPaidFee
    @FeeAmountConfiguration
    void testCreate_ExistingNotPaid_PersistedTransaction() {
        final PersistentTransaction entity;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        entity = transactionRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(transactionRepository.count())
            .isEqualTo(1);
        TransactionAssertions.isEqualTo(entity, PersistentTransaction.builder()
            .id(1L)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .description("Cuota de Member 1 Surname 1 para Febrero 2020")
            .amount(1F)
            .build());
    }

    @Test
    @DisplayName("When a fee is paid and the fee exists but is not paid, it returns the created data")
    @ValidMember
    @NotPaidFee
    @FeeAmountConfiguration
    void testCreate_ExistingNotPaid_ReturnedData() {
        final Collection<? extends MemberFee> fee;

        fee = service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        Assertions.assertThat(fee)
            .hasSize(1);

        FeeAssertions.isEqualTo(fee.iterator()
            .next(),
            MemberFee.builder()
                .memberId(1L)
                .memberName("Member 1 Surname 1")
                .date(YearMonth.of(2020, Month.FEBRUARY))
                .paid(true)
                .build());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates multiple fees are persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_MultipleDates_PersistedFee() {

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE, Fees.NEXT_DATE));

        Assertions.assertThat(repository.count())
            .isEqualTo(2);

        Assertions.assertThat(repository.findAll())
            .extracting(FeeEntity::getDate)
            .contains(YearMonth.of(2020, Month.FEBRUARY))
            .contains(YearMonth.of(2020, Month.MARCH));
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates a single transaction is persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_MultipleDates_PersistedFee_PersistedTransaction() {
        final PersistentTransaction entity;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE, Fees.NEXT_DATE));

        entity = transactionRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(transactionRepository.count())
            .isEqualTo(1);
        TransactionAssertions.isEqualTo(entity, PersistentTransaction.builder()
            .id(1L)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .description("Cuota de Member 1 Surname 1 para Febrero 2020, Marzo 2020")
            .amount(2F)
            .build());
    }

    @Test
    @DisplayName("When a fee is paid and no fee amount is registered a single transaction is persisted with no amount")
    @ValidMember
    void testCreate_NoAmount_PersistedTransaction() {
        final PersistentTransaction entity;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        entity = transactionRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(transactionRepository.count())
            .isEqualTo(1);
        TransactionAssertions.isEqualTo(entity, PersistentTransaction.builder()
            .id(1L)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .description("Cuota de Member 1 Surname 1 para Febrero 2020")
            .amount(0F)
            .build());
    }

    @Test
    @DisplayName("When a fee is paid the fee is persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_PersistedFee() {
        final FeeEntity entity;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
        FeeAssertions.isEqualTo(entity, FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(YearMonth.of(2020, Month.FEBRUARY))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("When a fee is paid a single transaction is persisted")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_PersistedTransaction() {
        final PersistentTransaction entity;

        service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        entity = transactionRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(transactionRepository.count())
            .isEqualTo(1);
        TransactionAssertions.isEqualTo(entity, PersistentTransaction.builder()
            .id(1L)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .description("Cuota de Member 1 Surname 1 para Febrero 2020")
            .amount(1F)
            .build());
    }

    @Test
    @DisplayName("When a fee is paid it returns the created data")
    @ValidMember
    @FeeAmountConfiguration
    void testCreate_ReturnedData() {
        final Collection<? extends MemberFee> fee;

        fee = service.payFees(1L, Fees.PAYMENT_DATE, List.of(Fees.DATE));

        Assertions.assertThat(fee)
            .hasSize(1);

        FeeAssertions.isEqualTo(fee.iterator()
            .next(),
            MemberFee.builder()
                .memberId(1L)
                .memberName("Member 1 Surname 1")
                .date(YearMonth.of(2020, Month.FEBRUARY))
                .paid(true)
                .build());
    }

}
