
package com.bernardomg.association.test.data.fee.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bernardomg.association.fee.infra.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.infra.inbound.jpa.model.FeePaymentEntity;
import com.bernardomg.association.fee.infra.inbound.jpa.repository.FeePaymentSpringRepository;
import com.bernardomg.association.fee.infra.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.test.config.factory.FeeEntities;
import com.bernardomg.association.transaction.infra.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.infra.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.test.config.factory.TransactionEntities;

@Component
public final class FeeInitializer {

    @Autowired
    private FeePaymentSpringRepository  feePaymentRepository;

    @Autowired
    private FeeSpringRepository         feeRepository;

    @Autowired
    private TransactionSpringRepository transactionRepository;

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
