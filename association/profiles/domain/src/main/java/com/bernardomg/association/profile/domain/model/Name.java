
package com.bernardomg.association.profile.domain.model;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public record Name(String firstName, String lastName, Optional<String> nickname) {

    public Name(final String firstName, final String lastName, final Optional<String> nickname) {
        Objects.requireNonNull(firstName, "First name can't be null");
        Objects.requireNonNull(lastName, "Last name can't be null");
        Objects.requireNonNull(nickname, "Nickname can't be null");

        this.firstName = StringUtils.trim(firstName);
        this.lastName = StringUtils.trim(lastName);

        this.nickname = handleEmpty(nickname);
    }

    public String fullName() {
        return nickname.map(value -> String.format("%s \"%s\" %s", firstName, value, lastName))
            .orElseGet(() -> String.format("%s %s", firstName, lastName));
    }

    private final static Optional<String> handleEmpty(final Optional<String> value) {
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
