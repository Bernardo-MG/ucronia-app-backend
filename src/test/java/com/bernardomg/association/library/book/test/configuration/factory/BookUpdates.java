
package com.bernardomg.association.library.book.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookUpdate;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookUpdate.BookType;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookUpdate.GameSystem;

public final class BookUpdates {

    public static final BookUpdate minimal() {
        return BookUpdate.builder()
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withGameSystem(new GameSystem(null))
            .withBookType(new BookType(null))
            .withDonors(List.of())
            .build();
    }

}
