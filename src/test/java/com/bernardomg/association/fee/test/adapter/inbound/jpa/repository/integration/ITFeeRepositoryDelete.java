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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - delete")
class ITFeeRepositoryDelete {

    @Autowired
    private FeeSpringRepository feeSpringRepository;

    @Autowired
    private FeeRepository       repository;

    @Test
    @DisplayName("When there is no data, nothing is removed")
    void testDelete_NoData() {
        // WHEN
        repository.delete(PersonConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(feeSpringRepository.count())
            .as("fees")
            .isZero();
    }

    @Test
    @DisplayName("When a not paid entity is deleted, it is removed")
    @ActiveMember
    @NotPaidFee
    void testDelete_NotPaid() {
        // WHEN
        repository.delete(PersonConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(feeSpringRepository.count())
            .as("fees")
            .isZero();
    }

    @Test
    @DisplayName("When a paid entity is deleted, it is removed")
    @ActiveMember
    @PaidFee
    @Disabled("Handle relationships")
    void testDelete_Paid() {
        // WHEN
        repository.delete(PersonConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(feeSpringRepository.count())
            .as("fees")
            .isZero();
    }

}
