/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

package com.bernardomg.transaction.fee.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.bernardomg.association.fee.domain.model.FeeMemberStatus;
import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.repository.FeeMemberRepository;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeCalendarConstants;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.test.configuration.factory.MemberConstants;
import com.bernardomg.association.fee.test.configuration.factory.MembersFees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fee service - get for year")
class TestFeeServiceGetForYear {

    @Mock
    private EventEmitter          eventEmitter;

    @Mock
    private FeeMemberRepository   feeMemberRepository;

    @Mock
    private FeeRepository         feeRepository;

    @Mock
    private MessageSource         messageSource;

    @InjectMocks
    private DefaultFeeService     service;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    @DisplayName("When filtering by active the correct query is used")
    void testGetForYear_Active() {
        final Iterable<MemberFees> calendars;
        final Sorting              sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        given(feeRepository.findAllInYearForActiveMembers(FeeCalendarConstants.CURRENT_YEAR, sorting))
            .willReturn(List.of(Fees.paidCurrentMonth()));

        // WHEN
        calendars = service.getForYear(FeeCalendarConstants.CURRENT_YEAR, FeeMemberStatus.ACTIVE, sorting);

        // THEN
        Assertions.assertThat(calendars)
            .containsExactly(MembersFees.activePaidCurrentMonth());
    }

    @Test
    @DisplayName("When filtering by all the correct query is used")
    void testGetForYear_All() {
        final Iterable<MemberFees> calendars;
        final Sorting              sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        given(feeRepository.findAllInYear(FeeCalendarConstants.CURRENT_YEAR, sorting))
            .willReturn(List.of(Fees.paidCurrentMonth()));
        given(feeMemberRepository.isActive(MemberConstants.NUMBER)).willReturn(true);

        // WHEN
        calendars = service.getForYear(FeeCalendarConstants.CURRENT_YEAR, FeeMemberStatus.ALL, sorting);

        // THEN
        Assertions.assertThat(calendars)
            .containsExactly(MembersFees.activePaidCurrentMonth());
    }

    @Test
    @DisplayName("When filtering by inactive the correct query is used")
    void testGetForYear_Inactive() {
        final Iterable<MemberFees> calendars;
        final Sorting              sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        given(feeRepository.findAllInYearForInactiveMembers(FeeCalendarConstants.CURRENT_YEAR, sorting))
            .willReturn(List.of(Fees.paidCurrentMonth()));

        // WHEN
        calendars = service.getForYear(FeeCalendarConstants.CURRENT_YEAR, FeeMemberStatus.INACTIVE, sorting);

        // THEN
        Assertions.assertThat(calendars)
            .containsExactly(MembersFees.inactivePaidCurrentMonth());
    }

}
