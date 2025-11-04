
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.library.book.domain.model.Donor;

public final class Donors {

    public static final Donor valid() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Donor(ContactConstants.NUMBER, name);
    }

    private Donors() {
        super();
    }

}
