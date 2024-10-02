
package com.bernardomg.association.transaction.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonth;

public final class TransactionCalendarMonths {

    public static final TransactionCalendarMonth empty() {
        return new TransactionCalendarMonth(TransactionConstants.MONTH, List.of());
    }

    public static final TransactionCalendarMonth single() {
        return new TransactionCalendarMonth(TransactionConstants.MONTH, List.of(Transactions.valid()));
    }

    private TransactionCalendarMonths() {
        super();
    }

}
