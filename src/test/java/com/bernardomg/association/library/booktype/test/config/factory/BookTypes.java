
package com.bernardomg.association.library.booktype.test.config.factory;

import com.bernardomg.association.library.booktype.domain.model.BookType;

public final class BookTypes {

    public static final BookType emptyName() {
        return BookType.builder()
            .withName(" ")
            .build();
    }

    public static final BookType valid() {
        return BookType.builder()
            .withName(BookTypeConstants.NAME)
            .build();
    }

}
