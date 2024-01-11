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

package com.bernardomg.association.membership.test.fee.service.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.membership.fee.service.FeeService;
import com.bernardomg.association.membership.test.fee.config.AlternativePaidFee;
import com.bernardomg.association.membership.test.fee.config.NotPaidFee;
import com.bernardomg.association.membership.test.fee.config.PaidFee;
import com.bernardomg.association.membership.test.fee.config.factory.FeeConstants;
import com.bernardomg.association.membership.test.fee.config.factory.Fees;
import com.bernardomg.association.membership.test.member.config.AlternativeMember;
import com.bernardomg.association.membership.test.member.config.NoSurnameMember;
import com.bernardomg.association.membership.test.member.config.ValidMember;
import com.bernardomg.association.membership.test.member.config.factory.MemberConstants;
import com.bernardomg.association.model.fee.Fee;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee service - get one")
class ITFeeServiceGetOne {

    @Autowired
    private FeeService service;

    public ITFeeServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("With no surname, only the name is returned")
    @NoSurnameMember
    @PaidFee
    void testGetOne_NoSurname() {
        final Optional<Fee> fee;

        // WHEN
        fee = service.getOne(MemberConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .contains(Fees.noSurname());
    }

    @Test
    @DisplayName("With a valid id, and a not paid fee, the related entity is returned")
    @ValidMember
    @NotPaidFee
    void testGetOne_NotPaid() {
        final Optional<Fee> fee;

        // WHEN
        fee = service.getOne(MemberConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .contains(Fees.notPaid());
    }

    @Test
    @DisplayName("With a valid id, and a paid fee, the related entity is returned")
    @ValidMember
    @PaidFee
    void testGetOne_Paid() {
        final Optional<Fee> fee;

        // WHEN
        fee = service.getOne(MemberConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .contains(Fees.paid());
    }

    @Test
    @DisplayName("With a valid id, and two members with paid fees, the first entity is returned")
    @ValidMember
    @AlternativeMember
    @PaidFee
    @AlternativePaidFee
    void testGetOne_Paid_TwoMembers() {
        final Optional<Fee> fee;

        // WHEN
        fee = service.getOne(MemberConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .contains(Fees.paid());
    }

    @Test
    @DisplayName("With a valid id, and two members with paid fees, the alternative entity is returned")
    @ValidMember
    @AlternativeMember
    @PaidFee
    @AlternativePaidFee
    void testGetOne_Paid_TwoMembers_Alternative() {
        final Optional<Fee> fee;

        // WHEN
        fee = service.getOne(MemberConstants.ALTERNATIVE_NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .contains(Fees.alternative());
    }

}
