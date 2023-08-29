/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.association.test.fee.service.integration;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.DtoMemberFee;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.request.FeesPayment;
import com.bernardomg.association.fee.persistence.model.PersistentFee;
import com.bernardomg.association.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.fee.util.assertion.FeeAssertions;
import com.bernardomg.association.test.fee.util.model.FeesCreate;
import com.bernardomg.association.test.transaction.util.assertion.TransactionAssertions;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
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
    @DisplayName("When a fee is paid and the fee exists but is unpaid, it is set to paid")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/unpaid.sql" })
    void testCreate_ExistingUnpaid_PersistedFee() {
        final FeesPayment   feeRequest;
        final PersistentFee entity;

        feeRequest = FeesCreate.valid();

        service.payFees(feeRequest);

        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
        FeeAssertions.isEqualTo(entity, PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(YearMonth.of(2020, Month.FEBRUARY))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("When a fee is paid and the fee exists but is unpaid, a single transaction is persisted")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/unpaid.sql" })
    void testCreate_ExistingUnpaid_PersistedTransaction() {
        final FeesPayment           feeRequest;
        final PersistentTransaction entity;

        feeRequest = FeesCreate.valid();

        service.payFees(feeRequest);

        entity = transactionRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(transactionRepository.count())
            .isEqualTo(1);
        TransactionAssertions.isEqualTo(entity, PersistentTransaction.builder()
            .id(1L)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .description("Fee paid")
            .amount(1F)
            .build());
    }

    @Test
    @DisplayName("When a fee is paid and the fee exists but is unpaid, it returns the created data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/unpaid.sql" })
    void testCreate_ExistingUnpaid_ReturnedData() {
        final FeesPayment                     feeRequest;
        final Collection<? extends MemberFee> fee;

        feeRequest = FeesCreate.valid();

        fee = service.payFees(feeRequest);

        Assertions.assertThat(fee)
            .hasSize(1);

        FeeAssertions.isEqualTo(fee.iterator()
            .next(),
            DtoMemberFee.builder()
                .id(1L)
                .memberId(1L)
                .memberName(null)
                .date(YearMonth.of(2020, Month.FEBRUARY))
                .paid(true)
                .build());
    }

    @Test
    @DisplayName("When the user is inactive and a fee is created the fee is persisted")
    @Sql({ "/db/queries/member/inactive.sql" })
    void testCreate_Inactive_PersistedFee() {
        final FeesPayment   feeRequest;
        final PersistentFee entity;

        feeRequest = FeesCreate.valid();

        service.payFees(feeRequest);

        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
        FeeAssertions.isEqualTo(entity, PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(YearMonth.of(2020, Month.FEBRUARY))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("When the user is inactive and a fee is created a single transaction is persisted")
    @Sql({ "/db/queries/member/inactive.sql" })
    void testCreate_Inactive_PersistedTransaction() {
        final FeesPayment           feeRequest;
        final PersistentTransaction entity;

        feeRequest = FeesCreate.valid();

        service.payFees(feeRequest);

        entity = transactionRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(transactionRepository.count())
            .isEqualTo(1);
        TransactionAssertions.isEqualTo(entity, PersistentTransaction.builder()
            .id(1L)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .description("Fee paid")
            .amount(1F)
            .build());
    }

    @Test
    @DisplayName("When the user is inactive and a a fee is created it returns the created data")
    @Sql({ "/db/queries/member/inactive.sql" })
    void testCreate_Inactive_ReturnedData() {
        final FeesPayment                     feeRequest;
        final Collection<? extends MemberFee> fee;

        feeRequest = FeesCreate.valid();

        fee = service.payFees(feeRequest);

        Assertions.assertThat(fee)
            .hasSize(1);

        FeeAssertions.isEqualTo(fee.iterator()
            .next(),
            DtoMemberFee.builder()
                .id(1L)
                .memberId(1L)
                .memberName(null)
                .date(YearMonth.of(2020, Month.FEBRUARY))
                .paid(true)
                .build());
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates multiple fees are persisted")
    @Sql({ "/db/queries/member/single.sql" })
    void testCreate_MultipleDates_PersistedFee() {
        final FeesPayment feeRequest;

        feeRequest = FeesCreate.multipleDates();

        service.payFees(feeRequest);

        Assertions.assertThat(repository.count())
            .isEqualTo(2);

        Assertions.assertThat(repository.findAll())
            .extracting(PersistentFee::getDate)
            .contains(YearMonth.of(2020, Month.FEBRUARY))
            .contains(YearMonth.of(2020, Month.MARCH));
    }

    @Test
    @DisplayName("When a fee is paid with multiple dates a single transaction is persisted")
    @Sql({ "/db/queries/member/single.sql" })
    void testCreate_MultipleDates_PersistedFee_PersistedTransaction() {
        final FeesPayment           feeRequest;
        final PersistentTransaction entity;

        feeRequest = FeesCreate.multipleDates();

        service.payFees(feeRequest);

        entity = transactionRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(transactionRepository.count())
            .isEqualTo(1);
        TransactionAssertions.isEqualTo(entity, PersistentTransaction.builder()
            .id(1L)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .description("Fee paid")
            .amount(1F)
            .build());
    }

    @Test
    @DisplayName("When a fee is paid the fee is persisted")
    @Sql({ "/db/queries/member/single.sql" })
    void testCreate_PersistedFee() {
        final FeesPayment   feeRequest;
        final PersistentFee entity;

        feeRequest = FeesCreate.valid();

        service.payFees(feeRequest);

        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
        FeeAssertions.isEqualTo(entity, PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(YearMonth.of(2020, Month.FEBRUARY))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("When a fee is paid a single transaction is persisted")
    @Sql({ "/db/queries/member/single.sql" })
    void testCreate_PersistedTransaction() {
        final FeesPayment           feeRequest;
        final PersistentTransaction entity;

        feeRequest = FeesCreate.valid();

        service.payFees(feeRequest);

        entity = transactionRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(transactionRepository.count())
            .isEqualTo(1);
        TransactionAssertions.isEqualTo(entity, PersistentTransaction.builder()
            .id(1L)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .description("Fee paid")
            .amount(1F)
            .build());
    }

    @Test
    @DisplayName("When a fee is paid it returns the created data")
    @Sql({ "/db/queries/member/single.sql" })
    void testCreate_ReturnedData() {
        final FeesPayment                     feeRequest;
        final Collection<? extends MemberFee> fee;

        feeRequest = FeesCreate.valid();

        fee = service.payFees(feeRequest);

        Assertions.assertThat(fee)
            .hasSize(1);

        FeeAssertions.isEqualTo(fee.iterator()
            .next(),
            DtoMemberFee.builder()
                .id(1L)
                .memberId(1L)
                .memberName(null)
                .date(YearMonth.of(2020, Month.FEBRUARY))
                .paid(true)
                .build());
    }

}
