
package com.bernardomg.association.security.user.domain.model;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public record UserProfile(Optional<String> identifier, Long number, Name name) {

    public UserProfile(final Optional<String> identifier, final Long number, final Name name) {
        Objects.requireNonNull(identifier, "Identifier can't be null");
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(name, "Name can't be null");

        this.identifier = handleEmpty(identifier);
        this.number = Objects.requireNonNull(number);
        this.name = Objects.requireNonNull(name);
    }

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

    private static final Optional<String> handleEmpty(final Optional<String> value) {
        final Optional<String> trimmed;
        final Optional<String> result;

        trimmed = value.map(StringUtils::trim);
        if (trimmed.orElse("")
            .isEmpty()) {
            result = Optional.empty();
        } else {
            result = value.map(StringUtils::trim);
        }

        return result;
    }

}
