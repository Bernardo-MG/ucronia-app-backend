
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.FeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.member.domain.model.MembershipEvolutionMonth;
import com.bernardomg.association.member.domain.repository.MembershipEvolutionRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.factory.MembershipEvolutionMonthConstants;
import com.bernardomg.association.member.test.configuration.factory.MembershipEvolutionMonths;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("MembershipEvolutionRepository - find in range")
class ITMembershipEvolutionRepositoryFindInRange {

    @Autowired
    private MembershipEvolutionRepository repository;

    @Test
    @DisplayName("Can filter having only the end date")
    @PositiveFeeType
    @ActiveMember
    @FeeFullYear
    void testFindInRange_End() {
        final Sorting                              sorting;
        final Collection<MembershipEvolutionMonth> evolution;
        final Instant                              date;

        // GIVEN
        sorting = Sorting.unsorted();
        date = MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
            .plusMonths(2)
            .toInstant();

        // WHEN
        evolution = repository.findInRange(Optional.empty(), Optional.of(date), sorting);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .containsExactlyInAnyOrder(
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(1)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(2)
                    .toInstant()));
    }

    @Test
    @DisplayName("Returns all when not applying range")
    @PositiveFeeType
    @ActiveMember
    @FeeFullYear
    void testFindInRange_NoRange() {
        final Sorting                              sorting;
        final Collection<MembershipEvolutionMonth> evolution;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        evolution = repository.findInRange(Optional.empty(), Optional.empty(), sorting);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .containsExactlyInAnyOrder(
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(1)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(2)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(3)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(4)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(5)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(6)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(7)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(8)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(9)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(10)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(11)
                    .toInstant()));
    }

    @Test
    @DisplayName("When reading all with no data, nothing is returned")
    void testFindInRange_NoRange_NoData() {
        final Sorting                              sorting;
        final Collection<MembershipEvolutionMonth> evolution;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        evolution = repository.findInRange(Optional.empty(), Optional.empty(), sorting);

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
        evolution = repository.findInRange(Optional.empty(), Optional.empty(), sorting);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .isEmpty();
    }

    @Test
    @DisplayName("Can filter having in a range")
    @PositiveFeeType
    @ActiveMember
    @FeeFullYear
    void testFindInRange_Range() {
        final Sorting                              sorting;
        final Collection<MembershipEvolutionMonth> evolution;
        final Instant                              from;
        final Instant                              to;

        // GIVEN
        sorting = Sorting.unsorted();
        from = MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
            .plusMonths(1)
            .toInstant();
        to = MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
            .plusMonths(3)
            .toInstant();

        // WHEN
        evolution = repository.findInRange(Optional.of(from), Optional.of(to), sorting);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .containsExactlyInAnyOrder(
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(1)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(2)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(3)
                    .toInstant()));
    }

    @Test
    @DisplayName("Can filter having only the start date")
    @PositiveFeeType
    @ActiveMember
    @FeeFullYear
    void testFindInRange_Start() {
        final Sorting                              sorting;
        final Collection<MembershipEvolutionMonth> evolution;
        final Instant                              date;

        // GIVEN
        sorting = Sorting.unsorted();
        date = MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
            .plusMonths(1)
            .toInstant();

        // WHEN
        evolution = repository.findInRange(Optional.of(date), Optional.empty(), sorting);

        // THEN
        Assertions.assertThat(evolution)
            .as("evolution")
            .containsExactlyInAnyOrder(
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(1)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(2)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(3)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(4)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(5)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(6)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(7)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(8)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(9)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(10)
                    .toInstant()),
                MembershipEvolutionMonths.forMonth(MembershipEvolutionMonthConstants.START_MONTH.atZone(ZoneOffset.UTC)
                    .plusMonths(11)
                    .toInstant()));
    }

}
