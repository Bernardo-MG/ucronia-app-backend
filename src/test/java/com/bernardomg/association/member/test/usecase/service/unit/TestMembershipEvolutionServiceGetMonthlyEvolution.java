
package com.bernardomg.association.member.test.usecase.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.time.ZoneOffset;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.filter.MembershipEvolutionQuery;
import com.bernardomg.association.member.domain.model.MembershipEvolutionMonth;
import com.bernardomg.association.member.domain.repository.MembershipEvolutionRepository;
import com.bernardomg.association.member.test.configuration.factory.MembershipEvolutionMonthConstants;
import com.bernardomg.association.member.test.configuration.factory.MembershipEvolutionMonths;
import com.bernardomg.association.member.test.configuration.factory.MembershipEvolutionQueries;
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
        final MembershipEvolutionQuery           query;
        final Iterable<MembershipEvolutionMonth> evolution;

        // GIVEN
        given(membershipEvolutionRepository.findInRange(eq(MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()), eq(
                MembershipEvolutionMonthConstants.CURRENT_MONTH.atDay(1)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant()),
            any())).willReturn(List.of(MembershipEvolutionMonths.currentMonth()));

        query = MembershipEvolutionQueries.previousAndThis();

        // WHEN
        evolution = service.getMonthlyEvolution(query);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .containsExactly(MembershipEvolutionMonths.currentMonth());
    }

    @Test
    @DisplayName("Can't read beyond the current month")
    void testGetMonthlyEvolution_LimitsAtCurrent() {
        final MembershipEvolutionQuery query;

        // GIVEN
        query = MembershipEvolutionQueries.aroundCurrent();

        // WHEN
        service.getMonthlyEvolution(query);

        // THEN
        Mockito.verify(membershipEvolutionRepository)
            .findInRange(eq(MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()), eq(
                    MembershipEvolutionMonthConstants.CURRENT_MONTH.atDay(1)
                        .atStartOfDay(ZoneOffset.UTC)
                        .toInstant()),
                any());
    }

    @Test
    @DisplayName("When there is no data nothing is returned")
    void testGetMonthlyEvolution_NoData() {
        final MembershipEvolutionQuery           query;
        final Iterable<MembershipEvolutionMonth> evolution;

        // GIVEN
        given(membershipEvolutionRepository.findInRange(eq(MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()), eq(
                MembershipEvolutionMonthConstants.CURRENT_MONTH.atDay(1)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant()),
            any())).willReturn(List.of());

        query = MembershipEvolutionQueries.previousAndThis();

        // WHEN
        evolution = service.getMonthlyEvolution(query);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .isEmpty();
    }

}
