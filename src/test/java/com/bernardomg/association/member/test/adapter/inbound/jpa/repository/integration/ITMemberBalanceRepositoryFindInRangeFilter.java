
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.test.config.initializer.FeeInitializer;
import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.member.domain.repository.MemberBalanceRepository;
import com.bernardomg.association.member.test.config.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.config.factory.MemberBalanceConstants;
import com.bernardomg.association.member.test.config.factory.MonthlyMemberBalances;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberBalanceRepository - find in range - ranges")
@ActiveMember
@Disabled("Stop using the view")
class ITMemberBalanceRepositoryFindInRangeFilter {

    @Autowired
    private FeeInitializer          feeInitializer;

    @Autowired
    private MemberBalanceRepository memberBalanceRepository;

    @BeforeEach
    public void initializeFees() {
        feeInitializer.registerFeePreviousMonth(false);
        feeInitializer.registerFeeCurrentMonth(false);
        feeInitializer.registerFeeNextMonth(false);
    }

    @Test
    @DisplayName("Filtering with a range where the end is before the start returns nothing")
    void testFindInRange_CurrentToPrevious() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(MemberBalanceConstants.CURRENT_MONTH,
            MemberBalanceConstants.PREVIOUS_MONTH, sort);

        // THEN
        Assertions.assertThat(balances)
            .isEmpty();
    }

    @Test
    @DisplayName("Can filter without end month")
    void testFindInRange_Previous() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(MemberBalanceConstants.PREVIOUS_MONTH, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth(), MonthlyMemberBalances.currentMonth());
    }

    @Test
    @DisplayName("Can filter for two months")
    void testFindInRange_PreviousToCurrent() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(MemberBalanceConstants.PREVIOUS_MONTH,
            MemberBalanceConstants.CURRENT_MONTH, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth(), MonthlyMemberBalances.currentMonth());
    }

    @Test
    @DisplayName("Can filter around the current month")
    void testFindInRange_PreviousToNext() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(MemberBalanceConstants.PREVIOUS_MONTH,
            MemberBalanceConstants.NEXT_MONTH, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth(), MonthlyMemberBalances.currentMonth());
    }

    @Test
    @DisplayName("Can filter for the previous month")
    void testFindInRange_PreviousToPrevious() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(MemberBalanceConstants.PREVIOUS_MONTH,
            MemberBalanceConstants.PREVIOUS_MONTH, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth());
    }

    @Test
    @DisplayName("Can filter without starting month")
    void testFindInRange_ToNext() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        balances = memberBalanceRepository.findInRange(null, MemberBalanceConstants.NEXT_MONTH, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth(), MonthlyMemberBalances.currentMonth());
    }

}
