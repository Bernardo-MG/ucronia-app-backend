
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookChangeTitle;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.FictionBookCreation;

public final class FictionBookCreations {

    public static final FictionBookCreation minimal() {
        final BookChangeTitle title;

        title = new BookChangeTitle(BookConstants.TITLE, null, null);
        return new FictionBookCreation(BookConstants.ISBN_10, title, BookConstants.LANGUAGE);
    }

}
