
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookChangeTitle;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.GameBookCreation;

public final class GameBookCreations {

    public static final GameBookCreation minimal() {
        final BookChangeTitle title;

        title = BookChangeTitle.builder()
            .withTitle(BookConstants.TITLE)
            .build();
        return GameBookCreation.builder()
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .build();
    }

}
