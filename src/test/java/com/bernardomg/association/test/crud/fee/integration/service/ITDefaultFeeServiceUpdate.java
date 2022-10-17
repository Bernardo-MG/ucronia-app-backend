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

package com.bernardomg.association.test.crud.fee.integration.service;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.crud.fee.model.DtoFeeForm;
import com.bernardomg.association.crud.fee.model.MemberFee;
import com.bernardomg.association.crud.fee.model.PersistentFee;
import com.bernardomg.association.crud.fee.repository.FeeRepository;
import com.bernardomg.association.crud.fee.service.DefaultFeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default fee service - update")
@Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
public class ITDefaultFeeServiceUpdate {

    @Autowired
    private FeeRepository     repository;

    @Autowired
    private DefaultFeeService service;

    public ITDefaultFeeServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("Adds no entity when updating")
    public void testUpdate_AddsNoEntity() {
        final DtoFeeForm fee;

        fee = new DtoFeeForm();
        fee.setId(1L);
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(false);

        service.update(1L, fee);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Updates persisted data")
    public void testUpdate_PersistedData() {
        final DtoFeeForm    fee;
        final PersistentFee entity;

        fee = new DtoFeeForm();
        fee.setId(1L);
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(false);

        service.update(1L, fee);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals(1, entity.getMemberId());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), entity.getDate()
            .toInstant());
        Assertions.assertEquals(false, entity.getPaid());
    }

    @Test
    @DisplayName("Returns the updated data")
    public void testUpdate_ReturnedData() {
        final DtoFeeForm fee;
        final MemberFee  result;

        fee = new DtoFeeForm();
        fee.setId(1L);
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(false);

        result = service.update(1L, fee);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(1, result.getMemberId());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), result.getDate()
            .toInstant());
        Assertions.assertEquals(false, result.getPaid());
    }

}
