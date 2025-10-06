
package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MonthlyMemberBalanceEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.specification.MonthlyMemberBalanceSpecifications;
import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.member.domain.repository.MemberBalanceRepository;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringSorting;

@Repository
@Transactional
public final class JpaMemberBalanceRepository implements MemberBalanceRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                        log = LoggerFactory.getLogger(JpaMemberBalanceRepository.class);

    private final MonthlyMemberBalanceSpringRepository monthlyMemberBalanceRepository;

    public JpaMemberBalanceRepository(final MonthlyMemberBalanceSpringRepository monthlyMemberBalanceRepo) {
        super();

        monthlyMemberBalanceRepository = Objects.requireNonNull(monthlyMemberBalanceRepo);
    }

    @Override
    public final Collection<MonthlyMemberBalance> findInRange(final Instant from, final Instant to,
            final Sorting sorting) {
        final Optional<Specification<MonthlyMemberBalanceEntity>> spec;
        final Collection<MonthlyMemberBalanceEntity>              balances;
        final Collection<MonthlyMemberBalance>                    monthlyBalances;
        final Sort                                                sort;

        // TODO: the dates are optional

        log.debug("Finding balance in from {} to {} sorting by {}", from, to, sorting);

        spec = MonthlyMemberBalanceSpecifications.inRange(from, to);

        sort = SpringSorting.toSort(sorting);
        if (spec.isPresent()) {
            balances = monthlyMemberBalanceRepository.findAll(spec.get(), sort);
        } else {
            balances = monthlyMemberBalanceRepository.findAll(sort);
        }

        monthlyBalances = balances.stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found balance from {} to {}: {}", from, to, monthlyBalances);

        return monthlyBalances;
    }

    private final MonthlyMemberBalance toDomain(final MonthlyMemberBalanceEntity entity) {
        final YearMonth month;
        final LocalDate monthParsed;

        monthParsed = LocalDate.ofInstant(entity.getMonth(), ZoneOffset.UTC);
        month = YearMonth.of(monthParsed.getYear(), monthParsed.getMonth());
        return new MonthlyMemberBalance(month, entity.getTotal());
    }

}
