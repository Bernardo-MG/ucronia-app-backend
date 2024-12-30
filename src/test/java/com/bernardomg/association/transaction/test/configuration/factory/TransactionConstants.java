
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

public final class TransactionConstants {

    public static final long      ALTERNATIVE_INDEX                = 20;

    public static final float     AMOUNT                           = 1F;

    public static final float     AMOUNT_BIGGER                    = 2F;

    public static final float     AMOUNT_DECIMAL                   = 1.2F;

    public static final float     AMOUNT_FEES                      = 4F;

    public static final LocalDate DATE                             = LocalDate.of(2020, Month.FEBRUARY, 1);

    public static final String    DESCRIPTION                      = "Transaction";

    public static final String    DESCRIPTION_FEE_DECEMBER_JANUARY = "Cuota de Person 1 Last name 1 para Diciembre 2020, Enero 2021";

    public static final String    DESCRIPTION_FEE_FEBRUARY         = "Cuota de Person 1 Last name 1 para Febrero 2020";

    public static final String    DESCRIPTION_FEE_FEBRUARY_MARCH   = "Cuota de Person 1 Last name 1 para Febrero 2020, Marzo 2020";

    public static final long      ID                               = 1;

    public static final long      INDEX                            = 10;

    public static final long      INDEX_SMALL                      = 1;

    public static final YearMonth MONTH                            = YearMonth.now();

    public static final long      NEXT_INDEX                       = 11;

}
