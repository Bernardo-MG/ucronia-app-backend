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

package com.bernardomg.association.test.fee.calendar.integration.service;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.calendar.model.UserFeeCalendar;
import com.bernardomg.association.fee.calendar.service.FeeCalendarService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee calendar service - get all - filter by only active status")
public class ITFeeCalendarServiceGetAllFilterOnlyActive {

    @Autowired
    private FeeCalendarService service;

    public ITFeeCalendarServiceGetAllFilterOnlyActive() {
        super();
    }

    @Test
    @DisplayName("Returns all the data for an active user")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    public void testGetAllActive_Count() {
        final Iterable<UserFeeCalendar> result;
        final Sort                      sort;

        sort = Sort.unsorted();

        result = service.getAll(2020, true, sort);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
        Assertions.assertThat(IterableUtils.size(result.iterator()
            .next()
            .getMonths()))
            .isEqualTo(12);
    }

    @Test
    @DisplayName("Returns no data for an inactive user")
    @Sql({ "/db/queries/member/inactive.sql", "/db/queries/fee/full_year.sql" })
    public void testGetAllInactive_Count() {
        final Iterable<UserFeeCalendar> result;
        final Sort                      sort;

        sort = Sort.unsorted();

        result = service.getAll(2020, true, sort);

        Assertions.assertThat(IterableUtils.size(result))
            .isZero();
    }

}
