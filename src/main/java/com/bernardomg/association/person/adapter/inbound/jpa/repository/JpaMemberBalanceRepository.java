
package com.bernardomg.association.person.adapter.inbound.jpa.repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MonthlyMemberBalanceEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MonthlyMemberBalanceSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.specification.MonthlyMemberBalanceSpecifications;
import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.person.domain.repository.MemberBalanceRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public final class JpaMemberBalanceRepository implements MemberBalanceRepository {

    private final MonthlyMemberBalanceSpringRepository monthlyMemberBalanceRepository;

    public JpaMemberBalanceRepository(final MonthlyMemberBalanceSpringRepository monthlyMemberBalanceRepo) {
        super();

        monthlyMemberBalanceRepository = Objects.requireNonNull(monthlyMemberBalanceRepo);
    }

    @Override
    public final Iterable<MonthlyMemberBalance> findInRange(final YearMonth startDate, final YearMonth endDate,
            final Sort sort) {
        final Optional<Specification<MonthlyMemberBalanceEntity>> spec;
        final Collection<MonthlyMemberBalanceEntity>              balances;
        final Iterable<MonthlyMemberBalance>                      monthlyBalances;

        // TODO: the dates are optional

        log.debug("Finding balance in from {} to {} sorted by {}", startDate, endDate, sort);

        // Specification from the request
        spec = MonthlyMemberBalanceSpecifications.inRange(startDate, endDate);

        if (spec.isPresent()) {
            balances = monthlyMemberBalanceRepository.findAll(spec.get(), sort);
        } else {
            balances = monthlyMemberBalanceRepository.findAll(sort);
        }

        monthlyBalances = balances.stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found balance in from {} to {}: {}", startDate, endDate, monthlyBalances);

        return monthlyBalances;
    }

    private final MonthlyMemberBalance toDomain(final MonthlyMemberBalanceEntity entity) {
        final YearMonth month;

        month = YearMonth.of(entity.getMonth()
            .getYear(),
            entity.getMonth()
                .getMonth());
        return new MonthlyMemberBalance(month, entity.getTotal());
    }

}
