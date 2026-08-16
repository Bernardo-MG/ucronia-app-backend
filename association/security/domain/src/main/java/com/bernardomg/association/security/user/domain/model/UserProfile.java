
package com.bernardomg.association.security.user.domain.model;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.profile.domain.model.Name;

public record UserProfile(Optional<String> identifier, Long number, Name name) {

    public UserProfile(final Optional<String> identifier, final Long number, final Name name) {
        Objects.requireNonNull(identifier, "Identifier can't be null");
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(name, "Name can't be null");

        this.identifier = handleEmpty(identifier);
        this.number = Objects.requireNonNull(number);
        this.name = Objects.requireNonNull(name);
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
