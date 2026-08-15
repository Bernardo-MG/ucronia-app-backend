
package com.bernardomg.association.transaction.test.configuration.factory;

import com.bernardomg.association.transaction.domain.model.TransactionType;

public final class TransactionTypes {

    public static final TransactionType forNumber(final long number) {
        return new TransactionType(number, TransactionTypeConstants.DESCRIPTION + " " + number,
            TransactionTypeConstants.COLOR);
    }

    public static final TransactionType valid() {
        return new TransactionType(TransactionTypeConstants.NUMBER, TransactionTypeConstants.DESCRIPTION,
            TransactionTypeConstants.COLOR);
    }

    private TransactionTypes() {
        super();
    }

}
