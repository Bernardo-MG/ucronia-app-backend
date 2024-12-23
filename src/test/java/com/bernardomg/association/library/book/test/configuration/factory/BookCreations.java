
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookChangeTitle;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookCreation;

public final class BookCreations {

    public static final BookCreation minimal() {
        final BookChangeTitle title;

        title = BookChangeTitle.builder()
            .withTitle(BookConstants.TITLE)
            .build();
        return BookCreation.builder()
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .build();
    }

}
