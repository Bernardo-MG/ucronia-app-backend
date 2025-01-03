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
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.FeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.test.configuration.factory.FeesQuery;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActivePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MultipleInactiveMembershipPerson;
import com.bernardomg.association.person.test.configuration.data.annotation.NoLastNameActiveMembershipPerson;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all")
class ITFeeRepositoryFindAll {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With a full year it returns all the fees")
    @MembershipActivePerson
    @FeeFullYear
    void testFindAll_FullYear() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

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
    @DisplayName("With multiple fees it returns all the fees")
    @MultipleInactiveMembershipPerson
    @MultipleFees
    void testFindAll_Multiple() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(1, Month.FEBRUARY), Fees.paidAt(2, Month.MARCH), Fees.paidAt(3, Month.APRIL),
                Fees.paidAt(4, Month.MAY), Fees.notPaidAt(5, Month.JUNE));
    }

    @Test
    @DisplayName("With no data it returns nothing")
    @MembershipActivePerson
    void testFindAll_NoFee() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With no last name it returns only the name")
    @NoLastNameActiveMembershipPerson
    @PaidFee
    void testFindAll_NoLastName() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.noLastName());
    }

    @Test
    @DisplayName("With a not paid fee it returns all the fees")
    @MembershipActivePerson
    @NotPaidFee
    void testFindAll_NotPaid() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaid());
    }

    @Test
    @DisplayName("With a paid fee it returns all the fees")
    @MembershipActivePerson
    @PaidFee
    void testFindAll_Paid() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

}
