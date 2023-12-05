
package com.bernardomg.association.membership.test.fee.util.initializer;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;

@Component
public final class FeeInitializer {

    public static final YearMonth CURRENT_MONTH   = YearMonth.now();

    public static final YearMonth NEXT_MONTH      = YearMonth.now()
        .plusMonths(1);

    public static final YearMonth PREVIOUS_MONTH  = YearMonth.now()
        .minusMonths(1);

    public static final YearMonth TWO_MONTHS_BACK = YearMonth.now()
        .minusMonths(2);

    @Autowired
    private FeeRepository         feeRepository;

    public final void registerFeeCurrentMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = FeeEntity.builder()
            .paid(paid)
            .memberId(1L)
            .date(CURRENT_MONTH)
            .build();

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeeCurrentMonthAlternative(final Boolean paid) {
        final FeeEntity fee;

        fee = FeeEntity.builder()
            .paid(paid)
            .memberId(2L)
            .date(CURRENT_MONTH)
            .build();

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeeNextMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = FeeEntity.builder()
            .paid(paid)
            .memberId(1L)
            .date(NEXT_MONTH)
            .build();

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeePreviousMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = FeeEntity.builder()
            .paid(paid)
            .memberId(1L)
            .date(PREVIOUS_MONTH)
            .build();

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeeTwoMonthsBack(final Boolean paid) {
        final FeeEntity fee;

        fee = FeeEntity.builder()
            .paid(paid)
            .memberId(1L)
            .date(TWO_MONTHS_BACK)
            .build();

        feeRepository.save(fee);
        feeRepository.flush();
    }

}
