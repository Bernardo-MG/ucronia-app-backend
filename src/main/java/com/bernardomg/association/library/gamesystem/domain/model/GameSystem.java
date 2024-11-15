
package com.bernardomg.association.library.gamesystem.domain.model;

import org.apache.commons.lang3.StringUtils;

public record GameSystem(Long number, String name) {

    public GameSystem(final Long number, final String name) {
        this.number = number;
        this.name = StringUtils.trim(name);
    }

}
