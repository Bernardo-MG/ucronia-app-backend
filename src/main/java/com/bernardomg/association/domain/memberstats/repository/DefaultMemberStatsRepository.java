
package com.bernardomg.association.domain.memberstats.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.bernardomg.association.domain.memberstats.model.DtoMemberStats;
import com.bernardomg.association.domain.memberstats.model.MemberStats;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class DefaultMemberStatsRepository implements MemberStatsRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String                     queryActive   = "SELECT COUNT(m.id) FROM members m WHERE m.active = true";

    private final String                     queryInactive = "SELECT COUNT(m.id) FROM members m WHERE m.active = false";

    private final String                     queryTotal    = "SELECT COUNT(m.id) FROM members m";

    @Override
    public final MemberStats findStats() {
        final SqlParameterSource namedParameters;
        final Integer            total;
        final Integer            active;
        final Integer            inactive;
        final DtoMemberStats     stats;

        namedParameters = new MapSqlParameterSource();

        total = jdbcTemplate.queryForObject(queryTotal, namedParameters, Integer.class);
        active = jdbcTemplate.queryForObject(queryActive, namedParameters, Integer.class);
        inactive = jdbcTemplate.queryForObject(queryInactive, namedParameters, Integer.class);

        stats = new DtoMemberStats();
        stats.setActive(active);
        stats.setInactive(inactive);
        stats.setTotal(total);

        return stats;
    }

}
