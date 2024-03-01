
package com.bernardomg.association.library.test.config.factory;

import com.bernardomg.association.library.adapter.inbound.jpa.model.BookEntity;

public final class BookEntities {

    public static final BookEntity valid() {
        return BookEntity.builder()
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .build();
    }

}
