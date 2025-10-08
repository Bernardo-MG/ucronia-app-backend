
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.domain.filter.PersonFilter;

public final class PersonFilters {

    public static final PersonFilter empty() {
        return new PersonFilter(null, "");
    }

    private PersonFilters() {
        super();
    }

}
