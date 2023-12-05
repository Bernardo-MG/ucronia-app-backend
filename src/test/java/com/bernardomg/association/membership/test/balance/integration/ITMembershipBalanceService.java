
package com.bernardomg.association.membership.test.balance.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.membership.balance.model.MonthlyMemberBalance;
import com.bernardomg.association.membership.balance.model.request.MemberBalanceQuery;
import com.bernardomg.association.membership.balance.model.request.MemberBalanceQueryRequest;
import com.bernardomg.association.membership.balance.service.MembershipBalanceService;
import com.bernardomg.association.membership.test.balance.util.assertion.MonthlyMemberBalanceAssertions;
import com.bernardomg.association.membership.test.balance.util.model.MonthlyMemberBalances;
import com.bernardomg.association.membership.test.fee.util.initializer.FeeInitializer;
import com.bernardomg.association.membership.test.member.configuration.AlternativeMember;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Membership balance service - get balance")
@ValidMember
@AlternativeMember
class ITMembershipBalanceService {

    @Autowired
    private FeeInitializer           feeInitializer;

    @Autowired
    private MembershipBalanceService service;

    @Test
    @DisplayName("With a fee for the current month and not paid it returns balance for this month")
    void testGetBalance_CurrentMonth_NotPaid() {
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final MonthlyMemberBalance                     balance;

        feeInitializer.registerFeeCurrentMonth(false);

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(1);

        balance = balances.iterator()
            .next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance, MonthlyMemberBalances.forMonth(FeeInitializer.CURRENT_MONTH));
    }

    @Test
    @DisplayName("With a fee for the current month and paid it returns balance for this month")
    void testGetBalance_CurrentMonth_Paid() {
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final MonthlyMemberBalance                     balance;

        feeInitializer.registerFeeCurrentMonth(true);

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(1);

        balance = balances.iterator()
            .next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance, MonthlyMemberBalances.forMonth(FeeInitializer.CURRENT_MONTH));
    }

    @Test
    @DisplayName("With a fee for the next month and not paid it returns no balance")
    void testGetBalance_NextMonth_NotPaid() {
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;

        feeInitializer.registerFeeNextMonth(false);

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("With a fee for the next month and paid it returns no balance")
    void testGetBalance_NextMonth_Paid() {
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;

        feeInitializer.registerFeeNextMonth(true);

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testGetBalance_NoData() {
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("With a fee for the previous month and not paid it returns balance for the previous month")
    void testGetBalance_PreviousMonth_NotPaid() {
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final MonthlyMemberBalance                     balance;

        feeInitializer.registerFeePreviousMonth(false);

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(1);

        balance = balances.iterator()
            .next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance,
            MonthlyMemberBalances.forMonth(FeeInitializer.PREVIOUS_MONTH));
    }

    @Test
    @DisplayName("With a fee for the previous month and paid it returns balance for the previous month")
    void testGetBalance_PreviousMonth_Paid() {
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final MonthlyMemberBalance                     balance;

        feeInitializer.registerFeePreviousMonth(true);

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(1);

        balance = balances.iterator()
            .next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance,
            MonthlyMemberBalances.forMonth(FeeInitializer.PREVIOUS_MONTH));
    }

    @Test
    @DisplayName("With fees for two members this month it returns balance for both this month")
    void testGetBalance_TwoMembers() {
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final MonthlyMemberBalance                     balance;

        feeInitializer.registerFeeCurrentMonth(true);
        feeInitializer.registerFeeCurrentMonth(true);

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(1);

        balance = balances.iterator()
            .next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance,
            MonthlyMemberBalances.forMonthAndTotal(FeeInitializer.CURRENT_MONTH, 2L));
    }

}
