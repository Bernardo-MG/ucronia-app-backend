
package com.bernardomg.association.membership.test.fee.util.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bernardomg.association.membership.fee.persistence.model.PersistentFee;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.test.fee.util.model.PersistentFees;

@Component
public final class FeeInitializer {

    @Autowired
    private FeeRepository feeRepository;

    public final void registerFeeCurrentMonth(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFees.currentMonth(paid);

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeeCurrentMonthAlternative(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFees.currentMonthAlternative(paid);

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeeNextMonth(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFees.nextMonth(paid);

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeeNextYear(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFees.nextYear(paid);

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeePreviousMonth(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFees.previousMonth(paid);

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeePreviousYear(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFees.previousYear(paid);

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeeTwoMonthsBack(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFees.twoMonthsBack(paid);

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeeTwoYearsBack(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFees.twoYearsBack(paid);

        feeRepository.save(fee);
        feeRepository.flush();
    }

}
