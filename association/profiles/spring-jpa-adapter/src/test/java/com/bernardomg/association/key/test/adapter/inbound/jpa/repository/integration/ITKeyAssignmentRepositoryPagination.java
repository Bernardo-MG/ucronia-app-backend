
package com.bernardomg.association.key.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.key.test.configuration.data.annotation.AlternativeAvailableKey;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithKey;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;
import com.bernardomg.test.pagination.AbstractPaginationIT;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyAssignmentRepository - findAll")
@PositiveFeeType
@ActiveMemberWithKey
@AlternativeAvailableKey
class ITKeyAssignmentRepositoryPagination extends AbstractPaginationIT<KeyAssignment> {

    @Autowired
    private KeyAssignmentRepository repository;

    public ITKeyAssignmentRepositoryPagination() {
        super(2, "number");
    }

    @Override
    protected final Page<KeyAssignment> read(final Pagination pagination, final Sorting sorting) {
        return repository.findAll(pagination, sorting);
    }

    @Test
    @DisplayName("When requesting pagination for the first page, then it returns the assigned key")
    void testFindAll_Page1() {
        final Page<KeyAssignment> assignments;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        pagination = new Pagination(1, 1);
        sorting = Sorting.asc("number");

        // WHEN
        assignments = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(assignments)
            .as("When requesting pagination for the first page, then it returns the assigned key")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(KeyAssignments.assigned());
    }

    @Test
    @DisplayName("When requesting pagination for the second page, then it returns the unassigned key")
    void testFindAll_Page2() {
        final Page<KeyAssignment> assignments;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        pagination = new Pagination(2, 1);
        sorting = Sorting.asc("number");

        // WHEN
        assignments = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(assignments)
            .as("When requesting pagination for the second page, then it returns the unassigned key")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(KeyAssignments.unassignedAlternative());
    }

    @Test
    @DisplayName("When results are sorted and paginated, then sorting is applied before pagination")
    void testFindAll_SortDescending() {
        final Page<KeyAssignment> assignments;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        pagination = new Pagination(1, 1);
        sorting = Sorting.desc("number");

        // WHEN
        assignments = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(assignments)
            .as("Sorting is applied before pagination")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(KeyAssignments.unassignedAlternative());
    }

}
