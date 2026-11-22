
package com.bernardomg.association.fee.member.service.unit;

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
@DisplayName("DefaultFeeSummaryService - getFeeSummary")
class TestFeeSummaryServiceGetFeeSummary {

    @Mock
    private FeeSummaryRepository     feeBalanceRepository;

    @InjectMocks
    private DefaultFeeSummaryService service;

    @Test
    @DisplayName("When there is data, then it is returned")
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
