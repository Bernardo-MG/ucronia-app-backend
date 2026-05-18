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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneOffset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.domain.model.FeeSummary;
import com.bernardomg.association.fee.domain.repository.FeeSummaryRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeSummaries;
import com.bernardomg.association.fee.usecase.service.DefaultFeeSummaryService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fee summary service - get fee summary")
class TestFeeSummaryServiceGetFeeSummary {

    @Mock
    private FeeSummaryRepository     feeBalanceRepository;

    @InjectMocks
    private DefaultFeeSummaryService service;

    public TestFeeSummaryServiceGetFeeSummary() {
        super();
    }

    @Test
    @DisplayName("When there is data it is returned")
    void testGetFeeSummary() {
        final FeeSummary summary;
        final FeeSummary read;
        final Instant    from;
        final Instant    to;

        // GIVEN
        from = LocalDate.of(2024, 2, 1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        to = LocalDate.of(2024, 2, 1)
            .withDayOfMonth(YearMonth.of(2024, 2)
                .lengthOfMonth())
            .atTime(LocalTime.MAX)
            .atZone(ZoneOffset.UTC)
            .toInstant();

        summary = FeeSummaries.both();
        given(feeBalanceRepository.findBetween(from, to)).willReturn(summary);

        // WHEN
        read = service.getFeeSummary(from, to);

        // THEN
        assertThat(read).as("summary")
            .isEqualTo(summary);
    }

}
