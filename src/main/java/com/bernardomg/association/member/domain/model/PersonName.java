
package com.bernardomg.association.member.domain.model;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class PersonName {

    public static final PersonName of(final String firstName, final String lastName) {
        return new PersonName(firstName, lastName);
    }

    private String firstName;

    private String lastName;

    public PersonName(final String firstName, final String lastName) {
        super();

        this.firstName = StringUtils.trim(firstName);
        this.lastName = StringUtils.trim(lastName);
    }

    public final String getFullName() {
        return String.format("%s %s", firstName, lastName)
            .trim();
    }

}
