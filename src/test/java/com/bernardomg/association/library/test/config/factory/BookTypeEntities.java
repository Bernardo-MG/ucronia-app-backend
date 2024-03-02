
package com.bernardomg.association.library.test.config.factory;

import com.bernardomg.association.library.adapter.inbound.jpa.model.BookTypeEntity;

public final class BookTypeEntities {

    public static final BookTypeEntity valid() {
        return BookTypeEntity.builder()
            .withName(BookTypeConstants.NAME)
            .build();
    }

}
