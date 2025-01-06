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
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.FeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActivePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipInactivePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.NoLastNameActiveMembershipPerson;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all for member")
class ITFeeRepositoryfindAllForMemberForMember {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With a full year it returns all the fees")
    @MembershipActivePerson
    @FeeFullYear
    void testFindAllForMember_Active_FullYear() {
        final Iterable<Fee> fees;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidForMonth(Month.JANUARY.getValue()), Fees.paidForMonth(Month.FEBRUARY.getValue()),
                Fees.paidForMonth(Month.MARCH.getValue()), Fees.paidForMonth(Month.APRIL.getValue()),
                Fees.paidForMonth(Month.MAY.getValue()), Fees.paidForMonth(Month.JUNE.getValue()),
                Fees.paidForMonth(Month.JULY.getValue()), Fees.paidForMonth(Month.AUGUST.getValue()),
                Fees.paidForMonth(Month.SEPTEMBER.getValue()), Fees.paidForMonth(Month.OCTOBER.getValue()),
                Fees.paidForMonth(Month.NOVEMBER.getValue()), Fees.paidForMonth(Month.DECEMBER.getValue()));
    }

    @Test
    @DisplayName("With no data it returns nothing")
    @MembershipActivePerson
    void testFindAllForMember_Active_NoFee() {
        final Iterable<Fee> fees;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With no last name it returns only the name")
    @NoLastNameActiveMembershipPerson
    @PaidFee
    void testFindAllForMember_Active_NoLastName() {
        final Iterable<Fee> fees;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.noLastName());
    }

    @Test
    @DisplayName("With a not paid fee, for an active member, it returns all the fees")
    @MembershipActivePerson
    @NotPaidFee
    void testFindAllForMember_Active_NotPaid() {
        final Iterable<Fee> fees;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaid());
    }

    @Test
    @DisplayName("With a paid fee, for an active member, it returns all the fees")
    @MembershipActivePerson
    @PaidFee
    void testFindAllForMember_Active_Paid() {
        final Iterable<Fee> fees;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

    @Test
    @DisplayName("With a wrong member it returns nothing")
    @MembershipActivePerson
    @PaidFee
    void testFindAllForMember_Active_WrongMember() {
        final Iterable<Fee> fees;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllForMember(-1L, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee, for an inactive member, it returns all the fees")
    @MembershipInactivePerson
    @NotPaidFee
    void testFindAllForMember_Inactive_NotPaid() {
        final Iterable<Fee> fees;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaid());
    }

    @Test
    @DisplayName("With a paid fee, for an inactive member, it returns all the fees")
    @MembershipInactivePerson
    @PaidFee
    void testFindAllForMember_Inactive_Paid() {
        final Iterable<Fee> fees;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllForMember(PersonConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

}
