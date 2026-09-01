
package com.bernardomg.association.calendar.game.domain.model;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public record GameTable(long number, String name, String description) {

    public GameTable(final long number, final String name, final String description) {
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(name, "Name can't be null");
        Objects.requireNonNull(description, "Description can't be null");

        this.number = number;
        this.name = StringUtils.trim(name);
        this.description = StringUtils.trim(description);
    }

}
