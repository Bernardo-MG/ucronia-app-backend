
package com.bernardomg.association.membership.test.balance.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.membership.balance.model.MemberBalanceQuery;
import com.bernardomg.association.membership.balance.model.MonthlyMemberBalance;
import com.bernardomg.association.membership.balance.service.MembershipBalanceService;
import com.bernardomg.association.membership.test.balance.util.assertion.MonthlyMemberBalanceAssertions;
import com.bernardomg.association.membership.test.balance.util.model.MemberBalanceQueryRequests;
import com.bernardomg.association.membership.test.balance.util.model.MonthlyMemberBalances;
import com.bernardomg.association.membership.test.fee.util.initializer.FeeInitializer;
import com.bernardomg.association.membership.test.member.configuration.AlternativeMember;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Membership balance service - get balance")
@ValidMember
class ITMembershipBalanceService {

    @Autowired
    private FeeInitializer           feeInitializer;

    @Autowired
    private MembershipBalanceService service;

    @Test
    @DisplayName("With a fee for the current month and not paid it returns balance for this month")
    void testGetBalance_CurrentMonth_NotPaid() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        query = MemberBalanceQueryRequests.empty();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.currentMonth());
    }

    @Test
    @DisplayName("With a fee for the current month and paid it returns balance for this month")
    void testGetBalance_CurrentMonth_Paid() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        query = MemberBalanceQueryRequests.empty();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.currentMonth());
    }

    @Test
    @DisplayName("With a fee for the next month and not paid it returns no balance")
    void testGetBalance_NextMonth_NotPaid() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeNextMonth(false);

        query = MemberBalanceQueryRequests.empty();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("With a fee for the next month and paid it returns no balance")
    void testGetBalance_NextMonth_Paid() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeNextMonth(true);

        query = MemberBalanceQueryRequests.empty();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testGetBalance_NoData() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        query = MemberBalanceQueryRequests.empty();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("With a fee for the previous month and not paid it returns balance for the previous month")
    void testGetBalance_PreviousMonth_NotPaid() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;
        final MonthlyMemberBalance           balance;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(false);

        query = MemberBalanceQueryRequests.empty();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(1);

        balance = balances.iterator()
            .next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance, MonthlyMemberBalances.previousMonth());
    }

    @Test
    @DisplayName("With a fee for the previous month and paid it returns balance for the previous month")
    void testGetBalance_PreviousMonth_Paid() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(true);

        query = MemberBalanceQueryRequests.empty();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth());
    }

    @Test
    @DisplayName("With fees for two members this month it returns balance for both this month")
    @ValidMember
    @AlternativeMember
    void testGetBalance_TwoMembers() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);
        feeInitializer.registerFeeCurrentMonthAlternative(true);

        query = MemberBalanceQueryRequests.empty();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.currentMonth(2L));
    }

    @Test
    @DisplayName("With a fee for two months back and not paid it returns balance for the previous month")
    void testGetBalance_TwoMonthsBack_NotPaid() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeTwoMonthsBack(false);

        query = MemberBalanceQueryRequests.empty();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.twoMonthsBack());
    }

    @Test
    @DisplayName("With a fee for two months back and paid it returns balance for the previous month")
    void testGetBalance_TwoMonthsBack_Paid() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeTwoMonthsBack(true);

        query = MemberBalanceQueryRequests.empty();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.twoMonthsBack());
    }

}
