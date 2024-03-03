
package com.bernardomg.association.library.test.config.factory;

import java.util.List;

import com.bernardomg.association.library.adapter.inbound.jpa.model.BookEntity;

public final class BookEntities {

    public static final BookEntity valid() {
        return BookEntity.builder()
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .build();
    }

}