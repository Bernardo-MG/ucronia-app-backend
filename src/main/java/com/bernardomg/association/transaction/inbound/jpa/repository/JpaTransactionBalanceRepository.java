
package com.bernardomg.association.transaction.inbound.jpa.repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.transaction.domain.model.TransactionBalanceQuery;
import com.bernardomg.association.transaction.domain.model.TransactionCurrentBalance;
import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;
import com.bernardomg.association.transaction.domain.repository.TransactionBalanceRepository;
import com.bernardomg.association.transaction.inbound.jpa.model.MonthlyBalanceEntity;
import com.bernardomg.association.transaction.inbound.jpa.specification.MonthlyBalanceSpecifications;

public final class JpaTransactionBalanceRepository implements TransactionBalanceRepository {

    private final MonthlyBalanceSpringRepository monthlyBalanceRepository;

    public JpaTransactionBalanceRepository(final MonthlyBalanceSpringRepository monthlyBalanceRepo) {
        super();

        monthlyBalanceRepository = monthlyBalanceRepo;
    }

    @Override
    public final Optional<TransactionCurrentBalance> findCurrent() {
        final Optional<MonthlyBalanceEntity>      readBalance;
        final LocalDate                           month;
        final Optional<TransactionCurrentBalance> currentBalance;
        final LocalDate                           balanceDate;
        final float                               results;

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

            currentBalance = Optional.of(TransactionCurrentBalance.builder()
                .total(readBalance.get()
                    .getTotal())
                .results(results)
                .build());
        }

        return currentBalance;
    }

    @Override
    public final Collection<TransactionMonthlyBalance> getMonthlyBalance(final TransactionBalanceQuery query,
            final Sort sort) {
        final Optional<Specification<MonthlyBalanceEntity>> requestSpec;
        final Specification<MonthlyBalanceEntity>           limitSpec;
        final Specification<MonthlyBalanceEntity>           spec;
        final Collection<MonthlyBalanceEntity>              balance;

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

        balance = monthlyBalanceRepository.findAll(spec, sort);

        return balance.stream()
            .map(this::toDomain)
            .toList();
    }

    private final TransactionMonthlyBalance toDomain(final MonthlyBalanceEntity entity) {
        final YearMonth month;

        month = YearMonth.of(entity.getMonth()
            .getYear(),
            entity.getMonth()
                .getMonth());
        return TransactionMonthlyBalance.builder()
            .date(month)
            .total(entity.getTotal())
            .results(entity.getResults())
            .build();
    }

}
