
package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MonthlyMemberBalanceEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.specification.MonthlyMemberBalanceSpecifications;
import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.member.domain.repository.MemberBalanceRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JpaMemberBalanceRepository implements MemberBalanceRepository {

    private final MonthlyMemberBalanceSpringRepository monthlyMemberBalanceRepository;

    public JpaMemberBalanceRepository(final MonthlyMemberBalanceSpringRepository monthlyMemberBalanceRepo) {
        super();

        monthlyMemberBalanceRepository = monthlyMemberBalanceRepo;
    }

    @Override
    public final Iterable<MonthlyMemberBalance> findInRange(final YearMonth startDate, final YearMonth endDate,
            final Sort sort) {
        final Optional<Specification<MonthlyMemberBalanceEntity>> requestSpec;
        final Specification<MonthlyMemberBalanceEntity>           limitSpec;
        final Specification<MonthlyMemberBalanceEntity>           spec;
        final Collection<MonthlyMemberBalanceEntity>              balances;
        final Iterable<MonthlyMemberBalance>                      monthlyBalances;

        log.debug("Finding balance in from {} to {} sorted by {}", startDate, endDate, sort);

        // Specification from the request
        requestSpec = MonthlyMemberBalanceSpecifications.inRange(startDate, endDate);
        // Up to this month
        limitSpec = MonthlyMemberBalanceSpecifications.before(YearMonth.now()
            .plusMonths(1));

        // Combine specifications
        if (requestSpec.isPresent()) {
            spec = requestSpec.get()
                .and(limitSpec);
        } else {
            spec = limitSpec;
        }

        balances = monthlyMemberBalanceRepository.findAll(spec, sort);

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
        return MonthlyMemberBalance.builder()
            .withDate(month)
            .withTotal(entity.getTotal())
            .build();
    }

}
