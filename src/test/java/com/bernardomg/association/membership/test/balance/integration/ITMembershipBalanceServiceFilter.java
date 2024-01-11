
package com.bernardomg.association.membership.test.balance.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.membership.balance.service.MembershipBalanceService;
import com.bernardomg.association.membership.test.balance.config.factory.MemberBalanceQueryRequests;
import com.bernardomg.association.membership.test.balance.config.factory.MonthlyMemberBalances;
import com.bernardomg.association.membership.test.fee.util.initializer.FeeInitializer;
import com.bernardomg.association.membership.test.member.config.AlternativeMember;
import com.bernardomg.association.membership.test.member.config.ValidMember;
import com.bernardomg.association.model.member.MemberBalanceQuery;
import com.bernardomg.association.model.member.MonthlyMemberBalance;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Membership balance service - get balance - filter")
@ValidMember
@AlternativeMember
class ITMembershipBalanceServiceFilter {

    @Autowired
    private FeeInitializer           feeInitializer;

    @Autowired
    private MembershipBalanceService service;

    @BeforeEach
    public void initializeFees() {
        feeInitializer.registerFeePreviousMonth(false);
        feeInitializer.registerFeeCurrentMonth(false);
        feeInitializer.registerFeeNextMonth(false);
    }

    @Test
    @DisplayName("Can filter around the current month")
    void testGetBalance_Filter_AroundCurrent() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        query = MemberBalanceQueryRequests.aroundCurrent();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth(), MonthlyMemberBalances.currentMonth());
    }

    @Test
    @DisplayName("Can filter for the previous month")
    void testGetBalance_Filter_PreviousMonth() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        query = MemberBalanceQueryRequests.aroundPrevious();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth());
    }

    @Test
    @DisplayName("Filtering with a range where the end is before the start returns nothing")
    void testGetBalance_Filter_RangeEndBeforeStart() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        query = MemberBalanceQueryRequests.endBeforeStart();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .isEmpty();
    }

    @Test
    @DisplayName("Can filter for two months")
    void testGetBalance_Filter_TwoMonths() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        query = MemberBalanceQueryRequests.previousAndThis();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth(), MonthlyMemberBalances.currentMonth());
    }

}
