
package com.bernardomg.association.fee.domain.model;

import java.util.Objects;

import com.bernardomg.association.profile.domain.model.Name;

public record FeeMember(Long number, Name name) {

    public FeeMember(final Long number, final Name name) {
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(name, "Name can't be null");

        this.number = number;
        this.name = name;
    }

}
