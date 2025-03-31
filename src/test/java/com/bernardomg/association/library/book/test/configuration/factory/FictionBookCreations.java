
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookChangeTitle;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.FictionBookCreation;

public final class FictionBookCreations {

    public static final FictionBookCreation minimal() {
        final BookChangeTitle title;

        title = BookChangeTitle.builder()
            .withTitle(BookConstants.TITLE)
            .build();
        return FictionBookCreation.builder()
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .build();
    }

}
