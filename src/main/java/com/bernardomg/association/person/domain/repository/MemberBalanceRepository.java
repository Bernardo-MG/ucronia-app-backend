
package com.bernardomg.association.person.domain.repository;

import java.time.YearMonth;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;

public interface MemberBalanceRepository {

    public Iterable<MonthlyMemberBalance> findInRange(final YearMonth startDate, final YearMonth endDate,
            final Sort sort);

}
