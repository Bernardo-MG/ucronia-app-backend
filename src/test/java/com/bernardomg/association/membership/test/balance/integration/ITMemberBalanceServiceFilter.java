
package com.bernardomg.association.membership.test.balance.integration;

import java.time.Month;
import java.time.YearMonth;
import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.membership.balance.model.MonthlyMemberBalance;
import com.bernardomg.association.membership.balance.model.request.MemberBalanceQuery;
import com.bernardomg.association.membership.balance.model.request.ValidatedMemberBalanceQuery;
import com.bernardomg.association.membership.balance.service.MemberBalanceService;
import com.bernardomg.association.membership.fee.persistence.model.PersistentFee;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Balance service - get balance - filter")
@Sql({ "/db/queries/member/single.sql", "/db/queries/member/alternative.sql" })
class ITMemberBalanceServiceFilter {

    @Autowired
    private FeeRepository        feeRepository;

    @Autowired
    private MemberBalanceService service;

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
        persist(yearMonth.getYear(), yearMonth.getMonth()
            .minus(1), false);
        persist(yearMonth.getYear(), yearMonth.getMonth(), false);
        persist(yearMonth.getYear(), yearMonth.getMonth()
            .plus(1), false);

        query = ValidatedMemberBalanceQuery.builder()
            .startDate(yearMonth.minusMonths(1))
            .endDate(yearMonth.plusMonths(1))
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(2);

        balancesItr = balances.iterator();
        balance = balancesItr.next();
        Assertions.assertThat(balance.getMonth())
            .isEqualTo(yearMonth.minusMonths(1));
        Assertions.assertThat(balance.getTotal())
            .isEqualTo(1);

        balance = balancesItr.next();
        Assertions.assertThat(balance.getMonth())
            .isEqualTo(yearMonth);
        Assertions.assertThat(balance.getTotal())
            .isEqualTo(1);
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
        persist(yearMonth.getYear(), yearMonth.getMonth()
            .minus(1), false);
        persist(yearMonth.getYear(), yearMonth.getMonth(), false);
        persist(yearMonth.getYear(), yearMonth.getMonth()
            .plus(1), false);

        query = ValidatedMemberBalanceQuery.builder()
            .startDate(yearMonth.minusMonths(1))
            .endDate(yearMonth.minusMonths(1))
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(1);

        balance = balances.iterator()
            .next();
        Assertions.assertThat(balance.getMonth())
            .isEqualTo(yearMonth.minusMonths(1));
        Assertions.assertThat(balance.getTotal())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Filtering with a range where the end is before the start returns nothing")
    void testGetBalance_Filter_RangeEndBeforeStart() {
        final YearMonth                                yearMonth;
        final MemberBalanceQuery                       query;
        final Sort                                     sort;
        final Iterable<? extends MonthlyMemberBalance> balances;

        yearMonth = YearMonth.now();
        persist(yearMonth.getYear(), yearMonth.getMonth()
            .minus(1), false);
        persist(yearMonth.getYear(), yearMonth.getMonth(), false);
        persist(yearMonth.getYear(), yearMonth.getMonth()
            .plus(1), false);

        query = ValidatedMemberBalanceQuery.builder()
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
        persist(yearMonth.getYear(), yearMonth.getMonth()
            .minus(1), false);
        persist(yearMonth.getYear(), yearMonth.getMonth(), false);
        persist(yearMonth.getYear(), yearMonth.getMonth()
            .plus(1), false);

        query = ValidatedMemberBalanceQuery.builder()
            .startDate(yearMonth.minusMonths(1))
            .endDate(yearMonth)
            .build();

        sort = Sort.unsorted();

        balances = service.getBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(2);

        balancesItr = balances.iterator();
        balance = balancesItr.next();
        Assertions.assertThat(balance.getMonth())
            .isEqualTo(yearMonth.minusMonths(1));
        Assertions.assertThat(balance.getTotal())
            .isEqualTo(1);

        balance = balancesItr.next();
        Assertions.assertThat(balance.getMonth())
            .isEqualTo(yearMonth);
        Assertions.assertThat(balance.getTotal())
            .isEqualTo(1);
    }

}
