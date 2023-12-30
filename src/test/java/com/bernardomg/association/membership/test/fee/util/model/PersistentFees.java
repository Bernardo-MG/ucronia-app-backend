
package com.bernardomg.association.membership.test.fee.util.model;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.membership.fee.persistence.model.PersistentFee;

public final class PersistentFees {

    public static PersistentFee notPaidAt(final Month month) {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(YearMonth.of(Fees.YEAR, month))
            .paid(false)
            .build();
    }

    public static PersistentFee paid() {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.DATE)
            .paid(true)
            .build();
    }

    public static PersistentFee paidAt(final Month month) {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(YearMonth.of(Fees.YEAR, month))
            .paid(true)
            .build();
    }

    public static PersistentFee paidNextDate() {
        return PersistentFee.builder()
            .id(2L)
            .memberId(1L)
            .date(Fees.NEXT_DATE)
            .paid(true)
            .build();
    }

    private PersistentFees() {
        super();
    }

}
