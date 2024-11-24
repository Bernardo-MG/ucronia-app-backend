
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.book.domain.model.Book.Donor;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;

public final class Donors {

    public static final Donor valid() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new Donor(PersonConstants.NUMBER, name);
    }

    private Donors() {
        super();
    }

}
