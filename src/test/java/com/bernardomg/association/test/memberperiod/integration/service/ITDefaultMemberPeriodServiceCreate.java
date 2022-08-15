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

package com.bernardomg.association.test.memberperiod.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.memberperiod.model.DtoMemberPeriod;
import com.bernardomg.association.memberperiod.model.MemberPeriod;
import com.bernardomg.association.memberperiod.model.PersistentMemberPeriod;
import com.bernardomg.association.memberperiod.repository.MemberPeriodRepository;
import com.bernardomg.association.memberperiod.service.DefaultMemberPeriodService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default member period service - create")
@Sql({ "/db/queries/member/single.sql" })
public class ITDefaultMemberPeriodServiceCreate {

    @Autowired
    private MemberPeriodRepository     repository;

    @Autowired
    private DefaultMemberPeriodService service;

    public ITDefaultMemberPeriodServiceCreate() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when creating")
    public void testCreate_AddsEntity() {
        final DtoMemberPeriod period;

        period = new DtoMemberPeriod();
        period.setMember(1L);
        period.setStartMonth(2);
        period.setStartYear(3);
        period.setEndMonth(4);
        period.setEndYear(5);

        service.create(1L, period);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Persists the data")
    public void testCreate_PersistedData() {
        final DtoMemberPeriod        period;
        final PersistentMemberPeriod entity;

        period = new DtoMemberPeriod();
        period.setMember(1L);
        period.setStartMonth(2);
        period.setStartYear(3);
        period.setEndMonth(4);
        period.setEndYear(5);

        service.create(1L, period);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals(1, entity.getMember());
        Assertions.assertEquals(2, entity.getStartMonth());
        Assertions.assertEquals(3, entity.getStartYear());
        Assertions.assertEquals(4, entity.getEndMonth());
        Assertions.assertEquals(5, entity.getEndYear());
    }

    @Test
    @DisplayName("Returns the created data")
    public void testCreate_ReturnedData() {
        final MemberPeriod    result;
        final DtoMemberPeriod period;

        period = new DtoMemberPeriod();
        period.setMember(1L);
        period.setStartMonth(2);
        period.setStartYear(3);
        period.setEndMonth(4);
        period.setEndYear(5);

        result = service.create(1L, period);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(1, result.getMember());
        Assertions.assertEquals(2, result.getStartMonth());
        Assertions.assertEquals(3, result.getStartYear());
        Assertions.assertEquals(4, result.getEndMonth());
        Assertions.assertEquals(5, result.getEndYear());
    }

}
