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

import com.bernardomg.association.fee.domain.model.FeeBalance;
import com.bernardomg.association.fee.domain.repository.FeeBalanceRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.AlternativePaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.AlternativeActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeBalanceRepository - find all in month")
class ITFeeBalanceRepositoryFindForMonth {

    @Autowired
    private FeeBalanceRepository repository;

    @Test
    @DisplayName("With no data, it returns nothing")
    void testFindForMonth_NoData() {
        final FeeBalance balance;

        // WHEN
        balance = repository.findForMonth(FeeConstants.DATE);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(balance.paid())
                .as("paid fees")
                .isZero();
            softly.assertThat(balance.unpaid())
                .as("unpaid fees")
                .isZero();
        });
    }

    @Test
    @DisplayName("With a paid fee, for an active member, the correct balance is returned")
    @PositiveFeeType
    @ActiveMember
    @PaidFee
    void testFindForMonth_Paid_Active() {
        final FeeBalance balance;

        // WHEN
        balance = repository.findForMonth(FeeConstants.DATE);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(balance.paid())
                .as("paid fees")
                .isEqualTo(1);
            softly.assertThat(balance.unpaid())
                .as("unpaid fees")
                .isZero();
        });
    }

    @Test
    @DisplayName("With a paid fee for a month without data, for an active member, nothing is returned")
    @PositiveFeeType
    @ActiveMember
    @PaidFee
    void testFindForMonth_Paid_Active_WrongMonth() {
        final FeeBalance balance;

        // WHEN
        balance = repository.findForMonth(FeeConstants.DATE.plusMonths(1));

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(balance.paid())
                .as("paid fees")
                .isZero();
            softly.assertThat(balance.unpaid())
                .as("unpaid fees")
                .isZero();
        });
    }

    @Test
    @DisplayName("With a paid fee, for an inactive member, the correct balance is returned")
    @PositiveFeeType
    @InactiveMember
    @PaidFee
    void testFindForMonth_Paid_Inactive() {
        final FeeBalance balance;

        // WHEN
        balance = repository.findForMonth(FeeConstants.DATE);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(balance.paid())
                .as("paid fees")
                .isEqualTo(1);
            softly.assertThat(balance.unpaid())
                .as("unpaid fees")
                .isZero();
        });
    }

    @Test
    @DisplayName("With a paid fee, for an active member, the correct balance is returned")
    @PositiveFeeType
    @ActiveMember
    @AlternativeActiveMember
    @NotPaidFee
    @AlternativePaidFee
    void testFindForMonth_PaidAndNotPaid_Active() {
        final FeeBalance balance;

        // WHEN
        balance = repository.findForMonth(FeeConstants.DATE);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(balance.paid())
                .as("paid fees")
                .isEqualTo(1);
            softly.assertThat(balance.unpaid())
                .as("unpaid fees")
                .isEqualTo(1);
        });
    }

    @Test
    @DisplayName("With an unpaid fee, for an active member, the correct balance is returned")
    @PositiveFeeType
    @ActiveMember
    @NotPaidFee
    void testFindForMonth_Unpaid_Active() {
        final FeeBalance balance;

        // WHEN
        balance = repository.findForMonth(FeeConstants.DATE);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(balance.paid())
                .as("paid fees")
                .isZero();
            softly.assertThat(balance.unpaid())
                .as("unpaid fees")
                .isEqualTo(1);
        });
    }

    @Test
    @DisplayName("With an unpaid fee for a month without data, for an active member, nothing is returned")
    @PositiveFeeType
    @ActiveMember
    @NotPaidFee
    void testFindForMonth_Unpaid_Active_WrongMonth() {
        final FeeBalance balance;

        // WHEN
        balance = repository.findForMonth(FeeConstants.DATE.plusMonths(1));

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(balance.paid())
                .as("paid fees")
                .isZero();
            softly.assertThat(balance.unpaid())
                .as("unpaid fees")
                .isZero();
        });
    }

    @Test
    @DisplayName("With an unpaid fee, for an inactive member, the correct balance is returned")
    @PositiveFeeType
    @InactiveMember
    @NotPaidFee
    void testFindForMonth_Unpaid_Inactive() {
        final FeeBalance balance;

        // WHEN
        balance = repository.findForMonth(FeeConstants.DATE);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(balance.paid())
                .as("paid fees")
                .isZero();
            softly.assertThat(balance.unpaid())
                .as("unpaid fees")
                .isEqualTo(1);
        });
    }

}
