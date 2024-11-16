
package com.bernardomg.association.library.book.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookChangeTitle;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookUpdate;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookUpdate.BookType;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookUpdate.Donation;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookUpdate.GameSystem;

public final class BookUpdates {

    public static final BookUpdate minimal() {
        final BookChangeTitle title;

        title = BookChangeTitle.builder()
            .withTitle(BookConstants.TITLE)
            .build();
        return BookUpdate.builder()
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withGameSystem(new GameSystem(null))
            .withBookType(new BookType(null))
            .withDonation(new Donation(null, null))
            .build();
    }

}
