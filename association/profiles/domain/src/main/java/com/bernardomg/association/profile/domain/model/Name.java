
package com.bernardomg.association.profile.domain.model;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public record Name(String firstName, String lastName) {

    public Name(final String firstName, final String lastName) {
        Objects.requireNonNull(firstName, "First name can't be null");
        Objects.requireNonNull(lastName, "Last name can't be null");

        this.firstName = StringUtils.trim(firstName);
        this.lastName = StringUtils.trim(lastName);
    }

    public final String fullName() {
        return String.format("%s %s", firstName, lastName)
            .trim();
    }

}
