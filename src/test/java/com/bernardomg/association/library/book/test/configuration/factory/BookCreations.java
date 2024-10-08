
package com.bernardomg.association.library.book.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookCreation;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookCreation.BookType;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookCreation.GameSystem;

public final class BookCreations {

    public static final BookCreation minimal() {
        return BookCreation.builder()
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withGameSystem(new GameSystem(""))
            .withBookType(new BookType(""))
            .withDonors(List.of())
            .build();
    }

}
