
package com.bernardomg.association.member.domain.repository;

import java.time.Instant;
import java.util.Collection;

import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.data.domain.Sorting;

public interface MemberBalanceRepository {

    public Collection<MonthlyMemberBalance> findInRange(final Instant from, final Instant to, final Sorting sorting);

}
