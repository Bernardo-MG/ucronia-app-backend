
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.library.lending.domain.model.BookLending.Borrower;

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
