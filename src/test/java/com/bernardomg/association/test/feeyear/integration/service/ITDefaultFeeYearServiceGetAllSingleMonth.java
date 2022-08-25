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

package com.bernardomg.association.test.feeyear.integration.service;

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.feeyear.model.FeeMonth;
import com.bernardomg.association.feeyear.model.FeeYear;
import com.bernardomg.association.feeyear.service.DefaultFeeYearService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default fee year service - get all - single month")
@Sql({ "/db/queries/member/single.sql", "/db/queries/fee/first_month.sql" })
public class ITDefaultFeeYearServiceGetAllSingleMonth {

    @Autowired
    private DefaultFeeYearService service;

    public ITDefaultFeeYearServiceGetAllSingleMonth() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testGetAll_Count() {
        final Iterable<? extends FeeYear> result;

        result = service.getAll(2020);

        Assertions.assertEquals(1, IterableUtils.size(result));
        Assertions.assertEquals(1, IterableUtils.size(result.iterator()
            .next()
            .getMonths()));
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final Iterator<? extends FeeYear> data;
        FeeYear                           result;
        Iterator<FeeMonth>                months;
        FeeMonth                          month;

        data = service.getAll(2020)
            .iterator();

        result = data.next();
        Assertions.assertEquals(1, result.getMemberId());
        Assertions.assertEquals("Member 1 Surname", result.getMember());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertEquals(true, result.getActive());

        months = result.getMonths()
            .iterator();

        month = months.next();
        Assertions.assertEquals(1, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());
    }

}
