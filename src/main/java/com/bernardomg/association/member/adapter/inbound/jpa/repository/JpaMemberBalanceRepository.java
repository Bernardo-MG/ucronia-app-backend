
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
    public final Collection<MonthlyMemberBalance> findInRange(final YearMonth startDate, final YearMonth endDate,
            final Sorting sorting) {
        final Optional<Specification<MonthlyMemberBalanceEntity>> spec;
        final Collection<MonthlyMemberBalanceEntity>              balances;
        final Collection<MonthlyMemberBalance>                    monthlyBalances;
        final Sort                                                sort;
        final Instant                                             startDateParsed;
        final Instant                                             endDateParsed;

        // TODO: the dates are optional

        log.debug("Finding balance in from {} to {} sorting by {}", startDate, endDate, sorting);

        // Specification from the request
        if (startDate == null) {
            startDateParsed = null;
        } else {
            startDateParsed = startDate.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant();
        }
        if (endDate == null) {
            // TODO: better use optional
            endDateParsed = null;
        } else {
            endDateParsed = endDate.atEndOfMonth()
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant();
        }
        spec = MonthlyMemberBalanceSpecifications.inRange(startDateParsed, endDateParsed);

        sort = SpringSorting.toSort(sorting);
        if (spec.isPresent()) {
            balances = monthlyMemberBalanceRepository.findAll(spec.get(), sort);
        } else {
            balances = monthlyMemberBalanceRepository.findAll(sort);
        }

        monthlyBalances = balances.stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found balance from {} to {}: {}", startDate, endDate, monthlyBalances);

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
