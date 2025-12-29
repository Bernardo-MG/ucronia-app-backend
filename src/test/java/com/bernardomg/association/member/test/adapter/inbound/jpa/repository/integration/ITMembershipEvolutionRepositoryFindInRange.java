
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import java.time.ZoneOffset;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.test.configuration.data.annotation.FeeFullYear;
import com.bernardomg.association.member.domain.model.MembershipEvolutionMonth;
import com.bernardomg.association.member.domain.repository.MembershipEvolutionRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.factory.MembershipEvolutionMonthConstants;
import com.bernardomg.association.member.test.configuration.factory.MembershipEvolutionMonths;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MembershipEvolutionRepository - find in range")
class ITMembershipEvolutionRepositoryFindInRange {

    @Autowired
    private MembershipEvolutionRepository repository;

    @Test
    @DisplayName("Can filter having only the end date")
    @ActiveMember
    @FeeFullYear
    void testFindInRange_End() {
        final Sorting                              sorting;
        final Collection<MembershipEvolutionMonth> evolution;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        evolution = repository.findInRange(null, MembershipEvolutionMonthConstants.START_MONTH.plusMonths(2)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(), sorting);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .containsExactlyInAnyOrder(
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(1)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(2)));
    }

    @Test
    @DisplayName("Returns all when not applying range")
    @ActiveMember
    @FeeFullYear
    void testFindInRange_NoRange() {
        final Sorting                              sorting;
        final Collection<MembershipEvolutionMonth> evolution;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        evolution = repository.findInRange(null, null, sorting);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .containsExactlyInAnyOrder(
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(1)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(2)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(3)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(4)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(5)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(6)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(7)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(8)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(9)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(10)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(11)));
    }

    @Test
    @DisplayName("When reading all with no data, nothing is returned")
    void testFindInRange_NoRange_NoData() {
        final Sorting                              sorting;
        final Collection<MembershipEvolutionMonth> evolution;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        evolution = repository.findInRange(null, null, sorting);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .isEmpty();
    }

    @Test
    @DisplayName("When reading all with a profile with no member role, nothing is returned")
    @ValidProfile
    void testFindInRange_NoRange_NoMember() {
        final Sorting                              sorting;
        final Collection<MembershipEvolutionMonth> evolution;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        evolution = repository.findInRange(null, null, sorting);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .isEmpty();
    }

    @Test
    @DisplayName("Can filter having in a range")
    @ActiveMember
    @FeeFullYear
    void testFindInRange_Range() {
        final Sorting                              sorting;
        final Collection<MembershipEvolutionMonth> evolution;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        evolution = repository.findInRange(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(1)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            MembershipEvolutionMonthConstants.START_MONTH.plusMonths(3)
                .atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant(),
            sorting);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .containsExactlyInAnyOrder(
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(1)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(2)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(3)));
    }

    @Test
    @DisplayName("Can filter having only the start date")
    @ActiveMember
    @FeeFullYear
    void testFindInRange_Start() {
        final Sorting                              sorting;
        final Collection<MembershipEvolutionMonth> evolution;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        evolution = repository.findInRange(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(1)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(), null, sorting);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .containsExactlyInAnyOrder(
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(1)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(2)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(3)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(4)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(5)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(6)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(7)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(8)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(9)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(10)),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.plusMonths(11)));
    }

}
