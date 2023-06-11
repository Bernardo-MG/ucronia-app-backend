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
import com.bernardomg.association.fee.model.request.DtoFeeCreationRequest;
import com.bernardomg.association.fee.persistence.model.PersistentFee;
import com.bernardomg.association.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.fee.assertion.FeeAssertions;

@IntegrationTest
@DisplayName("Fee service - update")
public class ITFeeServiceUpdate {

    @Autowired
    private FeeRepository repository;

    @Autowired
    private FeeService    service;

    public ITFeeServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("Adds no entity when updating")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
    public void testUpdate_AddsNoEntity() {
        final DtoFeeCreationRequest fee;

        fee = new DtoFeeCreationRequest();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(false);

        service.update(1L, fee);

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("When updating a not existing entity a new one is added")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
    public void testUpdate_NotExisting_AddsEntity() {
        final DtoFeeCreationRequest fee;

        fee = new DtoFeeCreationRequest();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 2, 1));
        fee.setPaid(false);

        service.update(10L, fee);

        Assertions.assertThat(repository.count())
            .isEqualTo(2);
    }

    @Test
    @DisplayName("When changing a fee from unpaid to paid the persisted data is updated")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/unpaid.sql" })
    public void testUpdate_Pay_PersistedData() {
        final DtoFeeCreationRequest fee;
        final PersistentFee         entity;

        fee = new DtoFeeCreationRequest();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(true);

        service.update(1L, fee);
        entity = repository.findAll()
            .iterator()
            .next();

        FeeAssertions.isEqualTo(entity, ImmutableMemberFee.builder()
            .id(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("Updates persisted data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
    public void testUpdate_PersistedData() {
        final DtoFeeCreationRequest fee;
        final PersistentFee         entity;

        fee = new DtoFeeCreationRequest();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(false);

        service.update(1L, fee);
        entity = repository.findAll()
            .iterator()
            .next();

        FeeAssertions.isEqualTo(entity, ImmutableMemberFee.builder()
            .id(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(false)
            .build());
    }

    @Test
    @DisplayName("Returns the updated data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
    public void testUpdate_ReturnedData() {
        final DtoFeeCreationRequest fee;
        final MemberFee             result;

        fee = new DtoFeeCreationRequest();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(false);

        result = service.update(1L, fee);

        FeeAssertions.isEqualTo(result, ImmutableMemberFee.builder()
            .id(1L)
            .name(null)
            .surname(null)
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(false)
            .build());
    }

}
