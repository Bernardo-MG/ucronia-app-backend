
package com.bernardomg.association.library.test.config.factory;

import com.bernardomg.association.library.domain.model.Book;

public final class Books {

    public static final Book valid() {
        return Book.builder()
            .withTitle(BookConstants.TITLE)
            .build();
    }

}
