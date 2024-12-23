
package com.bernardomg.association.library.booktype.domain.model;

import org.apache.commons.lang3.StringUtils;

public record BookType(Long number, String name) {

    public BookType(final Long number, final String name) {
        this.number = number;
        this.name = StringUtils.trim(name);
    }

}
