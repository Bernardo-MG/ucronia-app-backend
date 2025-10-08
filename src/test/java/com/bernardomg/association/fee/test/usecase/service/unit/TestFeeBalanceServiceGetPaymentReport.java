/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.fee.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.domain.model.FeeBalance;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeBalanceService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fee balance service - get fee balance")
class TestFeeBalanceServiceGetPaymentReport {

    @Mock
    private FeeRepository            feeRepository;

    @InjectMocks
    private DefaultFeeBalanceService service;

    public TestFeeBalanceServiceGetPaymentReport() {
        super();
    }

    @Test
    @DisplayName("When there is no data there are no fees")
    void testGetFeeBalance_NoData() {
        final FeeBalance report;

        // GIVEN
        given(feeRepository.findAllInMonth(ArgumentMatchers.any())).willReturn(List.of());

        // WHEN
        report = service.getFeeBalance();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(report.paid())
                .as("paid fees")
                .isZero();
            softly.assertThat(report.unpaid())
                .as("unpaid fees")
                .isZero();
        });
    }

    @Test
    @DisplayName("When there is a single paid fee it is returned")
    void testGetFeeBalance_Paid() {
        final FeeBalance report;

        // GIVEN
        given(feeRepository.findAllInMonth(ArgumentMatchers.any())).willReturn(List.of(Fees.paid()));

        // WHEN
        report = service.getFeeBalance();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(report.paid())
                .as("paid fees")
                .isEqualTo(1);
            softly.assertThat(report.unpaid())
                .as("unpaid fees")
                .isZero();
        });
    }

    @Test
    @DisplayName("When there are paid and unpaid fee they are returned")
    void testGetFeeBalance_PaidAndUnpaid() {
        final FeeBalance report;

        // GIVEN
        given(feeRepository.findAllInMonth(ArgumentMatchers.any())).willReturn(List.of(Fees.paid(), Fees.notPaid()));

        // WHEN
        report = service.getFeeBalance();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(report.paid())
                .as("paid fees")
                .isEqualTo(1);
            softly.assertThat(report.unpaid())
                .as("unpaid fees")
                .isEqualTo(1);
        });
    }

    @Test
    @DisplayName("When there is a single unpaid fee it is returned")
    void testGetFeeBalance_Unpaid() {
        final FeeBalance report;

        // GIVEN
        given(feeRepository.findAllInMonth(ArgumentMatchers.any())).willReturn(List.of(Fees.notPaid()));

        // WHEN
        report = service.getFeeBalance();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(report.paid())
                .as("paid fees")
                .isZero();
            softly.assertThat(report.unpaid())
                .as("unpaid fees")
                .isEqualTo(1);
        });
    }

}
