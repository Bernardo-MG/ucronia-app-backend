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

package com.bernardomg.association.test.paidmonth.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.paidmonth.model.DtoPaidMonth;
import com.bernardomg.association.paidmonth.model.PaidMonth;
import com.bernardomg.association.paidmonth.model.PersistentPaidMonth;
import com.bernardomg.association.paidmonth.repository.PaidMonthRepository;
import com.bernardomg.association.paidmonth.service.DefaultPaidMonthService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default paid month service - create")
@Sql({ "/db/queries/member/single.sql" })
public class ITDefaultPaidMonthServiceCreate {

    @Autowired
    private PaidMonthRepository     repository;

    @Autowired
    private DefaultPaidMonthService service;

    public ITDefaultPaidMonthServiceCreate() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when creating")
    public void testCreate_AddsEntity() {
        final DtoPaidMonth month;

        month = new DtoPaidMonth();
        month.setMember(1L);
        month.setMonth(2);
        month.setYear(3);

        service.create(1L, month);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Persists the data")
    public void testCreate_PersistedData() {
        final DtoPaidMonth        month;
        final PersistentPaidMonth entity;

        month = new DtoPaidMonth();
        month.setMember(1L);
        month.setMonth(2);
        month.setYear(3);

        service.create(1L, month);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals(1, entity.getMember());
        Assertions.assertEquals(2, entity.getMonth());
        Assertions.assertEquals(3, entity.getYear());
    }

    @Test
    @DisplayName("Returns the created data")
    public void testCreate_ReturnedData() {
        final PaidMonth    result;
        final DtoPaidMonth month;

        month = new DtoPaidMonth();
        month.setMember(1L);
        month.setMonth(2);
        month.setYear(3);

        result = service.create(1L, month);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(1, result.getMember());
        Assertions.assertEquals(2, result.getMonth());
        Assertions.assertEquals(3, result.getYear());
    }

}
