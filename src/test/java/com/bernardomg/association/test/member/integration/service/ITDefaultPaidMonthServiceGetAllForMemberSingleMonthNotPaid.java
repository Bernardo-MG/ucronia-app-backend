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

package com.bernardomg.association.test.member.integration.service;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.PaidMonth;
import com.bernardomg.association.member.service.DefaultPaidMonthService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default paid month service - get all for member")
@Sql({ "/db/queries/member_period/one_month.sql" })
public class ITDefaultPaidMonthServiceGetAllForMemberSingleMonthNotPaid {

    @Autowired
    private DefaultPaidMonthService service;

    public ITDefaultPaidMonthServiceGetAllForMemberSingleMonthNotPaid() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities for a member")
    public void testGetAll_Count() {
        final Iterable<? extends PaidMonth> result;

        result = service.getAllForMember(1L);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final PaidMonth result;

        result = service.getAllForMember(1L)
            .iterator()
            .next();

        Assertions.assertEquals(-1, result.getId());
        Assertions.assertEquals(1, result.getMember());
        Assertions.assertEquals(1, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertFalse(result.getPaid());
    }

}
