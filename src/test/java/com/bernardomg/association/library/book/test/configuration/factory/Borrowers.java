
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.lending.domain.model.BookLending.Borrower;
import com.bernardomg.association.person.domain.model.ContactName;
import com.bernardomg.association.person.test.configuration.factory.ContactConstants;

public final class Borrowers {

    public static final Borrower alternative() {
        final ContactName name;

        name = new ContactName(ContactConstants.ALTERNATIVE_FIRST_NAME, ContactConstants.ALTERNATIVE_LAST_NAME);
        return new Borrower(ContactConstants.ALTERNATIVE_NUMBER, name);
    }

    public static final Borrower valid() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Borrower(ContactConstants.NUMBER, name);
    }

    private Borrowers() {
        super();
    }

}
