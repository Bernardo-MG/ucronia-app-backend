/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.membership.test.calendar.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.membership.calendar.model.YearsRange;
import com.bernardomg.association.membership.calendar.service.MemberFeeCalendarService;
import com.bernardomg.association.membership.test.calendar.util.model.MemberCalendars;
import com.bernardomg.association.membership.test.fee.configuration.FeeFullYear;
import com.bernardomg.association.membership.test.fee.configuration.FeeFullYearAlternative;
import com.bernardomg.association.membership.test.fee.configuration.PaidFee;
import com.bernardomg.association.membership.test.fee.configuration.TwoFeeYearsConnected;
import com.bernardomg.association.membership.test.fee.configuration.TwoFeeYearsWithGap;
import com.bernardomg.association.membership.test.member.configuration.AlternativeMember;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee calendar service - get range")
class ITFeeCalendarServiceGetRange {

    @Autowired
    private MemberFeeCalendarService service;

    public ITFeeCalendarServiceGetRange() {
        super();
    }

    @Test
    @DisplayName("With a full year the year range is returned")
    @ValidMember
    @FeeFullYear
    void testGetRange_FullYear() {
        final YearsRange range;

        range = service.getRange();

        Assertions.assertThat(range.getYears())
            .containsOnly(MemberCalendars.YEAR);
    }

    @Test
    @DisplayName("With a full year and two members the year range is returned")
    @ValidMember
    @AlternativeMember
    @FeeFullYear
    @FeeFullYearAlternative
    void testGetRange_FullYear_TwoMembers() {
        final YearsRange range;

        range = service.getRange();

        Assertions.assertThat(range.getYears())
            .containsOnly(MemberCalendars.YEAR);
    }

    @Test
    @DisplayName("With no data the range is empty")
    void testGetRange_NoData() {
        final YearsRange range;

        range = service.getRange();

        Assertions.assertThat(range.getYears())
            .isEmpty();
    }

    @Test
    @DisplayName("With a single fee the year range is returned")
    @ValidMember
    @PaidFee
    void testGetRange_Single() {
        final YearsRange range;

        range = service.getRange();

        Assertions.assertThat(range.getYears())
            .containsOnly(MemberCalendars.YEAR);
    }

    @Test
    @DisplayName("With two years connected the year range is returned")
    @ValidMember
    @TwoFeeYearsConnected
    void testGetRange_TwoConnectedYears() {
        final YearsRange range;

        range = service.getRange();

        Assertions.assertThat(range.getYears())
            .containsExactly(MemberCalendars.YEAR_PREVIOUS, MemberCalendars.YEAR);
    }

    @Test
    @DisplayName("With two years with a gap the year range is returned")
    @ValidMember
    @TwoFeeYearsWithGap
    void testGetRange_TwoYearsWithGap() {
        final YearsRange range;

        range = service.getRange();

        Assertions.assertThat(range.getYears())
            .containsExactly(MemberCalendars.YEAR_TWO_PREVIOUS, MemberCalendars.YEAR);
    }

}
