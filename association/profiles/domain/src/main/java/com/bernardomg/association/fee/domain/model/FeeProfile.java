
package com.bernardomg.association.fee.domain.model;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public record FeeProfile(Optional<String> identifier, Long number, Name name) {

    public FeeProfile(final Optional<String> identifier, final Long number, final Name name) {
        Objects.requireNonNull(identifier);

        this.identifier = handleEmpty(identifier);
        this.number = Objects.requireNonNull(number);
        this.name = Objects.requireNonNull(name);
    }

    public record Name(String firstName, String lastName) {

        public Name(final String firstName, final String lastName) {
            Objects.requireNonNull(firstName);
            Objects.requireNonNull(lastName);

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
