
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.test.config.initializer.FeeInitializer;
import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.member.domain.repository.MemberBalanceRepository;
import com.bernardomg.association.member.test.config.data.annotation.SingleMember;
import com.bernardomg.association.member.test.config.factory.MemberBalanceConstants;
import com.bernardomg.association.member.test.config.factory.MonthlyMemberBalances;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberBalanceRepository - find in range - filter")
@SingleMember
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
    @DisplayName("Can filter around the current month")
    void testGetBalance_Filter_AroundCurrent() {
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
    void testGetBalance_Filter_PreviousMonth() {
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
    @DisplayName("Filtering with a range where the end is before the start returns nothing")
    void testGetBalance_Filter_RangeEndBeforeStart() {
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
    @DisplayName("Can filter for two months")
    void testGetBalance_Filter_TwoMonths() {
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

}
