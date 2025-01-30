
package com.bernardomg.association.library.book.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookChangeTitle;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.FictionBookUpdate;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.FictionBookUpdate.Donation;

public final class FictionBookUpdates {

    public static final FictionBookUpdate minimal() {
        final BookChangeTitle title;

        title = BookChangeTitle.builder()
            .withTitle(BookConstants.TITLE)
            .build();
        return FictionBookUpdate.builder()
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withDonation(new Donation(null, null))
            .build();
    }

}
