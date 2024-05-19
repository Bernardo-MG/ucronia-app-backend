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

import java.time.Month;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.data.annotation.FeeFullYear;
import com.bernardomg.association.fee.test.config.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.config.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.member.test.config.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.config.data.annotation.InactiveMember;
import com.bernardomg.association.member.test.config.data.annotation.NoSurnameMember;
import com.bernardomg.association.person.test.config.factory.PersonConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all for member")
class ITFeeRepositoryfindAllForMemberForMember {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With a full year it returns all the fees")
    @ActiveMember
    @FeeFullYear
    void testFindAllForMember_Active_FullYear() {
        final Iterable<Fee> fees;
        final Pageable      pageable;

        // GIVEN
        pageable = PageRequest.of(0, 20, Direction.ASC, "date");

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(Month.JANUARY.getValue()), Fees.paidAt(Month.FEBRUARY.getValue()),
                Fees.paidAt(Month.MARCH.getValue()), Fees.paidAt(Month.APRIL.getValue()),
                Fees.paidAt(Month.MAY.getValue()), Fees.paidAt(Month.JUNE.getValue()),
                Fees.paidAt(Month.JULY.getValue()), Fees.paidAt(Month.AUGUST.getValue()),
                Fees.paidAt(Month.SEPTEMBER.getValue()), Fees.paidAt(Month.OCTOBER.getValue()),
                Fees.paidAt(Month.NOVEMBER.getValue()), Fees.paidAt(Month.DECEMBER.getValue()));
    }

    @Test
    @DisplayName("With no data it returns nothing")
    @ActiveMember
    void testFindAllForMember_Active_NoFee() {
        final Iterable<Fee> fees;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With no surname it returns only the name")
    @NoSurnameMember
    @PaidFee
    void testFindAllForMember_Active_NoSurname() {
        final Iterable<Fee> fees;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.noSurname());
    }

    @Test
    @DisplayName("With a not paid fee, for an active member, it returns all the fees")
    @ActiveMember
    @NotPaidFee
    void testFindAllForMember_Active_NotPaid() {
        final Iterable<Fee> fees;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaid());
    }

    @Test
    @DisplayName("With a paid fee, for an active member, it returns all the fees")
    @ActiveMember
    @PaidFee
    void testFindAllForMember_Active_Paid() {
        final Iterable<Fee> fees;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

    @Test
    @DisplayName("With a wrong member it returns nothing")
    @ActiveMember
    @PaidFee
    void testFindAllForMember_Active_WrongMember() {
        final Iterable<Fee> fees;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        fees = repository.findAllForMember(-1L, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee, for an inactive member, it returns all the fees")
    @InactiveMember
    @NotPaidFee
    void testFindAllForMember_Inactive_NotPaid() {
        final Iterable<Fee> fees;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaid());
    }

    @Test
    @DisplayName("With a paid fee, for an inactive member, it returns all the fees")
    @InactiveMember
    @PaidFee
    void testFindAllForMember_Inactive_Paid() {
        final Iterable<Fee> fees;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

}
