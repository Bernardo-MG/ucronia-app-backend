
package com.bernardomg.association.membership.test.balance.integration;

import java.time.Month;
import java.time.YearMonth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.funds.test.configuration.argument.CurrentAndPreviousMonthProvider;
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
@DisplayName("Membership balance service - get balance")
@ValidMember
@AlternativeMember
class ITMembershipBalanceService {

    @Autowired
    private FeeRepository            feeRepository;

    @Autowired
    private MembershipBalanceService service;

    private final void persist(final Integer year, final Month month, final boolean paid) {
        final FeeEntity entity;

        entity = FeeEntity.builder()
            .date(YearMonth.of(year, month))
            .memberId(1l)
            .paid(paid)
            .build();

        feeRepository.save(entity);
        feeRepository.flush();
    }

    private final void persistAlternative(final Integer year, final Month month) {
        final FeeEntity entity;

        entity = FeeEntity.builder()
            .date(YearMonth.of(year, month))
            .memberId(2l)
            .paid(true)
            .build();

        feeRepository.save(entity);
        feeRepository.flush();
    }

    @Test
    @DisplayName("With a fee for the current month and not paid it returns balance for this month")
    void testGetBalance_CurrentMonth_NotPaid() {
        final YearMonth                                month;
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final MonthlyMemberBalance                     balance;

        month = YearMonth.now();
        persist(month.getYear(), month.getMonth(), false);

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(1);

        balance = balances.iterator()
            .next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance, MonthlyMemberBalances.forMonth(month));
    }

    @ParameterizedTest(name = "Date: {0}")
    @ArgumentsSource(CurrentAndPreviousMonthProvider.class)
    @DisplayName("Returns balance for the current month and adjacents")
    void testGetBalance_Dates(final YearMonth month) {
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final MonthlyMemberBalance                     balance;

        persist(month.getYear(), month.getMonth(), true);

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(1);

        balance = balances.iterator()
            .next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance, MonthlyMemberBalances.forMonth(month));
    }

    @Test
    @DisplayName("Returns no balance for the next month")
    void testGetBalance_NextMonth() {
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final YearMonth                                date;

        date = YearMonth.now()
            .plusMonths(1);
        persist(date.getYear(), date.getMonth(), true);

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
    @DisplayName("With fees for two members this month it returns balance for both this month")
    void testGetBalance_TwoMembers() {
        final YearMonth                                month;
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final MonthlyMemberBalance                     balance;

        month = YearMonth.now();
        persist(month.getYear(), month.getMonth(), true);
        persistAlternative(month.getYear(), month.getMonth());

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(1);

        balance = balances.iterator()
            .next();
        MonthlyMemberBalanceAssertions.isEqualTo(balance, MonthlyMemberBalances.forMonthAndTotal(month, 2L));
    }

}
