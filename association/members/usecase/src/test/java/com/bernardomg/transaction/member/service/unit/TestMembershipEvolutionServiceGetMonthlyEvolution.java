
package com.bernardomg.transaction.member.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.filter.MembershipEvolutionFilter;
import com.bernardomg.association.member.domain.model.MembershipEvolutionMonth;
import com.bernardomg.association.member.domain.repository.MembershipEvolutionRepository;
import com.bernardomg.association.member.test.configuration.factory.MembershipEvolutionFilters;
import com.bernardomg.association.member.test.configuration.factory.MembershipEvolutionMonthConstants;
import com.bernardomg.association.member.test.configuration.factory.MembershipEvolutionMonths;
import com.bernardomg.association.member.usecase.service.DefaultMembershipEvolutionService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Membership evolution service - get monthly evolution")
class TestMembershipEvolutionServiceGetMonthlyEvolution {

    @Mock
    private MembershipEvolutionRepository     membershipEvolutionRepository;

    @InjectMocks
    private DefaultMembershipEvolutionService service;

    @Test
    @DisplayName("Returns the queried data when covering previous and current")
    void testGetMonthlyEvolution_CoversBoth() {
        final MembershipEvolutionFilter          filter;
        final Iterable<MembershipEvolutionMonth> evolution;
        final Instant                            from;
        final Instant                            to;

        // GIVEN
        from = MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        to = MembershipEvolutionMonthConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        given(membershipEvolutionRepository.findInRange(eq(Optional.of(from)), eq(Optional.of(to)), any()))
            .willReturn(List.of(MembershipEvolutionMonths.currentMonth()));

        filter = MembershipEvolutionFilters.previousAndThis();

        // WHEN
        evolution = service.getMonthlyEvolution(filter);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .containsExactly(MembershipEvolutionMonths.currentMonth());
    }

    @Test
    @DisplayName("Can't read beyond the current month")
    void testGetMonthlyEvolution_LimitsAtCurrent() {
        final MembershipEvolutionFilter filter;
        final Instant                   from;
        final Instant                   to;

        // GIVEN
        filter = MembershipEvolutionFilters.aroundCurrent();

        // WHEN
        service.getMonthlyEvolution(filter);

        // THEN
        from = MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        to = MembershipEvolutionMonthConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        Mockito.verify(membershipEvolutionRepository)
            .findInRange(eq(Optional.of(from)), eq(Optional.of(to)), any());
    }

    @Test
    @DisplayName("When there is no data nothing is returned")
    void testGetMonthlyEvolution_NoData() {
        final MembershipEvolutionFilter          filter;
        final Iterable<MembershipEvolutionMonth> evolution;
        final Instant                            from;
        final Instant                            to;

        // GIVEN
        from = MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        to = MembershipEvolutionMonthConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        given(membershipEvolutionRepository.findInRange(eq(Optional.of(from)), eq(Optional.of(to)), any()))
            .willReturn(List.of());

        filter = MembershipEvolutionFilters.previousAndThis();

        // WHEN
        evolution = service.getMonthlyEvolution(filter);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .isEmpty();
    }

}
