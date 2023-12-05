
package com.bernardomg.association.membership.test.fee.util.initializer;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;

@Component
public final class FeeInitializer {

    public static final YearMonth CURRENT_MONTH   = YearMonth.now();

    public static final YearMonth PREVIOUS_MONTH  = YearMonth.now()
        .minusMonths(1);

    public static final YearMonth TWO_MONTHS_BACK = YearMonth.now()
        .minusMonths(2);

    @Autowired
    private FeeRepository         feeRepository;

    public final void registerFeeCurrentMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        fee.setDate(CURRENT_MONTH);

        feeRepository.save(fee);
    }

    public final void registerFeePreviousMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        fee.setDate(PREVIOUS_MONTH);

        feeRepository.save(fee);
    }

    public final void registerFeeTwoMonthsBack(final Boolean paid) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        fee.setDate(TWO_MONTHS_BACK);

        feeRepository.save(fee);
    }

}
