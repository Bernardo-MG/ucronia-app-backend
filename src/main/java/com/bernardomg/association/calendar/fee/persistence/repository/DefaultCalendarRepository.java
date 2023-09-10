
package com.bernardomg.association.calendar.fee.persistence.repository;

import java.util.Collection;
import java.util.Collections;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bernardomg.association.calendar.fee.model.FeeCalendarRange;
import com.bernardomg.association.calendar.fee.model.ImmutableFeeCalendarRange;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public final class DefaultCalendarRepository implements FeeCalendarRepository {

    private static final String              QUERY_RANGE                    = "SELECT extract(year from f.date) AS feeYear FROM fees f GROUP BY feeYear ORDER BY feeYear ASC";

    private static final String              QUERY_RANGE_WITH_ACTIVE_MEMBER = "SELECT extract(year from f.date) AS feeYear FROM fees f JOIN members m ON f.member_id = m.id WHERE m.active = true GROUP BY feeYear ORDER BY feeYear ASC";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public final FeeCalendarRange findRange(final boolean onlyActive) {
        final Collection<Integer> years;
        final String              query;

        if (onlyActive) {
            query = QUERY_RANGE_WITH_ACTIVE_MEMBER;
        } else {
            query = QUERY_RANGE;
        }

        years = jdbcTemplate.queryForList(query, Collections.emptyMap(), Integer.class);
        return ImmutableFeeCalendarRange.builder()
            .years(years)
            .build();
    }

}
