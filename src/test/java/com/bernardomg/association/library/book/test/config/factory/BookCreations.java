
package com.bernardomg.association.library.book.test.config.factory;

import java.util.List;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookCreation;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookCreationBookType;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookCreationGameSystem;

public final class BookCreations {

    public static final BookCreation minimal() {
        return BookCreation.builder()
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withGameSystem(BookCreationGameSystem.builder()
                .withName("")
                .build())
            .withBookType(BookCreationBookType.builder()
                .withName("")
                .build())
            .withDonors(List.of())
            .build();
    }

}
