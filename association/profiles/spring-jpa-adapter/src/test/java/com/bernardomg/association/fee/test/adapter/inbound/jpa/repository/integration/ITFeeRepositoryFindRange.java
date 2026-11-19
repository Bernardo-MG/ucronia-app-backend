
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.time.Year;
import java.time.ZoneOffset;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.AlternativeFeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.FeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.initializer.FeeInitializer;
import com.bernardomg.association.member.domain.model.YearsRange;
import com.bernardomg.association.member.test.configuration.factory.MemberCalendarConstants;
import com.bernardomg.association.profile.test.configuration.data.annotation.AlternativeProfile;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeRepository - findRange")
class ITFeeRepositoryFindRange {

    @Autowired
    private FeeInitializer feeInitializer;

    @Autowired
    private FeeRepository  repository;

    @Test
    @DisplayName("When there is a full year, then the year range is returned")
    @PositiveFeeType
    @ValidProfile
    @FeeFullYear
    void testFindRange_FullYear() {
        final YearsRange range;

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .containsOnly(MemberCalendarConstants.YEAR);
    }

    @Test
    @DisplayName("When there is a full year and two members, then the year range is returned")
    @PositiveFeeType
    @ValidProfile
    @AlternativeProfile
    @FeeFullYear
    @AlternativeFeeFullYear
    void testFindRange_FullYear_TwoMembers() {
        final YearsRange range;

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .containsOnly(MemberCalendarConstants.YEAR);
    }

    @Test
    @DisplayName("When there is a not paid fee for the next year, then nothing is returned")
    @PositiveFeeType
    @ValidProfile
    void testFindRange_NextYear_NotPaid() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeeNextYear(false);

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .containsOnly(FeeConstants.NEXT_YEAR);
    }

    @Test
    @DisplayName("When there is a paid fee for the next year, then nothing is returned")
    @PositiveFeeType
    @ValidProfile
    void testFindRange_NextYear_Paid() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeeNextYear(true);

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .containsOnly(FeeConstants.NEXT_YEAR);
    }

    @Test
    @DisplayName("When there is no data, then the range is empty")
    void testFindRange_NoData() {
        final YearsRange range;

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is no fee, then it returns nothing")
    @PositiveFeeType
    @ValidProfile
    void testFindRange_NoFee() {
        final YearsRange range;

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is no fees, then the range is empty")
    @PositiveFeeType
    @ValidProfile
    void testFindRange_NoFees() {
        final YearsRange range;

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a not paid fee, then the year range is returned")
    @PositiveFeeType
    @ValidProfile
    void testFindRange_NotPaid() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .containsOnly(FeeConstants.CURRENT_YEAR);
    }

    @Test
    @DisplayName("When there is a paid fee, then the year range is returned")
    @PositiveFeeType
    @ValidProfile
    void testFindRange_Paid() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .containsOnly(FeeConstants.CURRENT_YEAR);
    }

    @Test
    @DisplayName("When there is a not paid fee, then the year range is returned")
    @PositiveFeeType
    @ValidProfile
    void testFindRange_PreviousYear_NotPaid() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeePreviousYear(false);

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .containsOnly(FeeConstants.PREVIOUS_YEAR);
    }

    @Test
    @DisplayName("When there is a paid fee, then the year range is returned")
    @PositiveFeeType
    @ValidProfile
    void testFindRange_PreviousYear_Paid() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeePreviousYear(true);

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .containsOnly(FeeConstants.PREVIOUS_YEAR);
    }

    @Test
    @DisplayName("When there is two years connected, then the year range is returned")
    @PositiveFeeType
    @ValidProfile
    void testFindRange_TwoConnectedYears() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeePreviousYear(true);
        feeInitializer.registerFeeCurrentMonth(true);

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .containsExactly(FeeConstants.PREVIOUS_YEAR, FeeConstants.CURRENT_YEAR);
    }

    @Test
    @DisplayName("When there is two years connected and not in order, then the year range is returned")
    @PositiveFeeType
    @ValidProfile
    void testFindRange_TwoConnectedYears_NotInOrder() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);
        feeInitializer.registerFeePreviousYear(true);

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .containsExactly(FeeConstants.PREVIOUS_YEAR, FeeConstants.CURRENT_YEAR);
    }

    @Test
    @DisplayName("When there is two years with a gap, then the year range is returned")
    @PositiveFeeType
    @ValidProfile
    void testFindRange_TwoYearsWithGap() {
        final YearsRange range;

        // GIVEN
        feeInitializer.registerFeeTwoYearsBack(true);
        feeInitializer.registerFeeCurrentMonth(true);

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.years())
            .as("year range")
            .containsExactly(Year.of(FeeConstants.TWO_YEARS_BACK.atZone(ZoneOffset.UTC)
                .getYear()), FeeConstants.CURRENT_YEAR);
    }

}
