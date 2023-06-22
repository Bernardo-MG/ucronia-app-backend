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

package com.bernardomg.association.test.fee.integration.service;

import java.util.GregorianCalendar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.ImmutableMemberFee;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.request.DtoFeeCreateRequest;
import com.bernardomg.association.fee.persistence.model.PersistentFee;
import com.bernardomg.association.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.fee.assertion.FeeAssertions;

@IntegrationTest
@DisplayName("Fee service - create")
@Sql({ "/db/queries/member/single.sql" })
public class ITFeeServiceCreate {

    @Autowired
    private FeeRepository repository;

    @Autowired
    private FeeService    service;

    public ITFeeServiceCreate() {
        super();
    }

    @Test
    @DisplayName("Persists the data with a day which is not the first of the month")
    public void testCreate_AnotherDay_PersistedData() {
        final DtoFeeCreateRequest feeRequest;
        final PersistentFee       entity;

        feeRequest = new DtoFeeCreateRequest();
        feeRequest.setMemberId(1L);
        feeRequest.setDate(new GregorianCalendar(2020, 1, 2));
        feeRequest.setPaid(true);

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
    @DisplayName("With new data it adds the entity data to the persistence layer")
    public void testCreate_PersistedData() {
        final DtoFeeCreateRequest feeRequest;
        final PersistentFee       entity;

        feeRequest = new DtoFeeCreateRequest();
        feeRequest.setMemberId(1L);
        feeRequest.setDate(new GregorianCalendar(2020, 1, 1));
        feeRequest.setPaid(true);

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
    @DisplayName("With new data it returns the created data")
    public void testCreate_ReturnedData() {
        final DtoFeeCreateRequest feeRequest;
        final MemberFee           fee;

        feeRequest = new DtoFeeCreateRequest();
        feeRequest.setMemberId(1L);
        feeRequest.setDate(new GregorianCalendar(2020, 1, 1));
        feeRequest.setPaid(true);

        fee = service.create(feeRequest);

        FeeAssertions.isEqualTo(fee, ImmutableMemberFee.builder()
            .id(1L)
            .memberId(1L)
            .name(null)
            .surname(null)
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());
    }

}
