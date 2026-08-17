
package com.bernardomg.association.transaction.test.configuration.factory;

import com.bernardomg.association.transaction.domain.model.TransactionType;

public final class TransactionTypes {

    public static final TransactionType alternative() {
        return new TransactionType(TransactionTypeConstants.NUMBER, TransactionTypeConstants.ALTERNATIVE_DESCRIPTION,
            TransactionTypeConstants.COLOR);
    }

    public static final TransactionType forNumber(final long number) {
        return new TransactionType(number, TransactionTypeConstants.DESCRIPTION + " " + number,
            TransactionTypeConstants.COLOR);
    }

    public static final TransactionType toCreate() {
        return new TransactionType(-1, TransactionTypeConstants.DESCRIPTION, TransactionTypeConstants.COLOR);
    }

    public static final TransactionType valid() {
        return new TransactionType(TransactionTypeConstants.NUMBER, TransactionTypeConstants.DESCRIPTION,
            TransactionTypeConstants.COLOR);
    }

    private TransactionTypes() {
        super();
    }

}
