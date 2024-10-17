
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.test.configuration.data.annotation.FeeFullYear;
import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.member.test.configuration.factory.MemberBalanceConstants;
import com.bernardomg.association.member.test.configuration.factory.MonthlyMemberBalances;
import com.bernardomg.association.person.domain.repository.MemberBalanceRepository;
import com.bernardomg.association.person.test.configuration.data.annotation.ValidPerson;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberBalanceRepository - find in range")
class ITMemberBalanceRepositoryFindInRange {

    @Autowired
    private MemberBalanceRepository memberBalanceRepository;

    @Test
    @DisplayName("Can filter having only the end date")
    @ValidPerson
    @FeeFullYear
    void testFindInRange_End() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(null, MemberBalanceConstants.START_MONTH.plusMonths(2), sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactlyInAnyOrder(MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(1)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(2)));
    }

    @Test
    @DisplayName("Returns all when not applying range")
    @ValidPerson
    @FeeFullYear
    void testFindInRange_NoRange() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactlyInAnyOrder(MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(1)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(2)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(3)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(4)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(5)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(6)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(7)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(8)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(9)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(10)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(11)));
    }

    @Test
    @DisplayName("When reading all with no data, nothing is returned")
    void testFindInRange_NoRange_NoData() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("Can filter having in a range")
    @ValidPerson
    @FeeFullYear
    void testFindInRange_Range() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(MemberBalanceConstants.START_MONTH.plusMonths(1),
            MemberBalanceConstants.START_MONTH.plusMonths(3), sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactlyInAnyOrder(MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(1)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(2)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(3)));
    }

    @Test
    @DisplayName("Can filter having only the start date")
    @ValidPerson
    @FeeFullYear
    void testFindInRange_Start() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(MemberBalanceConstants.START_MONTH.plusMonths(1), null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactlyInAnyOrder(MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(1)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(2)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(3)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(4)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(5)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(6)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(7)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(8)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(9)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(10)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(11)));
    }

}
