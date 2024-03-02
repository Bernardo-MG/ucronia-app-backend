
package com.bernardomg.association.library.test.config.factory;

import com.bernardomg.association.library.domain.model.BookType;

public final class BookTypes {

    public static final BookType valid() {
        return BookType.builder()
            .withName(BookTypeConstants.NAME)
            .build();
    }

}
