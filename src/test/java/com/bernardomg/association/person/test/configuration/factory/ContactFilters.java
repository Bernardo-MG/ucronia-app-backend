
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.domain.filter.ContactFilter;

public final class ContactFilters {

    public static final ContactFilter empty() {
        return new ContactFilter(null, "");
    }

    private ContactFilters() {
        super();
    }

}
