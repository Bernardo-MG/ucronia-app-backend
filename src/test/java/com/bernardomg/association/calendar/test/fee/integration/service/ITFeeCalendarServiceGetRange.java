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
@DisplayName("Fee calendar service - get all")
class ITFeeCalendarServiceGetRange {

    @Autowired
    private FeeCalendarService service;

    public ITFeeCalendarServiceGetRange() {
        super();
    }

    @Test
    @DisplayName("With a full year the year range is returned")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    void testGetRange_FullYear() {
        final FeeCalendarRange range;

        range = service.getRange(false);

        Assertions.assertThat(range.getYears())
            .containsOnly(2020);
    }

    @Test
    @DisplayName("With a full year and two members the year range is returned")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/member/alternative.sql", "/db/queries/fee/full_year.sql",
            "/db/queries/fee/full_year_alternative.sql" })
    void testGetRange_FullYear_TwoMembers() {
        final FeeCalendarRange range;

        range = service.getRange(false);

        Assertions.assertThat(range.getYears())
            .containsOnly(2020);
    }

    @Test
    @DisplayName("With an inactive member it returns the range")
    @Sql({ "/db/queries/member/inactive.sql", "/db/queries/fee/full_year.sql" })
    void testGetRange_Inactive() {
        final FeeCalendarRange range;

        range = service.getRange(false);

        Assertions.assertThat(range.getYears())
            .containsOnly(2020);
    }

    @Test
    @DisplayName("With no data the range is empty")
    void testGetRange_NoData() {
        final FeeCalendarRange range;

        range = service.getRange(false);

        Assertions.assertThat(range.getYears())
            .isEmpty();
    }

    @Test
    @DisplayName("With a single fee the year range is returned")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
    void testGetRange_Single() {
        final FeeCalendarRange range;

        range = service.getRange(false);

        Assertions.assertThat(range.getYears())
            .containsOnly(2020);
    }

    @Test
    @DisplayName("With two years connected the year range is returned")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_connected.sql" })
    void testGetRange_TwoConnectedYears() {
        final FeeCalendarRange range;

        range = service.getRange(false);

        Assertions.assertThat(range.getYears())
            .containsExactly(2019, 2020);
    }

    @Test
    @DisplayName("With two years with a gap the year range is returned")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_gap.sql" })
    void testGetRange_TwoYearsWithGap() {
        final FeeCalendarRange range;

        range = service.getRange(false);

        Assertions.assertThat(range.getYears())
            .containsExactly(2018, 2020);
    }

}
