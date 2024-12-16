
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.test.configuration.data.annotation.FeeFullYear;
import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.member.domain.repository.MemberBalanceRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberBalanceConstants;
import com.bernardomg.association.member.test.configuration.factory.MonthlyMemberBalances;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActivePerson;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberBalanceRepository - find in range")
class ITMemberBalanceRepositoryFindInRange {

    @Autowired
    private MemberBalanceRepository memberBalanceRepository;

    @Test
    @DisplayName("Can filter having only the end date")
    @MembershipActivePerson
    @FeeFullYear
    void testFindInRange_End() {
        final Sorting                        sorting;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(null, MemberBalanceConstants.START_MONTH.plusMonths(2), sorting);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactlyInAnyOrder(MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(1)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(2)));
    }

    @Test
    @DisplayName("Returns all when not applying range")
    @MembershipActivePerson
    @FeeFullYear
    void testFindInRange_NoRange() {
        final Sorting                        sorting;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sorting);

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
        final Sorting                        sorting;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sorting);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("Can filter having in a range")
    @MembershipActivePerson
    @FeeFullYear
    void testFindInRange_Range() {
        final Sorting                        sorting;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(MemberBalanceConstants.START_MONTH.plusMonths(1),
            MemberBalanceConstants.START_MONTH.plusMonths(3), sorting);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactlyInAnyOrder(MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(1)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(2)),
                MonthlyMemberBalances.forMonth(MemberBalanceConstants.START_MONTH.plusMonths(3)));
    }

    @Test
    @DisplayName("Can filter having only the start date")
    @MembershipActivePerson
    @FeeFullYear
    void testFindInRange_Start() {
        final Sorting                        sorting;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(MemberBalanceConstants.START_MONTH.plusMonths(1), null, sorting);

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
