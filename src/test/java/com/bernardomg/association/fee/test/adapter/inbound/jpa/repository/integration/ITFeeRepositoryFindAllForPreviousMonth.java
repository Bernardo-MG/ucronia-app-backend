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

package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.fee.test.config.initializer.FeeInitializer;
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all by date")
class ITFeeRepositoryFindAllForPreviousMonth {

    @Autowired
    private FeeInitializer feeInitializer;

    @Autowired
    private FeeRepository  repository;

    @Test
    @DisplayName("When there is no data nothing is returned")
    void testFindAllForPreviousMonth_NoData() {
        final Iterable<Fee> fees;

        // WHEN
        fees = repository.findAllForPreviousMonth();

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a not paid fee in the previous month it is returned")
    @ValidMember
    void testFindAllForPreviousMonth_NotPaid() {
        final Iterable<Fee> fees;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(false);

        // WHEN
        fees = repository.findAllForPreviousMonth();

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidPreviousMonth());
    }

    @Test
    @DisplayName("When there is a paid fee in the previous month it is returned")
    @ValidMember
    void testFindAllForPreviousMonth_Paid() {
        final Iterable<Fee> fees;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(true);

        // WHEN
        fees = repository.findAllForPreviousMonth();

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidPreviousMonthNew());
    }

}
