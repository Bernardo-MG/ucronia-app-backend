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

import java.util.Collection;
import java.util.GregorianCalendar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.DtoMemberFee;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.request.FeeCreate;
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
@DisplayName("Fee service - create")
@Sql({ "/db/queries/member/single.sql" })
class ITFeeServiceCreate {

    @Autowired
    private FeeRepository         repository;

    @Autowired
    private FeeService            service;

    @Autowired
    private TransactionRepository transactionRepository;

    public ITFeeServiceCreate() {
        super();
        // TODO: Test with multiple dates
    }

    @Test
    @DisplayName("When a fee is created the data is persisted")
    void testCreate_PersistedData() {
        final FeeCreate     feeRequest;
        final PersistentFee entity;

        feeRequest = FeesCreate.valid();

        service.create(feeRequest);

        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
        FeeAssertions.isEqualTo(entity, PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("When a fee is created a transaction is persisted")
    void testCreate_PersistedTransaction() {
        final FeeCreate             feeRequest;
        final PersistentTransaction entity;

        feeRequest = FeesCreate.valid();

        service.create(feeRequest);

        entity = transactionRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(transactionRepository.count())
            .isEqualTo(1);
        TransactionAssertions.isEqualTo(entity, PersistentTransaction.builder()
            .id(1L)
            .date(new GregorianCalendar(2020, 0, 1))
            .description("Fee paid")
            .build());
    }

    @Test
    @DisplayName("With new data it returns the created data")
    void testCreate_ReturnedData() {
        final FeeCreate                       feeRequest;
        final Collection<? extends MemberFee> fee;

        feeRequest = FeesCreate.valid();

        fee = service.create(feeRequest);

        Assertions.assertThat(fee)
            .hasSize(1);

        FeeAssertions.isEqualTo(fee.iterator()
            .next(),
            DtoMemberFee.builder()
                .id(1L)
                .memberId(1L)
                .name(null)
                .surname(null)
                .date(new GregorianCalendar(2020, 1, 1))
                .paid(true)
                .build());
    }

}
