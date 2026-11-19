
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.domain.model.FeeSummary;
import com.bernardomg.association.fee.domain.repository.FeeSummaryRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.AlternativePaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.profile.test.configuration.data.annotation.AlternativeProfile;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeSummaryRepository - findBetween")
class ITFeeSummaryRepositoryFindBetween {

    @Autowired
    private FeeSummaryRepository repository;

    @Test
    @DisplayName("When there is no data, then it returns nothing")
    void testFindBetween_NoData() {
        final FeeSummary summary;

        // WHEN
        summary = repository.findBetween(FeeConstants.MONTH_START, FeeConstants.MONTH_END);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(summary.paid())
                .as("paid fees")
                .isZero();
            softly.assertThat(summary.unpaid())
                .as("unpaid fees")
                .isZero();
        });
    }

    @Test
    @DisplayName("When there is a paid fee, for an active member, then the correct summary is returned")
    @PositiveFeeType
    @ValidProfile
    @PaidFee
    void testFindBetween_Paid() {
        final FeeSummary summary;

        // WHEN
        summary = repository.findBetween(FeeConstants.MONTH_START, FeeConstants.MONTH_END);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(summary.paid())
                .as("paid fees")
                .isEqualTo(1);
            softly.assertThat(summary.unpaid())
                .as("unpaid fees")
                .isZero();
        });
    }

    @Test
    @DisplayName("When there is a paid fee for a month without data, for an active member, then nothing is returned")
    @PositiveFeeType
    @ValidProfile
    @PaidFee
    void testFindBetween_Paid_WrongMonth() {
        final FeeSummary summary;

        // WHEN
        summary = repository.findBetween(FeeConstants.NEXT_MONTH_START, FeeConstants.NEXT_MONTH_END);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(summary.paid())
                .as("paid fees")
                .isZero();
            softly.assertThat(summary.unpaid())
                .as("unpaid fees")
                .isZero();
        });
    }

    @Test
    @DisplayName("When there is a paid fee, for an active member, then the correct summary is returned")
    @PositiveFeeType
    @ValidProfile
    @AlternativeProfile
    @NotPaidFee
    @AlternativePaidFee
    void testFindBetween_PaidAndNotPaid() {
        final FeeSummary summary;

        // WHEN
        summary = repository.findBetween(FeeConstants.MONTH_START, FeeConstants.MONTH_END);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(summary.paid())
                .as("paid fees")
                .isEqualTo(1);
            softly.assertThat(summary.unpaid())
                .as("unpaid fees")
                .isEqualTo(1);
        });
    }

    @Test
    @DisplayName("When there is an unpaid fee, for an active member, then the correct summary is returned")
    @PositiveFeeType
    @ValidProfile
    @NotPaidFee
    void testFindBetween_Unpaid() {
        final FeeSummary summary;

        // WHEN
        summary = repository.findBetween(FeeConstants.MONTH_START, FeeConstants.MONTH_END);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(summary.paid())
                .as("paid fees")
                .isZero();
            softly.assertThat(summary.unpaid())
                .as("unpaid fees")
                .isEqualTo(1);
        });
    }

    @Test
    @DisplayName("When there is an unpaid fee for a month without data, for an active member, then nothing is returned")
    @PositiveFeeType
    @ValidProfile
    @NotPaidFee
    void testFindBetween_Unpaid_WrongMonth() {
        final FeeSummary summary;

        // WHEN
        summary = repository.findBetween(FeeConstants.NEXT_MONTH_START, FeeConstants.NEXT_MONTH_END);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(summary.paid())
                .as("paid fees")
                .isZero();
            softly.assertThat(summary.unpaid())
                .as("unpaid fees")
                .isZero();
        });
    }

}
