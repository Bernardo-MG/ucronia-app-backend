
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
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
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
class ITFeeRepositoryGetAllFilter {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("When there is a filter applied to the start date, then the returned data is filtered")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_From() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.from(FeeConstants.JUNE_DATE);

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Fees.notPaidForMonth(5, Month.JUNE));
    }

    @Test
    @DisplayName("When there is a filter applied to the start date which covers no fee, then no data is returned")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_From_NotInRange() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.from(FeeConstants.JULY_DATE);

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
    @DisplayName("When there is a filter applied to the date, then the returned data is filtered")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_InDate() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.inMonth(FeeConstants.MARCH_DATE);

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Fees.paidForMonth(2, Month.MARCH));
    }

    @Test
    @DisplayName("When there is a filter applied to the date using the lowest date of the year, then the returned data is filtered")
    @PositiveFeeType
    @ValidProfile
    @FeeFullYear
    void testFindAll_InDate_FirstDay_Data() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.to(FeeConstants.JANUARY_DATE);

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Fees.paidForMonth(Month.JANUARY.getValue()));
    }

    @Test
    @DisplayName("When there is a filter applied to the date using the highest date of the year, then the returned data is filtered")
    @PositiveFeeType
    @ValidProfile
    @FeeFullYear
    void testFindAll_InDate_LastDay_Data() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.inMonth(FeeConstants.DECEMBER_DATE);

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Fees.paidForMonth(Month.DECEMBER.getValue()));
    }

    @Test
    @DisplayName("When there is a filter applied to the date using a not existing date, then no data is returned")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_InDate_NotExisting() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.inMonth(FeeConstants.NOVEMBER_DATE);

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
    @DisplayName("When there is a filter applied to the end date, then the returned data is filtered")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_InRange() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.inRange(FeeConstants.DATE, FeeConstants.MAY_DATE);

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Fees.paidForMonth(1, Month.FEBRUARY), Fees.paidForMonth(2, Month.MARCH),
                Fees.paidForMonth(3, Month.APRIL), Fees.paidForMonth(4, Month.MAY));
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

        feeFilter = FeeFilters.to(FeeConstants.JANUARY_DATE);

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
    @DisplayName("When there is a filter applied to the end date, then the returned data is filtered")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_To() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.to(FeeConstants.DATE);

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Fees.paidForMonth(1, Month.FEBRUARY));
    }

    @Test
    @DisplayName("When there is a filter applied to the end date which covers no fee, then no data is returned")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_To_NotInRange() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.to(FeeConstants.JANUARY_DATE);

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("When there is a filter applied to the end date which covers no fee, then no data is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
