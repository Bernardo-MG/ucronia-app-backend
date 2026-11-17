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

import java.time.Month;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.MyFee;
import com.bernardomg.association.fee.domain.repository.MyFeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.FeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("MyFeeRepository - find all for member")
class ITMyFeeRepositoryFindAllForMember {

    private static MyFee myFee(final Fee fee) {
        return new MyFee(fee.month(), fee.paid(), fee.member(), fee.transaction());
    }

    @Autowired
    private MyFeeRepository repository;

    @Test
    @DisplayName("With a full year it returns all the fees")
    @PositiveFeeType
    @ValidProfile
    @FeeFullYear
    void testFindAllForMember_FullYear() {
        final Page<MyFee> fees;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllForMember(ProfileConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .containsExactly(myFee(Fees.paidForMonth(Month.JANUARY.getValue())),
                myFee(Fees.paidForMonth(Month.FEBRUARY.getValue())), myFee(Fees.paidForMonth(Month.MARCH.getValue())),
                myFee(Fees.paidForMonth(Month.APRIL.getValue())), myFee(Fees.paidForMonth(Month.MAY.getValue())),
                myFee(Fees.paidForMonth(Month.JUNE.getValue())), myFee(Fees.paidForMonth(Month.JULY.getValue())),
                myFee(Fees.paidForMonth(Month.AUGUST.getValue())), myFee(Fees.paidForMonth(Month.SEPTEMBER.getValue())),
                myFee(Fees.paidForMonth(Month.OCTOBER.getValue())), myFee(Fees.paidForMonth(Month.NOVEMBER.getValue())),
                myFee(Fees.paidForMonth(Month.DECEMBER.getValue())));
    }

    @Test
    @DisplayName("With no data, it returns nothing")
    void testFindAllForMember_NoData() {
        final Page<MyFee> fees;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForMember(ProfileConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With no fee it returns nothing")
    @PositiveFeeType
    @ValidProfile
    void testFindAllForMember_NoFee() {
        final Page<MyFee> fees;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForMember(ProfileConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee, for an active member, it returns all the fees")
    @PositiveFeeType
    @ValidProfile
    @NotPaidFee
    void testFindAllForMember_NotPaid() {
        final Page<MyFee> fees;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForMember(ProfileConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .containsExactly(myFee(Fees.notPaid()));
    }

    @Test
    @DisplayName("Counts all matching fees when reading a single page")
    @PositiveFeeType
    @ValidProfile
    @FeeFullYear
    void testFindAllForMember_Paginated() {
        final Sorting     sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.DESC)));
        final Page<MyFee> fees    = repository.findAllForMember(ProfileConstants.NUMBER, new Pagination(1, 2), sorting);

        Assertions.assertThat(fees.content())
            .containsExactly(myFee(Fees.paidForMonth(Month.DECEMBER.getValue())),
                myFee(Fees.paidForMonth(Month.NOVEMBER.getValue())));
        Assertions.assertThat(fees.totalElements())
            .isEqualTo(12);
    }

    @Test
    @DisplayName("With a paid fee, for an active member, it returns all the fees")
    @PositiveFeeType
    @ValidProfile
    @PaidFee
    void testFindAllForMember_Paid() {
        final Page<MyFee> fees;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForMember(ProfileConstants.NUMBER, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .containsExactly(myFee(Fees.paid()));
    }

    @Test
    @DisplayName("With a wrong member it returns nothing")
    @PositiveFeeType
    @ValidProfile
    @PaidFee
    void testFindAllForMember_WrongMember() {
        final Page<MyFee> fees;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForMember(-1L, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .isEmpty();
    }

}
