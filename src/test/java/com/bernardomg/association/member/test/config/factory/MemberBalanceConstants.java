
package com.bernardomg.association.member.test.config.factory;

import java.time.Month;
import java.time.YearMonth;

public final class MemberBalanceConstants {

    public static final YearMonth CURRENT_MONTH   = YearMonth.now();

    public static final YearMonth NEXT_MONTH      = YearMonth.now()
        .plusMonths(1);

    public static final YearMonth PREVIOUS_MONTH  = YearMonth.now()
        .minusMonths(1);

    public static final YearMonth START_MONTH     = YearMonth.of(2020, Month.JANUARY);

    public static final YearMonth TWO_MONTHS_BACK = YearMonth.now()
        .minusMonths(2);

    private MemberBalanceConstants() {
        super();
    }

}
