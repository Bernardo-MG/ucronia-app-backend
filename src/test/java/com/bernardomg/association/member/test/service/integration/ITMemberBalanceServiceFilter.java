
package com.bernardomg.association.member.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.test.util.initializer.FeeInitializer;
import com.bernardomg.association.member.model.MemberBalanceQuery;
import com.bernardomg.association.member.model.MonthlyMemberBalance;
import com.bernardomg.association.member.service.MemberBalanceService;
import com.bernardomg.association.member.test.config.annotation.AlternativeMember;
import com.bernardomg.association.member.test.config.annotation.ValidMember;
import com.bernardomg.association.member.test.config.factory.MemberBalanceQueryRequests;
import com.bernardomg.association.member.test.config.factory.MonthlyMemberBalances;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member balance service - get balance - filter")
@ValidMember
@AlternativeMember
class ITMemberBalanceServiceFilter {

    @Autowired
    private FeeInitializer       feeInitializer;

    @Autowired
    private MemberBalanceService service;

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
        balances = service.getMonthlyBalance(query, sort);

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
        balances = service.getMonthlyBalance(query, sort);

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
        balances = service.getMonthlyBalance(query, sort);

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
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth(), MonthlyMemberBalances.currentMonth());
    }

}
