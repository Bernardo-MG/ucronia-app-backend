
package com.bernardomg.association.domain.memberstats.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.domain.memberstats.model.MemberStats;
import com.bernardomg.association.domain.memberstats.repository.MemberStatsRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultMemberStatsService implements MemberStatsService {

    private final MemberStatsRepository repository;

    @Override
    @PreAuthorize("hasAuthority('READ_MEMBER_STATS')")
    public final MemberStats getStats() {
        return repository.findStats();
    }

}
