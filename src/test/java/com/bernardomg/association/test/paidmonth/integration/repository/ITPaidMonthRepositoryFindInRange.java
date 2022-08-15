
package com.bernardomg.association.test.paidmonth.integration.repository;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.paidmonth.model.PersistentPaidMonth;
import com.bernardomg.association.paidmonth.repository.PaidMonthRepository;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Paid month repository - find in range")
@Sql({ "/db/queries/paid_month/year_gaps.sql" })
public class ITPaidMonthRepositoryFindInRange {

    @Autowired
    private PaidMonthRepository repository;

    public ITPaidMonthRepositoryFindInRange() {
        super();
    }

    @Test
    @DisplayName("Returns a full year")
    public void testFindInRange_FullYear() {
        final Iterable<PersistentPaidMonth> result;

        result = repository.findInRange(1L, 1, 2020, 12, 2020);

        Assertions.assertEquals(7, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns a single existing month")
    public void testFindInRange_MatchSingleExistingMonth() {
        final Iterable<PersistentPaidMonth> result;

        result = repository.findInRange(1L, 1, 2020, 1, 2020);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Does not return a not existing month")
    public void testFindInRange_MatchSingleNotExistingMonth() {
        final Iterable<PersistentPaidMonth> result;

        result = repository.findInRange(1L, 1, 2019, 1, 2019);

        Assertions.assertEquals(0, IterableUtils.size(result));
    }

}
