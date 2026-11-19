
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.BadSqlGrammarException;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.domain.filter.FeeFilter;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.FeeFilters;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.profile.test.configuration.data.annotation.MultipleProfiles;
import com.bernardomg.association.profile.test.configuration.data.annotation.MultipleProfilesAccents;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeRepository - findAll")
class ITFeeRepositoryFindAllSort {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("When sorting in ascending order by name with accents, then it returns the ordered data")
    @PositiveFeeType
    @MultipleProfilesAccents
    @MultipleFees
    void testFindAll_Accents_Name_Asc() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("member.name.firstName", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.empty();

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.list(Fee.class))
            .extracting(fee -> fee.member()
                .name()
                .fullName())
            .as("fee full names")
            .containsExactly("Name á Last name 1", "Name é Last name 2", "Name í Last name 3", "Name ó Last name 4",
                "Name ú Last name 5");
    }

    @Test
    @DisplayName("When sorting in ascending order by month, then it returns the ordered data")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_Month_Asc() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.empty();

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.list(Fee.class))
            .extracting(Fee::month)
            .as("fee months")
            .containsExactly(FeeConstants.FEBRUARY_DATE, FeeConstants.MARCH_DATE, FeeConstants.APRIL_DATE,
                FeeConstants.MAY_DATE, FeeConstants.JUNE_DATE);
    }

    @Test
    @DisplayName("When sorting in descending order by month, then it returns the ordered data")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_Month_Desc() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("month", Sorting.Direction.DESC)));

        feeFilter = FeeFilters.empty();

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.list(Fee.class))
            .extracting(Fee::month)
            .as("fee months")
            .containsExactly(FeeConstants.JUNE_DATE, FeeConstants.MAY_DATE, FeeConstants.APRIL_DATE,
                FeeConstants.MARCH_DATE, FeeConstants.FEBRUARY_DATE);
    }

    @Test
    @DisplayName("When sorting in ascending order by name, then it returns the ordered data")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_Name_Asc() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("member.name.firstName", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.empty();

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.list(Fee.class))
            .extracting(fee -> fee.member()
                .name()
                .fullName())
            .as("fee full names")
            .containsExactly("Name 1 Last name 1", "Name 2 Last name 2", "Name 3 Last name 3", "Name 4 Last name 4",
                "Name 5 Last name 5");
    }

    @Test
    @DisplayName("When sorting in descending order by name, then it returns the ordered data")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_Name_Desc() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("member.name.firstName", Sorting.Direction.DESC)));

        feeFilter = FeeFilters.empty();

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.list(Fee.class))
            .extracting(fee -> fee.member()
                .name()
                .fullName())
            .as("fee full names")
            .containsExactly("Name 5 Last name 5", "Name 4 Last name 4", "Name 3 Last name 3", "Name 2 Last name 2",
                "Name 1 Last name 1");
    }

    @Test
    @DisplayName("When an invalid field ordering is used, then an exception is thrown")
    @PositiveFeeType
    @Disabled
    @MultipleProfiles
    @MultipleFees
    void testFindAll_NotExisting() {
        final FeeFilter        feeFilter;
        final ThrowingCallable action;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("abc", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.empty();

        // WHEN
        action = () -> repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("With an invalid field ordering throws an exception")
            .isInstanceOf(BadSqlGrammarException.class);
    }

    @Test
    @DisplayName("When sorting in ascending order by paid flag, then it returns the ordered data")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_Paid_Asc() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("paid", Sorting.Direction.ASC),
            new Sorting.Property("month", Sorting.Direction.ASC)));

        feeFilter = FeeFilters.empty();

        // WHEN
        fees = repository.findAll(feeFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Fees.notPaidForMonth(5, Month.JUNE), Fees.paidForMonth(1, Month.FEBRUARY),
                Fees.paidForMonth(2, Month.MARCH), Fees.paidForMonth(3, Month.APRIL), Fees.paidForMonth(4, Month.MAY));
    }

    @Test
    @DisplayName("When sorting in descending order by paid flag, then it returns the ordered data")
    @PositiveFeeType
    @MultipleProfiles
    @MultipleFees
    void testFindAll_Paid_Desc() {
        final Page<Fee>  fees;
        final FeeFilter  feeFilter;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("paid", Sorting.Direction.DESC),
            new Sorting.Property("month", Sorting.Direction.ASC)));

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

}
