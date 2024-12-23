
package com.bernardomg.association.library.publisher.domain.model;

import org.apache.commons.lang3.StringUtils;

public record Publisher(Long number, String name) {

    public Publisher(final Long number, final String name) {
        this.number = number;
        this.name = StringUtils.trim(name);
    }

}
