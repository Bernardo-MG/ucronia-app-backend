
package com.bernardomg.association.fee.test.util.initializer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTransactionEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeTransactionSpringRepository;
import com.bernardomg.association.transaction.test.factory.FeeTransactionEntities;

@Component
public final class TransactionInitializer {

    public static final Instant                  CURRENT_MONTH       = YearMonth.now()
        .atDay(10)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant                  CURRENT_MONTH_END   = YearMonth.now()
        .atEndOfMonth()
        .atTime(23, 59)
        .toInstant(ZoneOffset.UTC);

    public static final Instant                  CURRENT_MONTH_START = YearMonth.now()
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant                  NEXT_MONTH          = ZonedDateTime.now()
        .plus(1, ChronoUnit.MONTHS)
        .toLocalDate()
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant                  PREVIOUS_MONTH      = ZonedDateTime.now()
        .minusMonths(1)
        .toLocalDate()
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    private final FeeTransactionSpringRepository transactionRepository;

    public TransactionInitializer(final FeeTransactionSpringRepository transactionRepository) {
        super();
        this.transactionRepository = transactionRepository;
    }

    public final void registerAt(final int year, final Month month) {
        final FeeTransactionEntity transaction;
        final Instant              date;

        date = LocalDate.of(year, month, 1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        transaction = FeeTransactionEntities.forAmount(1F, date);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerCurrentMonth(final float amount) {
        final FeeTransactionEntity transaction;

        transaction = FeeTransactionEntities.forAmount(amount, CURRENT_MONTH);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerCurrentMonth(final float amount, final long index) {
        final FeeTransactionEntity transaction;

        transaction = FeeTransactionEntities.forAmount(amount, CURRENT_MONTH, index);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerCurrentMonthEnd(final float amount) {
        final FeeTransactionEntity transaction;

        transaction = FeeTransactionEntities.forAmount(amount, CURRENT_MONTH_END);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerCurrentMonthStart(final float amount) {
        final FeeTransactionEntity transaction;

        transaction = FeeTransactionEntities.forAmount(amount, CURRENT_MONTH_START);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerMonthsBack(final float amount, final Integer diff, final long index) {
        final FeeTransactionEntity transaction;
        final Instant              month;

        month = LocalDate.now()
            .minusMonths(diff)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        transaction = FeeTransactionEntities.forAmount(amount, month, index);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerNextMonth(final float amount) {
        final FeeTransactionEntity transaction;

        transaction = FeeTransactionEntities.forAmount(amount, NEXT_MONTH);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerPreviousMonth(final float amount) {
        final FeeTransactionEntity transaction;

        transaction = FeeTransactionEntities.forAmount(amount, PREVIOUS_MONTH);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerPreviousMonth(final float amount, final long index) {
        final FeeTransactionEntity transaction;

        transaction = FeeTransactionEntities.forAmount(amount, PREVIOUS_MONTH, index);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

}
