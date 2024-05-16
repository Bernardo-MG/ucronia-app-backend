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
import com.bernardomg.association.fee.test.config.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.config.factory.FeeConstants;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.member.test.config.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.config.data.annotation.InactiveMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all in month")
class ITFeeRepositoryFindAllInMonth {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With a paid fee, for an active member, it is returned")
    @ActiveMember
    @PaidFee
    void testFindAllInMonth_Active() {
        final Iterable<Fee> fees;

        // WHEN
        fees = repository.findAllInMonth(FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

    @Test
    @DisplayName("With a paid fee for a month without data, for an active member, nothing is returned")
    @ActiveMember
    @PaidFee
    void testFindAllInMonth_Active_WrongMonth() {
        final Iterable<Fee> fees;

        // WHEN
        fees = repository.findAllInMonth(FeeConstants.DATE.plusMonths(1));

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee, for an inactive member, it is returned")
    @InactiveMember
    @PaidFee
    void testFindAllInMonth_Inactive() {
        final Iterable<Fee> fees;

        // WHEN
        fees = repository.findAllInMonth(FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

    @Test
    @DisplayName("With no data, it returns nothing")
    void testFindAllInMonth_NoData() {
        final Iterable<Fee> fees;

        // WHEN
        fees = repository.findAllInMonth(FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

}
