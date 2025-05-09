
package com.bernardomg.association.transaction.test.util.initializer;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.stereotype.Component;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEntities;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public final class TransactionInitializer {

    public static final LocalDate             CURRENT_MONTH  = LocalDate.now();

    public static final LocalDate             NEXT_MONTH     = LocalDate.now()
        .plusMonths(1);

    public static final LocalDate             PREVIOUS_MONTH = LocalDate.now()
        .minusMonths(1);

    private final TransactionSpringRepository transactionRepository;

    public final void registerAt(final int year, final Month month) {
        final TransactionEntity transaction;
        final LocalDate         date;

        date = LocalDate.of(year, month, 1);
        transaction = TransactionEntities.forAmount(1F, date);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerCurrentMonth(final float amount) {
        final TransactionEntity transaction;

        transaction = TransactionEntities.forAmount(amount, CURRENT_MONTH);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerCurrentMonth(final float amount, final long index) {
        final TransactionEntity transaction;

        transaction = TransactionEntities.forAmount(amount, CURRENT_MONTH, index);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerMonthsBack(final float amount, final Integer diff, final long index) {
        final TransactionEntity transaction;
        final LocalDate         month;

        month = LocalDate.now()
            .minusMonths(diff);
        transaction = TransactionEntities.forAmount(amount, month, index);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerNextMonth(final float amount) {
        final TransactionEntity transaction;

        transaction = TransactionEntities.forAmount(amount, NEXT_MONTH);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerPreviousMonth(final float amount) {
        final TransactionEntity transaction;

        transaction = TransactionEntities.forAmount(amount, PREVIOUS_MONTH);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

    public final void registerPreviousMonth(final float amount, final long index) {
        final TransactionEntity transaction;

        transaction = TransactionEntities.forAmount(amount, PREVIOUS_MONTH, index);

        transactionRepository.save(transaction);
        transactionRepository.flush();
    }

}
