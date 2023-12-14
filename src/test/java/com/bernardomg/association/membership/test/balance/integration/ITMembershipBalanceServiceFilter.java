
package com.bernardomg.association.membership.test.balance.integration;

import java.time.Month;
import java.time.YearMonth;
import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.membership.balance.model.MonthlyMemberBalance;
import com.bernardomg.association.membership.balance.model.request.MemberBalanceQuery;
import com.bernardomg.association.membership.balance.model.request.MemberBalanceQueryRequest;
import com.bernardomg.association.membership.balance.service.MembershipBalanceService;
import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.test.balance.util.assertion.MonthlyMemberBalanceAssertions;
import com.bernardomg.association.membership.test.balance.util.model.MonthlyMemberBalances;
import com.bernardomg.association.membership.test.member.configuration.AlternativeMember;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Membership balance service - get balance - filter")
@ValidMember
@AlternativeMember
class ITMembershipBalanceServiceFilter {

    @Autowired
    private FeeRepository            feeRepository;

    @Autowired
    private MembershipBalanceService service;

    @BeforeEach
    public void initializeFees() {
        final YearMonth yearMonth;

        yearMonth = YearMonth.now();
        // Previous mont
        persist(yearMonth.getYear(), yearMonth.getMonth()
            .minus(1));
        // This month
        persist(yearMonth.getYear(), yearMonth.getMonth());
        // Next month
        persist(yearMonth.getYear(), yearMonth.getMonth()
            .plus(1));
    }

    private final void persist(final Integer year, final Month month) {
        final FeeEntity entity;

        entity = FeeEntity.builder()
            .date(YearMonth.of(year, month))
            .memberId(1l)
            .paid(false)
            .build();

        feeRepository.save(entity);
        feeRepository.flush();
    }

    @Test
    @DisplayName("Can filter around the current month")
    void testGetBalance_Filter_AroundCurrent() {
        final YearMonth                                yearMonth;
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final Iterator<? extends MonthlyMemberBalance> balancesItr;
        MonthlyMemberBalance                           balance;

        yearMonth = YearMonth.now();

        query = MemberBalanceQueryRequest.builder()
            .startDate(yearMonth.minusMonths(1))
            .endDate(yearMonth.plusMonths(1))
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(2);

        balancesItr = balances.iterator();
        balance = balancesItr.next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance, MonthlyMemberBalances.forMonth(yearMonth.minusMonths(1)));

        balance = balancesItr.next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance, MonthlyMemberBalances.forMonth(yearMonth));
    }

    @Test
    @DisplayName("Can filter for the previous month")
    void testGetBalance_Filter_PreviousMonth() {
        final YearMonth                                yearMonth;
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final MonthlyMemberBalance                     balance;

        yearMonth = YearMonth.now();

        query = MemberBalanceQueryRequest.builder()
            .startDate(yearMonth.minusMonths(1))
            .endDate(yearMonth.minusMonths(1))
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(1);

        balance = balances.iterator()
            .next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance, MonthlyMemberBalances.forMonth(yearMonth.minusMonths(1)));
    }

    @Test
    @DisplayName("Filtering with a range where the end is before the start returns nothing")
    void testGetBalance_Filter_RangeEndBeforeStart() {
        final YearMonth                                yearMonth;
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;

        yearMonth = YearMonth.now();

        query = MemberBalanceQueryRequest.builder()
            .startDate(yearMonth)
            .endDate(yearMonth.minusMonths(1))
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .isEmpty();
    }

    @Test
    @DisplayName("Can filter for two months")
    void testGetBalance_Filter_TwoMonths() {
        final YearMonth                                yearMonth;
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final Iterator<? extends MonthlyMemberBalance> balancesItr;
        MonthlyMemberBalance                           balance;

        yearMonth = YearMonth.now();

        query = MemberBalanceQueryRequest.builder()
            .startDate(yearMonth.minusMonths(1))
            .endDate(yearMonth)
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(2);

        balancesItr = balances.iterator();
        balance = balancesItr.next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance, MonthlyMemberBalances.forMonth(yearMonth.minusMonths(1)));

        balance = balancesItr.next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance, MonthlyMemberBalances.forMonth(yearMonth));
    }

}