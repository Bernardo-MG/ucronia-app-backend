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

package com.bernardomg.association.fee.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.association.fee.domain.repository.ActiveMemberRepository;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.factory.FeeCalendars;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeCalendarService;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.member.test.config.factory.MemberCalendars;
import com.bernardomg.association.member.test.config.factory.PersonConstants;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fee calendar service - get year")
class TestFeeCalendarServiceGetYear {

    @Mock
    private ActiveMemberRepository    activeMemberRepository;

    @Mock
    private FeeRepository             feeRepository;

    @InjectMocks
    private DefaultFeeCalendarService service;

    @Test
    @DisplayName("When filtering by active the correct query is used")
    void testGetYear_Active() {
        final Sort                  sort;
        final Iterable<FeeCalendar> calendars;

        // GIVEN
        sort = Sort.unsorted();

        given(feeRepository.findAllForActiveMembers(MemberCalendars.YEAR_CURRENT, sort))
            .willReturn(List.of(Fees.paidCurrentMonth()));
        given(activeMemberRepository.isActive(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR_CURRENT, MemberStatus.ACTIVE, sort);

        // THEN
        Assertions.assertThat(calendars)
            .containsExactly(FeeCalendars.activePaidCurrentMonth());
    }

    @Test
    @DisplayName("When filtering by all the correct query is used")
    void testGetYear_All() {
        final Sort                  sort;
        final Iterable<FeeCalendar> calendars;

        // GIVEN
        sort = Sort.unsorted();

        given(feeRepository.findAllInYear(MemberCalendars.YEAR_CURRENT, sort))
            .willReturn(List.of(Fees.paidCurrentMonth()));
        given(activeMemberRepository.isActive(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR_CURRENT, MemberStatus.ALL, sort);

        // THEN
        Assertions.assertThat(calendars)
            .containsExactly(FeeCalendars.activePaidCurrentMonth());
    }

    @Test
    @DisplayName("When filtering by inactive the correct query is used")
    void testGetYear_Inactive() {
        final Sort                  sort;
        final Iterable<FeeCalendar> calendars;

        // GIVEN
        sort = Sort.unsorted();

        given(feeRepository.findAllForInactiveMembers(MemberCalendars.YEAR_CURRENT, sort))
            .willReturn(List.of(Fees.paidCurrentMonth()));
        given(activeMemberRepository.isActive(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR_CURRENT, MemberStatus.INACTIVE, sort);

        // THEN
        Assertions.assertThat(calendars)
            .containsExactly(FeeCalendars.activePaidCurrentMonth());
    }

}
