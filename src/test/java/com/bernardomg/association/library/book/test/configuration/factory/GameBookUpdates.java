
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

        title = new BookChangeTitle(BookConstants.TITLE, null, null);
        return new GameBookUpdate(BookConstants.ISBN_10, title, BookConstants.LANGUAGE, List.of(),
            new Donation(null, null), null, List.of(), new BookType(null), new GameSystem(null));
    }

}
