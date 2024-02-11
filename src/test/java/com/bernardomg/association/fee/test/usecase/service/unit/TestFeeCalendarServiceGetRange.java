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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;
import com.bernardomg.association.fee.domain.repository.ActiveMemberRepository;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.factory.FeeCalendarYearsRanges;
import com.bernardomg.association.fee.usecase.service.DefaultFeeCalendarService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fee calendar service - get range")
class TestFeeCalendarServiceGetRange {

    @Mock
    private ActiveMemberRepository    activeMemberRepository;

    @Mock
    private FeeRepository             feeRepository;

    @InjectMocks
    private DefaultFeeCalendarService service;

    public TestFeeCalendarServiceGetRange() {
        super();
    }

    @Test
    @DisplayName("When there is data it is returned")
    void testGetRange() {
        final FeeCalendarYearsRange range;

        // GIVEN
        given(feeRepository.findRange()).willReturn(FeeCalendarYearsRanges.current());

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range)
            .as("year range")
            .isEqualTo(FeeCalendarYearsRanges.current());
    }

}
