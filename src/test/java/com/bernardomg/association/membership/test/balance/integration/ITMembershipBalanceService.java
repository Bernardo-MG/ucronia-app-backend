
package com.bernardomg.association.membership.test.balance.integration;

import java.time.Month;
import java.time.YearMonth;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.membership.balance.model.MonthlyMemberBalance;
import com.bernardomg.association.membership.balance.model.request.MemberBalanceQuery;
import com.bernardomg.association.membership.balance.model.request.MemberBalanceQueryRequest;
import com.bernardomg.association.membership.balance.service.MembershipBalanceService;
import com.bernardomg.association.membership.fee.persistence.model.PersistentFee;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Membership balance service - get balance")
@Sql({ "/db/queries/member/single.sql", "/db/queries/member/alternative.sql" })
class ITMembershipBalanceService {

    private static Stream<Arguments> geValidDates() {
        return Stream.of(
            // This month
            Arguments.of(YearMonth.now()),
            // Previous month
            Arguments.of(YearMonth.now()
                .minusMonths(1)));
    }

    @Autowired
    private FeeRepository            feeRepository;

    @Autowired
    private MembershipBalanceService service;

    private final void persist(final Integer year, final Month month, final boolean paid) {
        final PersistentFee entity;

        entity = PersistentFee.builder()
            .date(YearMonth.of(year, month))
            .memberId(1l)
            .paid(paid)
            .build();

        feeRepository.save(entity);
        feeRepository.flush();
    }

    private final void persistAlternative(final Integer year, final Month month) {
        final PersistentFee entity;

        entity = PersistentFee.builder()
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
        final YearMonth                                yearMonth;
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final MonthlyMemberBalance                     balance;

        yearMonth = YearMonth.now();
        persist(yearMonth.getYear(), yearMonth.getMonth(), false);

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(1);

        balance = balances.iterator()
            .next();
        Assertions.assertThat(balance.getMonth())
            .isEqualTo(yearMonth);
        Assertions.assertThat(balance.getTotal())
            .isEqualTo(1);
    }

    @ParameterizedTest(name = "Date: {0}")
    @MethodSource("geValidDates")
    @DisplayName("Returns balance for the current month and adjacents")
    void testGetBalance_Dates(final YearMonth date) {
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final MonthlyMemberBalance                     balance;

        persist(date.getYear(), date.getMonth(), true);

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(1);

        balance = balances.iterator()
            .next();
        Assertions.assertThat(balance.getMonth())
            .isEqualTo(date);
        Assertions.assertThat(balance.getTotal())
            .isEqualTo(1);
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
            .isEmpty();
    }

    @Test
    @DisplayName("With fees for two members this month it returns balance for both this month")
    void testGetBalance_TwoMembers() {
        final YearMonth                                yearMonth;
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;
        final MonthlyMemberBalance                     balance;

        yearMonth = YearMonth.now();
        persist(yearMonth.getYear(), yearMonth.getMonth(), true);
        persistAlternative(yearMonth.getYear(), yearMonth.getMonth());

        query = MemberBalanceQueryRequest.builder()
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(1);

        balance = balances.iterator()
            .next();
        Assertions.assertThat(balance.getMonth())
            .isEqualTo(yearMonth);
        Assertions.assertThat(balance.getTotal())
            .isEqualTo(2);
    }

}
