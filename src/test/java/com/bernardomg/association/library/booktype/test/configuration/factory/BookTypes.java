
package com.bernardomg.association.library.booktype.test.configuration.factory;

import com.bernardomg.association.library.booktype.domain.model.BookType;

public final class BookTypes {

    public static final BookType emptyName() {
        return new BookType(" ");
    }

    public static final BookType valid() {
        return new BookType(BookTypeConstants.NAME);
    }

}
