
package com.bernardomg.association.member.domain.model;

import java.util.Objects;

public record FeeMember(Long number, Name name) {

    public FeeMember(final Long number, final Name name) {
        this.number = Objects.requireNonNull(number);
        this.name = Objects.requireNonNull(name);
    }

}
