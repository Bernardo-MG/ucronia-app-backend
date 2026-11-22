
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;

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
import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeFilters;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.profile.test.configuration.data.annotation.MultipleProfiles;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;
import com.bernardomg.test.pagination.AbstractPaginationIT;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeRepository - findAll")
@PositiveFeeType
@MultipleProfiles
@MultipleFees
class ITFeeRepositoryFindAllPagination extends AbstractPaginationIT<Fee> {

    @Autowired
    private FeeRepository repository;

    public ITFeeRepositoryFindAllPagination() {
        super(5);
    }

    @Override
    protected Page<Fee> read(final Pagination pagination, final Sorting sorting) {
        return repository.findAll(FeeFilters.empty(), pagination, sorting);
    }

    @Test
    @DisplayName("When requesting pagination for the first page, then it returns the first page")
    void testFindAll_Page1() {
        final FeeFilter  feeFilter;
        final Page<Fee>  fees;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(1, 1);
        sorting = Sorting.asc("month");

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

    @Test
    @DisplayName("When requesting pagination for the second page, then it returns the second page")
    void testFindAll_Page2() {
        final FeeFilter  feeFilter;
        final Page<Fee>  fees;
        final Pagination pagination;
        final Sorting    sorting;

        // GIVEN
        pagination = new Pagination(2, 1);
        sorting = Sorting.asc("month");

        feeFilter = FeeFilters.empty();

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

}
