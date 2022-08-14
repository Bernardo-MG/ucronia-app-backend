
package com.bernardomg.association.test.member.integration.repository;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.PersistentMemberPeriod;
import com.bernardomg.association.member.repository.MemberPeriodRepository;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Paid month repository - find overlapped")
@Sql({ "/db/queries/member_period/single.sql" })
public class ITMemberPeriodRepositoryFindOverlapped {

    @Autowired
    private MemberPeriodRepository repository;

    @Test
    @DisplayName("Returns no period when querying after the period")
    public void testFindInRange_AfterPeriod_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findOverlapped(1L, 5, 5, 5, 6);

        Assertions.assertEquals(0, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns no period when querying before the period")
    public void testFindInRange_BeforePeriod_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findOverlapped(1L, 1, 2, 5, 2);

        Assertions.assertEquals(0, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns the period when the period matches the end month")
    public void testFindInRange_MatchEndMonth_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findOverlapped(1L, 4, 5, 4, 5);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns the period when the period matches the start month")
    public void testFindInRange_MatchStartMonth_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findOverlapped(1L, 2, 3, 2, 3);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns the period when the period overlaps the end month")
    public void testFindInRange_OverlapsEndMonth_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findOverlapped(1L, 3, 5, 5, 5);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns the period when overlapped")
    public void testFindInRange_OverlapsFullPeriod_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findOverlapped(1L, 1, 3, 5, 5);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns the period when the period overlaps the start month")
    public void testFindInRange_OverlapsStartMonth_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findOverlapped(1L, 1, 3, 3, 3);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns the period when the period touches the end month")
    public void testFindInRange_TouchEndMonth_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findOverlapped(1L, 4, 5, 5, 5);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns the period when the period touches the start month")
    public void testFindInRange_TouchStartMonth_Count() {
        final Iterable<PersistentMemberPeriod> result;

        result = repository.findOverlapped(1L, 1, 3, 2, 3);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

}
