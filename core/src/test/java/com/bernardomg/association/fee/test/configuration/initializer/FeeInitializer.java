
package com.bernardomg.association.fee.test.configuration.initializer;

import org.springframework.stereotype.Component;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeEntities;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEntities;

@Component
public final class FeeInitializer {

    private final FeeSpringRepository         feeRepository;

    private final TransactionSpringRepository transactionRepository;

    public FeeInitializer(final FeeSpringRepository feeRepository,
            final TransactionSpringRepository transactionRepository) {
        super();
        this.feeRepository = feeRepository;
        this.transactionRepository = transactionRepository;
    }

    public final void registerFeeCurrentMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = FeeEntities.currentMonth();
        save(fee, paid);
    }

    public final void registerFeeCurrentMonthAlternative(final Boolean paid) {
        final FeeEntity fee;

        fee = FeeEntities.currentMonthAlternative();
        save(fee, paid);
    }

    public final void registerFeeNextMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = FeeEntities.nextMonth();
        save(fee, paid);
    }

    public final void registerFeeNextYear(final Boolean paid) {
        final FeeEntity fee;

        fee = FeeEntities.nextYear();
        save(fee, paid);
    }

    public final void registerFeePreviousMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = FeeEntities.previousMonth();
        save(fee, paid);
    }

    public final void registerFeePreviousYear(final Boolean paid) {
        final FeeEntity fee;

        fee = FeeEntities.previousYear();
        save(fee, paid);
    }

    public final void registerFeeTwoMonthsBack(final Boolean paid) {
        final FeeEntity fee;

        fee = FeeEntities.twoMonthsBack();
        save(fee, paid);
    }

    public final void registerFeeTwoYearsBack(final Boolean paid) {
        final FeeEntity fee;

        fee = FeeEntities.twoYearsBack();
        save(fee, paid);
    }

    private final TransactionEntity registerTransaction() {
        final TransactionEntity toSave;
        final TransactionEntity saved;
        final Long              index;

        index = transactionRepository.count() + 1;
        toSave = TransactionEntities.index(index);
        saved = transactionRepository.save(toSave);
        transactionRepository.flush();

        return saved;
    }

    private final void save(final FeeEntity fee, final Boolean paid) {
        final TransactionEntity transaction;

        if (paid) {
            transaction = registerTransaction();
            fee.setTransaction(transaction);
            fee.setPaid(true);
        } else {
            fee.setPaid(false);
        }

        feeRepository.save(fee);
        feeRepository.flush();
    }

}
