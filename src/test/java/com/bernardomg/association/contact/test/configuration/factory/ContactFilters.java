
package com.bernardomg.association.contact.test.configuration.factory;

import com.bernardomg.association.contact.domain.filter.ContactFilter;

public final class ContactFilters {

    public static final ContactFilter empty() {
        return new ContactFilter("");
    }

    private ContactFilters() {
        super();
    }

}
