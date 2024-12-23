
package com.bernardomg.association.member.domain.repository;

import java.time.YearMonth;

import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.data.domain.Sorting;

public interface MemberBalanceRepository {

    public Iterable<MonthlyMemberBalance> findInRange(final YearMonth startDate, final YearMonth endDate,
            final Sorting sorting);

}
