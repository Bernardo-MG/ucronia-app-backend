
package com.bernardomg.association.library.book.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookChangeTitle;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.GameBookUpdate;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.GameBookUpdate.BookType;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.GameBookUpdate.Donation;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.GameBookUpdate.GameSystem;

public final class GameBookUpdates {

    public static final GameBookUpdate minimal() {
        final BookChangeTitle title;

        title = BookChangeTitle.builder()
            .withTitle(BookConstants.TITLE)
            .build();
        return GameBookUpdate.builder()
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
