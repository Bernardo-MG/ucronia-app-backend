
package com.bernardomg.association.key.test.adapter.inbound.jpa.repository.integration;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.key.domain.model.Key;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.key.test.configuration.data.annotation.AvailableKey;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.member.test.configuration.data.annotation.AlternativeMemberWithKey;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyAssignmentRepository - pagination")
class ITKeyAssignmentRepositoryPagination {

    @PersistenceContext
    private EntityManager           entityManager;

    @Autowired
    private KeyRepository           keyRepository;

    @Autowired
    private KeyAssignmentRepository repository;

    @Test
    @DisplayName("Assigned and unassigned keys occupy separate pages with the same total")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testFindAll_MixedPages() {
        final Page<KeyAssignment> first;
        final Key                 key;
        final Page<KeyAssignment> second;
        final Sorting             sorting;

        // GIVEN
        key = keyRepository.save(new Key(-1L, false, "Unassigned unavailable key"));
        sorting = new Sorting(List.of(new Sorting.Property("number", Sorting.Direction.ASC)));

        // WHEN
        first = repository.findAll(new Pagination(1, 1), sorting);
        second = repository.findAll(new Pagination(2, 1), sorting);

        // THEN
        Assertions.assertThat(first.content())
            .containsExactly(KeyAssignments.reassigned());
        Assertions.assertThat(second.content())
            .containsExactly(new KeyAssignment(key.number(), key.available(), key.description(), Optional.empty()));
        Assertions.assertThat(first.totalElements())
            .isEqualTo(2);
        Assertions.assertThat(second.totalElements())
            .isEqualTo(2);
    }

    @Test
    @DisplayName("Pagination uses one data query and one count query")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testFindAll_PagedQueries() {
        final Page<KeyAssignment> assignments;
        final Statistics          statistics;
        final boolean             statisticsEnabled;
        final Sorting             sorting;

        // GIVEN
        keyRepository.save(new Key(-1L, false, "Unassigned unavailable key"));
        sorting = new Sorting(List.of(new Sorting.Property("number", Sorting.Direction.ASC)));
        entityManager.flush();
        entityManager.clear();
        statistics = entityManager.getEntityManagerFactory()
            .unwrap(SessionFactory.class)
            .getStatistics();
        statisticsEnabled = statistics.isStatisticsEnabled();
        statistics.setStatisticsEnabled(true);
        statistics.clear();

        try {
            // WHEN
            assignments = repository.findAll(new Pagination(1, 1), sorting);

            // THEN
            Assertions.assertThat(assignments.content())
                .containsExactly(KeyAssignments.reassigned());
            Assertions.assertThat(assignments.totalElements())
                .isEqualTo(2);
            Assertions.assertThat(statistics.getPrepareStatementCount())
                .isEqualTo(2);
        } finally {
            statistics.setStatisticsEnabled(statisticsEnabled);
        }
    }

    @Test
    @DisplayName("Assignments to profiles without membership are included")
    @AvailableKey
    @ValidProfile
    void testFindAll_ProfileWithoutMembership() {
        final Page<KeyAssignment> assignments;

        // GIVEN
        repository.save(KeyConstants.NUMBER, ProfileConstants.NUMBER);

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .containsExactly(KeyAssignments.assigned());
    }

    @Test
    @DisplayName("Assigned and unassigned data is loaded with one query")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testFindAll_SingleQuery() {
        final Page<KeyAssignment> assignments;
        final Key                 key;
        final Statistics          statistics;
        final boolean             statisticsEnabled;

        // GIVEN
        key = keyRepository.save(new Key(-1L, false, "Unassigned unavailable key"));
        entityManager.flush();
        entityManager.clear();
        statistics = entityManager.getEntityManagerFactory()
            .unwrap(SessionFactory.class)
            .getStatistics();
        statisticsEnabled = statistics.isStatisticsEnabled();
        statistics.setStatisticsEnabled(true);
        statistics.clear();

        try {
            // WHEN
            assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

            // THEN
            Assertions.assertThat(assignments.content())
                .containsExactlyInAnyOrder(KeyAssignments.reassigned(),
                    new KeyAssignment(key.number(), key.available(), key.description(), Optional.empty()));
            Assertions.assertThat(statistics.getPrepareStatementCount())
                .isEqualTo(1);
        } finally {
            statistics.setStatisticsEnabled(statisticsEnabled);
        }
    }

    @Test
    @DisplayName("Description sorting is applied before pagination")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testFindAll_SortDescription() {
        final Page<KeyAssignment> assignments;
        final Key                 key;
        final Sorting             sorting;

        // GIVEN
        key = keyRepository.save(new Key(-1L, true, "Z last key"));
        sorting = new Sorting(List.of(new Sorting.Property("description", Sorting.Direction.DESC)));

        // WHEN
        assignments = repository.findAll(new Pagination(1, 1), sorting);

        // THEN
        Assertions.assertThat(assignments.content())
            .containsExactly(new KeyAssignment(key.number(), key.available(), key.description(), Optional.empty()));
        Assertions.assertThat(assignments.totalElements())
            .isEqualTo(2);
    }

}
