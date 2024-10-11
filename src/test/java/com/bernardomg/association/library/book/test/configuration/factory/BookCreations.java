
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookCreation;

public final class BookCreations {

    public static final BookCreation minimal() {
        return BookCreation.builder()
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .build();
    }

}
