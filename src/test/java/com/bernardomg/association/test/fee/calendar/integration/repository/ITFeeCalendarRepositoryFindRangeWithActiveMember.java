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

package com.bernardomg.association.test.fee.calendar.integration.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.calendar.model.FeeCalendarRange;
import com.bernardomg.association.fee.calendar.persistence.repository.FeeCalendarRepository;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee calendar repository - find range with active member")
public class ITFeeCalendarRepositoryFindRangeWithActiveMember {

    @Autowired
    private FeeCalendarRepository repository;

    public ITFeeCalendarRepositoryFindRangeWithActiveMember() {
        super();
    }

    @Test
    @DisplayName("With a full year the year range is returned")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    public void testFindRange_FullYear() {
        final FeeCalendarRange result;

        result = repository.findRangeWithActiveMember();

        Assertions.assertThat(result.getStart())
            .isEqualTo(2020);
        Assertions.assertThat(result.getEnd())
            .isEqualTo(2020);
    }

    @Test
    @DisplayName("With a full year and two members the year range is returned")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/member/alternative.sql", "/db/queries/fee/full_year.sql",
            "/db/queries/fee/full_year_alternative.sql" })
    public void testFindRange_FullYear_TwoMembers() {
        final FeeCalendarRange result;

        result = repository.findRangeWithActiveMember();

        Assertions.assertThat(result.getStart())
            .isEqualTo(2020);
        Assertions.assertThat(result.getEnd())
            .isEqualTo(2020);
    }

    @Test
    @DisplayName("With a single fee the year range is returned")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
    public void testFindRange_Single() {
        final FeeCalendarRange result;

        result = repository.findRangeWithActiveMember();

        Assertions.assertThat(result.getStart())
            .isEqualTo(2020);
        Assertions.assertThat(result.getEnd())
            .isEqualTo(2020);
    }

    @Test
    @DisplayName("With two years connected the year range is returned")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_connected.sql" })
    public void testFindRange_TwoConnectedYears() {
        final FeeCalendarRange result;

        result = repository.findRangeWithActiveMember();

        Assertions.assertThat(result.getStart())
            .isEqualTo(2019);
        Assertions.assertThat(result.getEnd())
            .isEqualTo(2020);
    }

    @Test
    @DisplayName("With two years with a gap the year range is returned")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_gap.sql" })
    public void testFindRange_TwoYearsWithGap() {
        final FeeCalendarRange result;

        result = repository.findRangeWithActiveMember();

        Assertions.assertThat(result.getStart())
            .isEqualTo(2018);
        Assertions.assertThat(result.getEnd())
            .isEqualTo(2020);
    }

}
