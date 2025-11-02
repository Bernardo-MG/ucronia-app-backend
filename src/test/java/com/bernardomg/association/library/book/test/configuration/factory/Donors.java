
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.person.domain.model.ContactName;
import com.bernardomg.association.person.test.configuration.factory.ContactConstants;

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
