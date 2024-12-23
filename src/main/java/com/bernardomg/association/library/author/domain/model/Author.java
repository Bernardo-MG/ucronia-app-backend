
package com.bernardomg.association.library.author.domain.model;

import org.apache.commons.lang3.StringUtils;

public record Author(Long number, String name) {

    public Author(final Long number, final String name) {
        this.number = number;
        this.name = StringUtils.trim(name);
    }

}
