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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.DtoFeeForm;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.PersistentFee;
import com.bernardomg.association.fee.repository.FeeRepository;
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
        final DtoFeeForm fee;

        fee = new DtoFeeForm();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(true);

        service.create(fee);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Persists the data with a day which is not the first of the month")
    public void testCreate_AnotherDay_PersistedData() {
        final DtoFeeForm    fee;
        final PersistentFee entity;

        fee = new DtoFeeForm();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 2));
        fee.setPaid(true);

        service.create(fee);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals(1, entity.getMemberId());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).getTime(), entity.getDate()
            .getTime());
        Assertions.assertEquals(true, entity.getPaid());
    }

    @Test
    @DisplayName("Persists the data")
    public void testCreate_PersistedData() {
        final DtoFeeForm    fee;
        final PersistentFee entity;

        fee = new DtoFeeForm();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(true);

        service.create(fee);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals(1, entity.getMemberId());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).getTime(), entity.getDate()
            .getTime());
        Assertions.assertEquals(true, entity.getPaid());
    }

    @Test
    @DisplayName("Returns the created data")
    public void testCreate_ReturnedData() {
        final MemberFee  result;
        final DtoFeeForm fee;

        fee = new DtoFeeForm();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(true);

        result = service.create(fee);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(1, result.getMemberId());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).getTime(), result.getDate()
            .getTime());
        Assertions.assertEquals(true, result.getPaid());
    }

}
