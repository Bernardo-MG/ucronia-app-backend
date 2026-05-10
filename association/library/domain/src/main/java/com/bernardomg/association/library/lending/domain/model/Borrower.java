
package com.bernardomg.association.library.lending.domain.model;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public record Borrower(long number, Name name) {

    public Borrower(final long number, final Name name) {
        this.number = Objects.requireNonNull(number);
        this.name = Objects.requireNonNull(name);
    }

    public record Name(String firstName, String lastName) {

        public Name(final String firstName, final String lastName) {
            this.firstName = StringUtils.trim(firstName);
            this.lastName = StringUtils.trim(lastName);
        }

        public final String fullName() {
            return String.format("%s %s", firstName, lastName)
                .trim();
        }

    }
}
