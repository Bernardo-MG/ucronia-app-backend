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

package com.bernardomg.association.fee.test.usecase.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.association.fee.test.config.factory.FeeCalendars;
import com.bernardomg.association.fee.usecase.service.FeeCalendarService;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.member.test.config.data.annotation.AlternativeMember;
import com.bernardomg.association.member.test.config.data.annotation.NoSurnameMember;
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.association.member.test.config.factory.MemberCalendars;
import com.bernardomg.association.test.data.fee.annotation.AlternativeFeeFullYear;
import com.bernardomg.association.test.data.fee.annotation.FeeFullYear;
import com.bernardomg.association.test.data.fee.annotation.TwoFeeYearsConnected;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee calendar service - get year")
class ITFeeCalendarServiceGetYear {

    @Autowired
    private FeeCalendarService service;

    public ITFeeCalendarServiceGetYear() {
        super();
    }

    @Test
    @DisplayName("With a full year it returns all data")
    @ValidMember
    @FeeFullYear
    void testGetYear_FullYear() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .containsExactly(FeeCalendars.inactivefullCalendar());
    }

    @Test
    @DisplayName("With a full year it returns all the data")
    @ValidMember
    @AlternativeMember
    @FeeFullYear
    @AlternativeFeeFullYear
    void testGetYear_FullYear_TwoMembers() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .containsExactly(FeeCalendars.inactivefullCalendar(), FeeCalendars.inactivefullCalendarAlternative());
    }

    @Test
    @DisplayName("With user without surname it returns all data")
    @NoSurnameMember
    @FeeFullYear
    void testGetYear_NoSurname() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .containsExactly(FeeCalendars.inactivefullCalendarNoSurname());
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all data for the queried year")
    @ValidMember
    @TwoFeeYearsConnected
    void testGetYear_TwoConnectedYears_First() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR_PREVIOUS, MemberStatus.ALL, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .containsExactly(FeeCalendars.inactiveTwoConnectedFirst());
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all data for the queried year")
    @ValidMember
    @TwoFeeYearsConnected
    void testGetYear_TwoConnectedYears_Second() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .containsExactly(FeeCalendars.inactiveTwoConnectedSecond());
    }

}
