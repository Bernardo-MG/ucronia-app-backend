
package com.bernardomg.association.fee.test.configuration.initializer;

import org.springframework.stereotype.Component;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTransactionEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeTransactionSpringRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeEntities;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;

@Component
public final class FeeInitializer {

    private static final FeeTransactionEntity indexTransaction(final long index) {
        final FeeTransactionEntity entity = new FeeTransactionEntity();
        entity.setIndex(index);
        entity.setAmount(TransactionConstants.AMOUNT);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION);
        return entity;
    }

    private final FeeSpringRepository            feeRepository;

    private final FeeTransactionSpringRepository transactionRepository;

    public FeeInitializer(final FeeSpringRepository feeRepository,
            final FeeTransactionSpringRepository transactionRepository) {
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

    private final FeeTransactionEntity registerTransaction() {
        final FeeTransactionEntity toSave;
        final FeeTransactionEntity saved;
        final Long                 index;

        index = transactionRepository.count() + 1;
        toSave = indexTransaction(index);
        saved = transactionRepository.save(toSave);
        transactionRepository.flush();

        return saved;
    }

    private final void save(final FeeEntity fee, final Boolean paid) {
        final FeeTransactionEntity transaction;

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
