
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
import com.bernardomg.association.fee.domain.filter.FeeFilter;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.FeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeFilters;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.profile.test.configuration.data.annotation.MultipleProfiles;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeRepository - findAll")
class ITFeeRepositoryFindAll {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("When there is a full year, then it returns all the fees")
    @PositiveFeeType
    @ValidProfile
    @FeeFullYear
    void testFindAll_FullYear() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.empty();

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Fees.paidForMonth(Month.JANUARY.getValue()), Fees.paidForMonth(Month.FEBRUARY.getValue()),
                Fees.paidForMonth(Month.MARCH.getValue()), Fees.paidForMonth(Month.APRIL.getValue()),
                Fees.paidForMonth(Month.MAY.getValue()), Fees.paidForMonth(Month.JUNE.getValue()),
                Fees.paidForMonth(Month.JULY.getValue()), Fees.paidForMonth(Month.AUGUST.getValue()),
                Fees.paidForMonth(Month.SEPTEMBER.getValue()), Fees.paidForMonth(Month.OCTOBER.getValue()),
                Fees.paidForMonth(Month.NOVEMBER.getValue()), Fees.paidForMonth(Month.DECEMBER.getValue()));
    }

    @Test
    @DisplayName("When there are multiple fees, then it returns all the fees")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_Multiple() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.empty();

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Fees.paidForMonth(1, Month.FEBRUARY), Fees.paidForMonth(2, Month.MARCH),
                Fees.paidForMonth(3, Month.APRIL), Fees.paidForMonth(4, Month.MAY),
                Fees.notPaidForMonth(5, Month.JUNE));
    }

    @Test
    @DisplayName("When there is no data, then it returns nothing")
    @PositiveFeeType
    @ValidProfile
    void testFindAll_NoData() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        feeFilter = FeeFilters.empty();

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

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
    void testFindAll_NoFee() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        feeFilter = FeeFilters.empty();

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a not paid fee, then it returns all the fees")
    @PositiveFeeType
    @ValidProfile
    @NotPaidFee
    void testFindAll_NotPaid() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        feeFilter = FeeFilters.empty();

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Fees.notPaid());
    }

    @Test
    @DisplayName("When there is a paid fee, then it returns all the fees")
    @PositiveFeeType
    @ValidProfile
    @PaidFee
    void testFindAll_Paid() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        feeFilter = FeeFilters.empty();

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Fees.paid());
    }

}
