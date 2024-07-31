
package com.bernardomg.association.library.booktype.test.config.factory;

import com.bernardomg.association.library.booktype.adapter.inbound.jpa.model.BookTypeEntity;

public final class BookTypeEntities {

    public static final BookTypeEntity valid() {
        return BookTypeEntity.builder()
            .withName(BookTypeConstants.NAME)
            .build();
    }

}
