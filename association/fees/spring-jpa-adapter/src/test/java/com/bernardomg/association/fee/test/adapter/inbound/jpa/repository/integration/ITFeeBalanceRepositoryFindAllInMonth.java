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
@DisplayName("FeeSummaryRepository - find all in month")
class ITFeeSummaryRepositoryFindForMonth {

    @Autowired
    private FeeSummaryRepository repository;

    @Test
    @DisplayName("With no data, it returns nothing")
    void testFindForMonth_NoData() {
        final FeeSummary summary;

        // WHEN
        summary = repository.findForMonth(FeeConstants.DATE);

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
    @DisplayName("With a paid fee, for an active member, the correct summary is returned")
    @PositiveFeeType
    @ValidProfile
    @PaidFee
    void testFindForMonth_Paid() {
        final FeeSummary summary;

        // WHEN
        summary = repository.findForMonth(FeeConstants.DATE);

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
    @DisplayName("With a paid fee for a month without data, for an active member, nothing is returned")
    @PositiveFeeType
    @ValidProfile
    @PaidFee
    void testFindForMonth_Paid_WrongMonth() {
        final FeeSummary summary;

        // WHEN
        summary = repository.findForMonth(FeeConstants.DATE.plusMonths(1));

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
    @DisplayName("With a paid fee, for an active member, the correct summary is returned")
    @PositiveFeeType
    @ValidProfile
    @AlternativeProfile
    @NotPaidFee
    @AlternativePaidFee
    void testFindForMonth_PaidAndNotPaid() {
        final FeeSummary summary;

        // WHEN
        summary = repository.findForMonth(FeeConstants.DATE);

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
    @DisplayName("With an unpaid fee, for an active member, the correct summary is returned")
    @PositiveFeeType
    @ValidProfile
    @NotPaidFee
    void testFindForMonth_Unpaid() {
        final FeeSummary summary;

        // WHEN
        summary = repository.findForMonth(FeeConstants.DATE);

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
    @DisplayName("With an unpaid fee for a month without data, for an active member, nothing is returned")
    @PositiveFeeType
    @ValidProfile
    @NotPaidFee
    void testFindForMonth_Unpaid_WrongMonth() {
        final FeeSummary summary;

        // WHEN
        summary = repository.findForMonth(FeeConstants.DATE.plusMonths(1));

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
