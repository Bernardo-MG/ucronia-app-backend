
package com.bernardomg.association.transaction.adapter.inbound.jpa.repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.MonthlyBalanceEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.specification.MonthlyBalanceSpecifications;
import com.bernardomg.association.transaction.domain.model.TransactionBalanceQuery;
import com.bernardomg.association.transaction.domain.model.TransactionCurrentBalance;
import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;
import com.bernardomg.association.transaction.domain.repository.TransactionBalanceRepository;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringSorting;

@Repository
@Transactional
public final class JpaTransactionBalanceRepository implements TransactionBalanceRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                  log = LoggerFactory.getLogger(JpaTransactionBalanceRepository.class);

    private final MonthlyBalanceSpringRepository monthlyBalanceRepository;

    public JpaTransactionBalanceRepository(final MonthlyBalanceSpringRepository monthlyBalanceRepo) {
        super();

        monthlyBalanceRepository = Objects.requireNonNull(monthlyBalanceRepo);
    }

    @Override
    public final Optional<TransactionCurrentBalance> findCurrent() {
        final Optional<MonthlyBalanceEntity>      readBalance;
        final LocalDate                           month;
        final Optional<TransactionCurrentBalance> currentBalance;
        final LocalDate                           balanceDate;
        final float                               results;

        log.debug("Finding current balance");

        // Find latest monthly balance
        // Ignore future balances
        month = LocalDate.now()
            .withDayOfMonth(1);
        readBalance = monthlyBalanceRepository.findLatestInOrBefore(month);

        if (readBalance.isEmpty()) {
            currentBalance = Optional.empty();
        } else {
            balanceDate = readBalance.get()
                .getMonth();

            // Take the results only if it's the current year and month
            if ((balanceDate.getYear() == month.getYear()) && (balanceDate.getMonth()
                .equals(month.getMonth()))) {
                results = readBalance.get()
                    .getResults();
            } else {
                results = 0;
            }

            currentBalance = Optional.of(new TransactionCurrentBalance(results, readBalance.get()
                .getTotal()));
        }

        log.debug("Found current balance: {}", currentBalance);

        return currentBalance;
    }

    @Override
    public final Collection<TransactionMonthlyBalance> findMonthlyBalance(final TransactionBalanceQuery query,
            final Sorting sorting) {
        final Optional<Specification<MonthlyBalanceEntity>> requestSpec;
        final Specification<MonthlyBalanceEntity>           limitSpec;
        final Specification<MonthlyBalanceEntity>           spec;
        final Collection<MonthlyBalanceEntity>              balance;
        final Collection<TransactionMonthlyBalance>         monthlyBalance;
        final Sort                                          sort;

        log.debug("Finding monthly balance");

        // Specification from the request
        requestSpec = MonthlyBalanceSpecifications.fromQuery(query);
        // Up to this month
        limitSpec = MonthlyBalanceSpecifications.before(YearMonth.now()
            .plusMonths(1));

        // Combine specifications
        if (requestSpec.isPresent()) {
            spec = requestSpec.get()
                .and(limitSpec);
        } else {
            spec = limitSpec;
        }

        sort = SpringSorting.toSort(sorting);
        balance = monthlyBalanceRepository.findAll(spec, sort);

        monthlyBalance = balance.stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found monthly balance {}", monthlyBalance);

        return monthlyBalance;
    }

    private final TransactionMonthlyBalance toDomain(final MonthlyBalanceEntity entity) {
        final YearMonth month;

        month = YearMonth.of(entity.getMonth()
            .getYear(),
            entity.getMonth()
                .getMonth());
        return new TransactionMonthlyBalance(month, entity.getResults(), entity.getTotal());
    }

}
