
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookChangeTitle;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.GameBookCreation;

public final class GameBookCreations {

    public static final GameBookCreation minimal() {
        final BookChangeTitle title;

        title = new BookChangeTitle(BookConstants.TITLE, null, null);
        return new GameBookCreation(BookConstants.ISBN_10, title, BookConstants.LANGUAGE);
    }

}
