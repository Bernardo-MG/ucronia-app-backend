
package com.bernardomg.association.member.domain.model;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Value;

/**
 * TODO: Try to make this immutable.
 */
@Value
@Builder(setterPrefix = "with")
public final class Guest {

    private final String name;

    @Builder.Default
    private final long   number = -1L;

    public Guest(final String name, final long number) {
        super();

        this.name = StringUtils.trim(name);
        this.number = number;
    }

}
