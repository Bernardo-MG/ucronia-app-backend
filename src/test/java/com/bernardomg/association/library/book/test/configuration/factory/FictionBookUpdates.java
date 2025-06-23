
package com.bernardomg.association.library.book.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookChangeTitle;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.FictionBookUpdate;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.FictionBookUpdate.Donation;

public final class FictionBookUpdates {

    public static final FictionBookUpdate minimal() {
        final BookChangeTitle title;

        title = new BookChangeTitle(BookConstants.TITLE, null, null);
        return new FictionBookUpdate(BookConstants.ISBN_10, title, BookConstants.LANGUAGE, List.of(),
            new Donation(null, null), null, List.of());
    }

}
