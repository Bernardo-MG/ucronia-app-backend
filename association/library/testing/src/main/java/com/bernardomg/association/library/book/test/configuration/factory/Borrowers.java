
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.lending.domain.model.Borrower;
import com.bernardomg.association.library.lending.domain.model.Borrower.Name;

public final class Borrowers {

    public static final Borrower alternative() {
        final Name name;

        name = new Name(BorrowerConstants.ALTERNATIVE_FIRST_NAME, BorrowerConstants.ALTERNATIVE_LAST_NAME);
        return new Borrower(BorrowerConstants.ALTERNATIVE_NUMBER, name);
    }

    public static final Borrower valid() {
        final Name name;

        name = new Name(BorrowerConstants.FIRST_NAME, BorrowerConstants.LAST_NAME);
        return new Borrower(BorrowerConstants.NUMBER, name);
    }

    private Borrowers() {
        super();
    }

}
