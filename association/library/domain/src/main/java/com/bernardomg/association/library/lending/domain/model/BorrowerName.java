
package com.bernardomg.association.library.lending.domain.model;

import org.apache.commons.lang3.StringUtils;

public record BorrowerName(String firstName, String lastName) {

    public BorrowerName(final String firstName, final String lastName) {
        this.firstName = StringUtils.trim(firstName);
        this.lastName = StringUtils.trim(lastName);
    }

    public final String fullName() {
        return String.format("%s %s", firstName, lastName)
            .trim();
    }

}
