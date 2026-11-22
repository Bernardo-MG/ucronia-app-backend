
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypes;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeTypeRepository - findAll")
class ITFeeTypeRepositoryFindAll {

    @Autowired
    private FeeTypeRepository repository;

    @Test
    @DisplayName("When there is an existing fee type, then it is returned")
    @PositiveFeeType
    void testFindAll() {
        final Page<FeeType> feeTypes;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        feeTypes = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(feeTypes)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("feeTypes")
            .containsExactly(FeeTypes.positive());
    }

    @Test
    @DisplayName("When there is no data, then it returns nothing")
    void testFindAll_NoData() {
        final Page<FeeType> feeTypes;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        feeTypes = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(feeTypes)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("feeTypes")
            .isEmpty();
    }

}
