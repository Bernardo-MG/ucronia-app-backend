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

import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.request.DtoFeeCreationRequest;
import com.bernardomg.association.fee.persistence.model.PersistentFee;
import com.bernardomg.association.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

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
    @DisplayName("Adds an entity when creating")
    public void testCreate_AddsEntity() {
        final DtoFeeCreationRequest fee;

        fee = new DtoFeeCreationRequest();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(true);

        service.create(fee);

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Persists the data with a day which is not the first of the month")
    public void testCreate_AnotherDay_PersistedData() {
        final DtoFeeCreationRequest fee;
        final PersistentFee         entity;

        fee = new DtoFeeCreationRequest();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 2));
        fee.setPaid(true);

        service.create(fee);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getId())
            .isNotNull();
        Assertions.assertThat(entity.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(entity.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 1).getTime());
        Assertions.assertThat(entity.getPaid())
            .isTrue();
    }

    @Test
    @DisplayName("Persists the data")
    public void testCreate_PersistedData() {
        final DtoFeeCreationRequest fee;
        final PersistentFee         entity;

        fee = new DtoFeeCreationRequest();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(true);

        service.create(fee);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getId())
            .isNotNull();
        Assertions.assertThat(entity.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(entity.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 1).getTime());
        Assertions.assertThat(entity.getPaid())
            .isTrue();
    }

    @Test
    @DisplayName("Returns the created data")
    public void testCreate_ReturnedData() {
        final MemberFee             result;
        final DtoFeeCreationRequest fee;

        fee = new DtoFeeCreationRequest();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(true);

        result = service.create(fee);

        Assertions.assertThat(result.getId())
            .isNotNull();
        Assertions.assertThat(result.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(result.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 1).getTime());
        Assertions.assertThat(result.getPaid())
            .isTrue();
    }

}
