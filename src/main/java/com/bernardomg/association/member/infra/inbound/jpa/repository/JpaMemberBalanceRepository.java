
package com.bernardomg.association.member.infra.inbound.jpa.repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.member.domain.repository.MemberBalanceRepository;
import com.bernardomg.association.member.infra.inbound.jpa.model.MonthlyMemberBalanceEntity;
import com.bernardomg.association.member.infra.inbound.jpa.specification.MonthlyMemberBalanceSpecifications;

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

        return balances.stream()
            .map(this::toDomain)
            .toList();
    }

    private final MonthlyMemberBalance toDomain(final MonthlyMemberBalanceEntity entity) {
        final YearMonth month;

        month = YearMonth.of(entity.getMonth()
            .getYear(),
            entity.getMonth()
                .getMonth());
        return MonthlyMemberBalance.builder()
            .date(month)
            .total(entity.getTotal())
            .build();
    }

}
