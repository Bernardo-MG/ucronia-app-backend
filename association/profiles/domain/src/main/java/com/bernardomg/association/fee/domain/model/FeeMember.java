
package com.bernardomg.association.fee.domain.model;

import java.util.Objects;

import com.bernardomg.association.profile.domain.model.Name;

public record FeeMember(Long number, Name name) {

    public FeeMember(final Long number, final Name name) {
        this.number = Objects.requireNonNull(number);
        this.name = Objects.requireNonNull(name);
    }

}
