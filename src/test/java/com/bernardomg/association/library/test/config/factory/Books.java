
package com.bernardomg.association.library.test.config.factory;

import java.util.List;

import com.bernardomg.association.library.domain.model.Book;

public final class Books {

    public static final Book valid() {
        return Book.builder()
            .withTitle(BookConstants.TITLE)
            .withAuthors(List.of(Authors.valid()))
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .build();
    }

}
