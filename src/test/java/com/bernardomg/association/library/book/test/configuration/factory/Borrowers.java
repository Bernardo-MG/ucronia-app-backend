
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.lending.domain.model.BookLending.Borrower;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;

public final class Borrowers {

    public static final Borrower alternative() {
        final PersonName name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        return new Borrower(PersonConstants.ALTERNATIVE_NUMBER, name);
    }

    public static final Borrower valid() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new Borrower(PersonConstants.NUMBER, name);
    }

    private Borrowers() {
        super();
    }

}
