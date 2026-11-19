
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.SoftAssertions;
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
@DisplayName("MyFeeRepository - findAllForMember")
class ITMyFeeRepositoryFindAllForMember {

    private static MyFee myFee(final Fee fee) {
        return new MyFee(fee.month(), fee.paid(), fee.member(), fee.transaction());
    }

    @Autowired
    private MyFeeRepository repository;

    @Test
    @DisplayName("When there is a full year, then it returns all the fees")
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
    @DisplayName("When there is no data, then it returns nothing")
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
    @DisplayName("When there is no fee, then it returns nothing")
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
    @DisplayName("When there is a not paid fee, for an active member, then it returns all the fees")
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
    @DisplayName("When one page of matching fees is read, then the total number of matching fees is returned")
    @PositiveFeeType
    @ValidProfile
    @FeeFullYear
    void testFindAllForMember_Paginated() {
        final Sorting     sorting;
        final Page<MyFee> fees;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.DESC)));

        // WHEN
        fees = repository.findAllForMember(ProfileConstants.NUMBER, new Pagination(1, 2), sorting);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(fees.content())
                .as("fees on the requested page")
                .containsExactly(myFee(Fees.paidForMonth(Month.DECEMBER.getValue())),
                    myFee(Fees.paidForMonth(Month.NOVEMBER.getValue())));
            softly.assertThat(fees.totalElements())
                .as("total matching fee count")
                .isEqualTo(12);
        });
    }

    @Test
    @DisplayName("When there is a paid fee, for an active member, then it returns all the fees")
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
    @DisplayName("When there is a wrong member, then it returns nothing")
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
