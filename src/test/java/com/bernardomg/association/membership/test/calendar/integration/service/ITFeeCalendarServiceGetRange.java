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
import com.bernardomg.association.membership.test.calendar.config.factory.MemberCalendars;
import com.bernardomg.association.membership.test.fee.config.AlternativeFeeFullYear;
import com.bernardomg.association.membership.test.fee.config.FeeFullYear;
import com.bernardomg.association.membership.test.fee.config.factory.FeeConstants;
import com.bernardomg.association.membership.test.fee.util.initializer.FeeInitializer;
import com.bernardomg.association.membership.test.member.configuration.AlternativeMember;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee calendar service - get range")
class ITFeeCalendarServiceGetRange {

    @Autowired
    private FeeInitializer           feeInitializer;

    @Autowired
    private MemberFeeCalendarService service;

    public ITFeeCalendarServiceGetRange() {
        super();
    }

    @Test
    @DisplayName("With a full year, the year range is returned")
    @ValidMember
    @FeeFullYear
    void testGetRange_FullYear() {
        final YearsRange range;

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range.getYears())
            .as("year range")
            .containsOnly(MemberCalendars.YEAR);
    }

    @Test
    @DisplayName("With a full year and two members, the year range is returned")
    @ValidMember
    @AlternativeMember
    @FeeFullYear
    @AlternativeFeeFullYear
    void testGetRange_FullYear_TwoMembers() {
        final YearsRange range;

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range.getYears())
            .as("year range")
            .containsOnly(MemberCalendars.YEAR);
    }

    @Test
    @DisplayName("With a not paid fee for the next year, nothing is returned")
    @ValidMember
    void testGetRange_NextYear_NotPaid() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeeNextYear(false);

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range.getYears())
            .as("year range")
            .containsOnly(FeeConstants.NEXT_YEAR.getValue());
    }

    @Test
    @DisplayName("With a paid fee for the next year, nothing is returned")
    @ValidMember
    void testGetRange_NextYear_Paid() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeeNextYear(true);

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range.getYears())
            .as("year range")
            .containsOnly(FeeConstants.NEXT_YEAR.getValue());
    }

    @Test
    @DisplayName("With no data, the range is empty")
    void testGetRange_NoData() {
        final YearsRange range;

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range.getYears())
            .as("year range")
            .isEmpty();
    }

    @Test
    @DisplayName("With no fees, the range is empty")
    @ValidMember
    void testGetRange_NoFees() {
        final YearsRange range;

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range.getYears())
            .as("year range")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee, the year range is returned")
    @ValidMember
    void testGetRange_NotPaid() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range.getYears())
            .as("year range")
            .containsOnly(FeeConstants.CURRENT_YEAR.getValue());
    }

    @Test
    @DisplayName("With a paid fee, the year range is returned")
    @ValidMember
    void testGetRange_Paid() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range.getYears())
            .as("year range")
            .containsOnly(FeeConstants.CURRENT_YEAR.getValue());
    }

    @Test
    @DisplayName("With a not paid fee, the year range is returned")
    @ValidMember
    void testGetRange_PreviousYear_NotPaid() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeePreviousYear(false);

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range.getYears())
            .as("year range")
            .containsOnly(FeeConstants.PREVIOUS_YEAR.getValue());
    }

    @Test
    @DisplayName("With a paid fee, the year range is returned")
    @ValidMember
    void testGetRange_PreviousYear_Paid() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeePreviousYear(true);

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range.getYears())
            .as("year range")
            .containsOnly(FeeConstants.PREVIOUS_YEAR.getValue());
    }

    @Test
    @DisplayName("With two years connected, the year range is returned")
    @ValidMember
    void testGetRange_TwoConnectedYears() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeePreviousYear(true);
        feeInitializer.registerFeeCurrentMonth(true);

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range.getYears())
            .as("year range")
            .containsExactly(FeeConstants.PREVIOUS_YEAR.getValue(), FeeConstants.CURRENT_YEAR.getValue());
    }

    @Test
    @DisplayName("With two years connected and not in order, the year range is returned")
    @ValidMember
    void testGetRange_TwoConnectedYears_NotInOrder() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);
        feeInitializer.registerFeePreviousYear(true);

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range.getYears())
            .as("year range")
            .containsExactly(FeeConstants.PREVIOUS_YEAR.getValue(), FeeConstants.CURRENT_YEAR.getValue());
    }

    @Test
    @DisplayName("With two years with a gap, the year range is returned")
    @ValidMember
    void testGetRange_TwoYearsWithGap() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeeTwoYearsBack(true);
        feeInitializer.registerFeeCurrentMonth(true);

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range.getYears())
            .as("year range")
            .containsExactly(FeeConstants.TWO_YEARS_BACK.getYear(), FeeConstants.CURRENT_YEAR.getValue());
    }

}
