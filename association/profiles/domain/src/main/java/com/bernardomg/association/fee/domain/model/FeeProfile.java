
package com.bernardomg.association.fee.domain.model;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.profile.domain.model.Name;

public record FeeProfile(Optional<String> identifier, Long number, Name name) {

    public FeeProfile(final Optional<String> identifier, final Long number, final Name name) {
        Objects.requireNonNull(identifier, "Identifier can't be null");
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(name, "Name can't be null");

        this.identifier = handleEmpty(identifier);
        this.number = number;
        this.name = name;
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
