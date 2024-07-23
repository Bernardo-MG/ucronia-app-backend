
package com.bernardomg.association.library.test.config.factory;

import java.util.List;

import com.bernardomg.association.library.adapter.inbound.jpa.model.BookEntity;

public final class BookEntities {

    public static final BookEntity noRelationships() {
        return BookEntity.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withDonors(List.of())
            .withPublishers(List.of())
            .build();
    }

}
