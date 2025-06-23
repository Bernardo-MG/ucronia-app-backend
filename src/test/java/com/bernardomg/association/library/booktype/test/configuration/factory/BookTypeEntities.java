
package com.bernardomg.association.library.booktype.test.configuration.factory;

import com.bernardomg.association.library.booktype.adapter.inbound.jpa.model.BookTypeEntity;

public final class BookTypeEntities {

    public static final BookTypeEntity valid() {
        final BookTypeEntity entity = new BookTypeEntity();
        entity.setNumber(BookTypeConstants.NUMBER);
        entity.setName(BookTypeConstants.NAME);
        return entity;
    }

    private BookTypeEntities() {
        super();
    }

}
