
package com.bernardomg.association.membership.test.fee.util.initializer;

import java.time.Year;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bernardomg.association.membership.fee.persistence.model.PersistentFee;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;

@Component
public final class FeeInitializer {

    public static final YearMonth CURRENT_MONTH       = YearMonth.now();

    public static final Year      CURRENT_YEAR        = Year.now();

    public static final YearMonth NEXT_MONTH          = YearMonth.now()
        .plusMonths(1);

    public static final Year      NEXT_YEAR           = Year.now()
        .plusYears(1);

    public static final YearMonth NEXT_YEAR_MONTH     = YearMonth.now()
        .plusYears(1);

    public static final YearMonth PREVIOUS_MONTH      = YearMonth.now()
        .minusMonths(1);

    public static final Year      PREVIOUS_YEAR       = Year.now()
        .minusYears(1);

    public static final YearMonth PREVIOUS_YEAR_MONTH = YearMonth.now()
        .minusYears(1);

    public static final YearMonth TWO_MONTHS_BACK     = YearMonth.now()
        .minusMonths(2);

    public static final YearMonth TWO_YEARS_BACK      = YearMonth.now()
        .minusYears(2);

    @Autowired
    private FeeRepository         feeRepository;

    public final void registerFeeCurrentMonth(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFee.builder()
            .paid(paid)
            .memberId(1L)
            .date(CURRENT_MONTH)
            .build();

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeeCurrentMonthAlternative(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFee.builder()
            .paid(paid)
            .memberId(2L)
            .date(CURRENT_MONTH)
            .build();

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeeNextMonth(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFee.builder()
            .paid(paid)
            .memberId(1L)
            .date(NEXT_MONTH)
            .build();

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeeNextYear(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFee.builder()
            .paid(paid)
            .memberId(1L)
            .date(NEXT_YEAR_MONTH)
            .build();

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeePreviousMonth(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFee.builder()
            .paid(paid)
            .memberId(1L)
            .date(PREVIOUS_MONTH)
            .build();

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeePreviousYear(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFee.builder()
            .paid(paid)
            .memberId(1L)
            .date(PREVIOUS_YEAR_MONTH)
            .build();

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeeTwoMonthsBack(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFee.builder()
            .paid(paid)
            .memberId(1L)
            .date(TWO_MONTHS_BACK)
            .build();

        feeRepository.save(fee);
        feeRepository.flush();
    }

    public final void registerFeeTwoYearsBack(final Boolean paid) {
        final PersistentFee fee;

        fee = PersistentFee.builder()
            .paid(paid)
            .memberId(1L)
            .date(TWO_YEARS_BACK)
            .build();

        feeRepository.save(fee);
        feeRepository.flush();
    }

}
