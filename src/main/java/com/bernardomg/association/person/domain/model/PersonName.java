
package com.bernardomg.association.person.domain.model;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record PersonName(String firstName, String lastName) {

    public static final PersonName of(final String firstName, final String lastName) {
        return new PersonName(firstName, lastName);
    }

    public PersonName(final String firstName, final String lastName) {
        this.firstName = StringUtils.trim(firstName);
        this.lastName = StringUtils.trim(lastName);
    }

    public final String fullName() {
        return String.format("%s %s", firstName, lastName)
            .trim();
    }

}
