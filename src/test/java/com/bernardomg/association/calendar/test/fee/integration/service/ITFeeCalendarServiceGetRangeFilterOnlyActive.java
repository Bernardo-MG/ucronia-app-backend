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

package com.bernardomg.association.calendar.test.fee.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.calendar.fee.model.FeeCalendarRange;
import com.bernardomg.association.calendar.fee.service.FeeCalendarService;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Fee calendar service - get all - only active")
class ITFeeCalendarServiceGetRangeFilterOnlyActive {

    @Autowired
    private FeeCalendarService service;

    public ITFeeCalendarServiceGetRangeFilterOnlyActive() {
        super();
    }

    @Test
    @DisplayName("With an active member it returns the full range")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    void testGetRange_Active() {
        final FeeCalendarRange range;

        range = service.getRange(true);

        Assertions.assertThat(range.getStart())
            .isEqualTo(2020);
        Assertions.assertThat(range.getEnd())
            .isEqualTo(2020);
    }

    @Test
    @DisplayName("With an inactive member it returns no range")
    @Sql({ "/db/queries/member/inactive.sql", "/db/queries/fee/full_year.sql" })
    void testGetRange_Inactive() {
        final FeeCalendarRange range;

        range = service.getRange(true);

        Assertions.assertThat(range.getStart())
            .isZero();
        Assertions.assertThat(range.getEnd())
            .isZero();
    }

    @Test
    @DisplayName("With no data it returns no range")
    void testGetRange_NoData() {
        final FeeCalendarRange range;

        range = service.getRange(true);

        Assertions.assertThat(range.getStart())
            .isZero();
        Assertions.assertThat(range.getEnd())
            .isZero();
    }

}
