
package com.bernardomg.association.test.memberperiod.integration.repository;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.memberperiod.model.PersistentMemberPeriod;
import com.bernardomg.association.memberperiod.repository.MemberPeriodRepository;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Paid month repository - find overlapped")
@Sql({ "/db/queries/member/single.sql", "/db/queries/member_period/single.sql" })
public class ITMemberPeriodRepositoryFindContaining {

    @Autowired
    private MemberPeriodRepository repository;

    @Test
    @DisplayName("Returns no period when querying after the period")
    public void testFindContaining_AfterPeriod_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findContaining(1L, 5, 5);

        Assertions.assertEquals(0, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns no period when querying before the period")
    public void testFindContaining_BeforePeriod_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findContaining(1L, 1, 3);

        Assertions.assertEquals(0, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns the period when querying inside the period")
    public void testFindOverlapped_Inside_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findContaining(1L, 4, 4);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns the period when querying the end month")
    public void testFindOverlapped_MatchEndMonth_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findContaining(1L, 4, 5);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns the period when querying the start month")
    public void testFindOverlapped_MatchStartMonth_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findContaining(1L, 2, 3);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

}
