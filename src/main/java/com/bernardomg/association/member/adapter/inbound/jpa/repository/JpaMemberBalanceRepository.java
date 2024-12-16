
package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MonthlyMemberBalanceEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.specification.MonthlyMemberBalanceSpecifications;
import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.member.domain.repository.MemberBalanceRepository;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;

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
            final Sorting sorting) {
        final Optional<Specification<MonthlyMemberBalanceEntity>> spec;
        final Collection<MonthlyMemberBalanceEntity>              balances;
        final Iterable<MonthlyMemberBalance>                      monthlyBalances;
        final Sort                                                sort;

        // TODO: the dates are optional

        log.debug("Finding balance in from {} to {} sorting by {}", startDate, endDate, sorting);

        // Specification from the request
        spec = MonthlyMemberBalanceSpecifications.inRange(startDate, endDate);

        sort = toSort(sorting);
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

    private final Order toOrder(final Property property) {
        final Order order;

        if (Direction.ASC.equals(property.direction())) {
            order = Order.asc(property.name());
        } else {
            order = Order.desc(property.name());
        }

        return order;
    }

    private final Sort toSort(final Sorting sorting) {
        return Sort.by(sorting.properties()
            .stream()
            .map(this::toOrder)
            .toList());
    }

}
