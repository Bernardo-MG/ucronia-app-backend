
package com.bernardomg.association.contact.test.configuration.factory;

import com.bernardomg.association.contact.domain.filter.ContactQuery;

public final class ContactQueries {

    public static final ContactQuery empty() {
        return new ContactQuery("");
    }

    private ContactQueries() {
        super();
    }

}
