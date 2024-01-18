
package com.bernardomg.association.member.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.member.model.MemberBalanceQuery;
import com.bernardomg.association.member.model.MonthlyMemberBalance;
import com.bernardomg.association.member.service.MemberBalanceService;
import com.bernardomg.association.member.test.config.factory.MemberBalanceQueryRequests;
import com.bernardomg.association.member.test.config.factory.MonthlyMemberBalances;
import com.bernardomg.association.member.test.util.assertion.MonthlyMemberBalanceAssertions;
import com.bernardomg.association.test.data.fee.initializer.FeeInitializer;
import com.bernardomg.association.test.data.member.annotation.AlternativeMember;
import com.bernardomg.association.test.data.member.annotation.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member balance service - get balance")
@ValidMember
class ITMemberBalanceService {

    @Autowired
    private FeeInitializer       feeInitializer;

    @Autowired
    private MemberBalanceService service;

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
        balances = service.getMonthlyBalance(query, sort);

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
        balances = service.getMonthlyBalance(query, sort);

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
        balances = service.getMonthlyBalance(query, sort);

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
        balances = service.getMonthlyBalance(query, sort);

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
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("With fees for previous and current months it returns balance for both of them")
    void testGetBalance_PreviousCurrentMonths() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(true);
        feeInitializer.registerFeeCurrentMonth(true);
        feeInitializer.registerFeeNextMonth(true);

        query = MemberBalanceQueryRequests.empty();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth(), MonthlyMemberBalances.currentMonth());
    }

    @Test
    @DisplayName("With fees for previous, current and next months it returns balance for the previous and current")
    void testGetBalance_PreviousCurrentNextMonths() {
        final MemberBalanceQuery             query;
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(true);
        feeInitializer.registerFeeCurrentMonth(true);
        feeInitializer.registerFeeNextMonth(true);

        query = MemberBalanceQueryRequests.empty();

        sort = Sort.unsorted();

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth(), MonthlyMemberBalances.currentMonth());
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
        balances = service.getMonthlyBalance(query, sort);

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
        balances = service.getMonthlyBalance(query, sort);

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
        balances = service.getMonthlyBalance(query, sort);

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
        balances = service.getMonthlyBalance(query, sort);

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
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.twoMonthsBack());
    }

}
