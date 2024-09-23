
package com.bernardomg.association.transaction.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonth;

public final class TransactionCalendarMonths {

    public static final TransactionCalendarMonth empty() {
        return TransactionCalendarMonth.builder()
            .withDate(TransactionConstants.MONTH)
            .withTransactions(List.of())
            .build();
    }

    public static final TransactionCalendarMonth single() {
        return TransactionCalendarMonth.builder()
            .withDate(TransactionConstants.MONTH)
            .withTransactions(List.of(Transactions.valid()))
            .build();
    }

    private TransactionCalendarMonths() {
        super();
    }

}
