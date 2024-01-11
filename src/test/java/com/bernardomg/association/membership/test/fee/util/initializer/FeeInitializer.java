
package com.bernardomg.association.membership.test.fee.util.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bernardomg.association.funds.test.transaction.config.factory.TransactionEntities;
import com.bernardomg.association.membership.test.fee.config.factory.FeeEntities;
import com.bernardomg.association.persistence.fee.model.FeeEntity;
import com.bernardomg.association.persistence.fee.model.FeePaymentEntity;
import com.bernardomg.association.persistence.fee.repository.FeePaymentRepository;
import com.bernardomg.association.persistence.fee.repository.FeeRepository;
import com.bernardomg.association.persistence.transaction.model.TransactionEntity;
import com.bernardomg.association.persistence.transaction.repository.TransactionRepository;

@Component
public final class FeeInitializer {

    @Autowired
    private FeePaymentRepository  feePaymentRepository;

    @Autowired
    private FeeRepository         feeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public final void registerFeeCurrentMonth(final Boolean paid) {
        final FeeEntity fee;
        final FeeEntity saved;

        fee = FeeEntities.currentMonth();

        saved = feeRepository.save(fee);

        if (paid) {
            registerPayment(saved.getId());
        }

        feeRepository.flush();
    }

    public final void registerFeeCurrentMonthAlternative(final Boolean paid) {
        final FeeEntity fee;
        final FeeEntity saved;

        fee = FeeEntities.currentMonthAlternative();

        saved = feeRepository.save(fee);

        if (paid) {
            registerPayment(saved.getId());
        }

        feeRepository.flush();
    }

    public final void registerFeeNextMonth(final Boolean paid) {
        final FeeEntity fee;
        final FeeEntity saved;

        fee = FeeEntities.nextMonth();

        saved = feeRepository.save(fee);

        if (paid) {
            registerPayment(saved.getId());
        }

        feeRepository.flush();
    }

    public final void registerFeeNextYear(final Boolean paid) {
        final FeeEntity fee;
        final FeeEntity saved;

        fee = FeeEntities.nextYear();

        saved = feeRepository.save(fee);

        if (paid) {
            registerPayment(saved.getId());
        }

        feeRepository.flush();
    }

    public final void registerFeePreviousMonth(final Boolean paid) {
        final FeeEntity fee;
        final FeeEntity saved;

        fee = FeeEntities.previousMonth();

        saved = feeRepository.save(fee);

        if (paid) {
            registerPayment(saved.getId());
        }

        feeRepository.flush();
    }

    public final void registerFeePreviousYear(final Boolean paid) {
        final FeeEntity fee;
        final FeeEntity saved;

        fee = FeeEntities.previousYear();

        saved = feeRepository.save(fee);

        if (paid) {
            registerPayment(saved.getId());
        }

        feeRepository.flush();
    }

    public final void registerFeeTwoMonthsBack(final Boolean paid) {
        final FeeEntity fee;
        final FeeEntity saved;

        fee = FeeEntities.twoMonthsBack();

        saved = feeRepository.save(fee);

        if (paid) {
            registerPayment(saved.getId());
        }

        feeRepository.flush();
    }

    public final void registerFeeTwoYearsBack(final Boolean paid) {
        final FeeEntity fee;
        final FeeEntity saved;

        fee = FeeEntities.twoYearsBack();

        saved = feeRepository.save(fee);

        if (paid) {
            registerPayment(saved.getId());
        }

        feeRepository.flush();
    }

    private final void registerPayment(final Long fee) {
        final TransactionEntity toSave;
        final TransactionEntity saved;
        final FeePaymentEntity  payment;
        final Long              index;

        index = transactionRepository.count() + 1;
        toSave = TransactionEntities.index(index);
        saved = transactionRepository.save(toSave);

        payment = FeePaymentEntity.builder()
            .feeId(fee)
            .transactionId(saved.getId())
            .build();
        feePaymentRepository.save(payment);
    }

}
