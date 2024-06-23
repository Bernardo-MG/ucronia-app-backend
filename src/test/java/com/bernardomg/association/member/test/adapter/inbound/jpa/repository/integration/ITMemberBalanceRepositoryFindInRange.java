
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.test.config.initializer.FeeInitializer;
import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.member.domain.repository.MemberBalanceRepository;
import com.bernardomg.association.member.test.config.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.config.data.annotation.AlternativeActiveMember;
import com.bernardomg.association.member.test.config.factory.MonthlyMemberBalances;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberBalanceRepository - find in range")
@ActiveMember
class ITMemberBalanceRepositoryFindInRange {

    @Autowired
    private FeeInitializer          feeInitializer;

    @Autowired
    private MemberBalanceRepository memberBalanceRepository;

    @Test
    @DisplayName("With a fee for the current month and not paid it returns balance for this month")
    void testFindInRange_NoRange_CurrentMonth_NotPaid() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sort = Sort.by("month");

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.currentMonth());
    }

    @Test
    @DisplayName("With a fee for the current month and paid it returns balance for this month")
    void testFindInRange_NoRange_CurrentMonth_Paid() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sort = Sort.by("month");

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.currentMonth());
    }

    @Test
    @DisplayName("With a fee for the next month and not paid it returns no balance")
    void testFindInRange_NoRange_NextMonth_NotPaid() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeNextMonth(false);

        sort = Sort.by("month");

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("With a fee for the next month and paid it returns no balance")
    void testFindInRange_NoRange_NextMonth_Paid() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeNextMonth(true);

        sort = Sort.by("month");

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testFindInRange_NoRange_NoData() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sort = Sort.by("month");

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("With fees for previous and current months it returns balance for both of them")
    void testFindInRange_NoRange_PreviousCurrentMonths() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(true);
        feeInitializer.registerFeeCurrentMonth(true);
        feeInitializer.registerFeeNextMonth(true);

        sort = Sort.by("month");

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth(), MonthlyMemberBalances.currentMonth());
    }

    @Test
    @DisplayName("With fees for previous, current and next months it returns balance for the previous and current")
    void testFindInRange_NoRange_PreviousCurrentNextMonths() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(true);
        feeInitializer.registerFeeCurrentMonth(true);
        feeInitializer.registerFeeNextMonth(true);

        sort = Sort.by("month");

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth(), MonthlyMemberBalances.currentMonth());
    }

    @Test
    @DisplayName("With a fee for the previous month and not paid it returns balance for the previous month")
    void testFindInRange_NoRange_PreviousMonth_NotPaid() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(false);

        sort = Sort.by("month");

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth());
    }

    @Test
    @DisplayName("With a fee for the previous month and paid it returns balance for the previous month")
    void testFindInRange_NoRange_PreviousMonth_Paid() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(true);

        sort = Sort.by("month");

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.previousMonth());
    }

    @Test
    @DisplayName("With fees for two members this month it returns balance for both this month")
    @ActiveMember
    @AlternativeActiveMember
    void testFindInRange_NoRange_TwoMembers() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);
        feeInitializer.registerFeeCurrentMonthAlternative(true);

        sort = Sort.by("month");

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.currentMonth(2L));
    }

    @Test
    @DisplayName("With a fee for two months back and not paid it returns balance for the previous month")
    void testFindInRange_NoRange_TwoMonthsBack_NotPaid() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeTwoMonthsBack(false);

        sort = Sort.by("month");

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.twoMonthsBack());
    }

    @Test
    @DisplayName("With a fee for two months back and paid it returns balance for the previous month")
    void testFindInRange_NoRange_TwoMonthsBack_Paid() {
        final Sort                           sort;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        feeInitializer.registerFeeTwoMonthsBack(true);

        sort = Sort.by("month");

        // WHEN
        balances = memberBalanceRepository.findInRange(null, null, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.twoMonthsBack());
    }

}
