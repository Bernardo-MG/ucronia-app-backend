
package com.bernardomg.association.membership.test.fee.util.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bernardomg.association.funds.test.transaction.util.model.TransactionEntities;
import com.bernardomg.association.funds.transaction.persistence.model.TransactionEntity;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.model.FeePaymentEntity;
import com.bernardomg.association.membership.fee.persistence.repository.FeePaymentRepository;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.test.fee.util.model.FeeEntities;

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

        toSave = TransactionEntities.valid();
        saved = transactionRepository.save(toSave);

        payment = FeePaymentEntity.builder()
            .feeId(fee)
            .transactionId(saved.getId())
            .build();
        feePaymentRepository.save(payment);
    }

}
