
package com.bernardomg.association.library.test.config.factory;

import java.util.List;

import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.model.GameSystem;

public final class Books {

    public static final Book emptyIsbn() {
        return Book.builder()
            .withTitle(BookConstants.TITLE)
            .withIsbn("")
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .build();
    }

    public static final Book emptyTitle() {
        return Book.builder()
            .withTitle(" ")
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withGameSystem(GameSystem.builder()
                .build())
            .withBookType(BookType.builder()
                .build())
            .build();
    }

    public static final Book full() {
        return Book.builder()
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .build();
    }

    public static final Book minimal() {
        return Book.builder()
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .build();
    }

    public static final Book noRelationships() {
        return Book.builder()
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withGameSystem(GameSystem.builder()
                .build())
            .withBookType(BookType.builder()
                .build())
            .build();
    }

}
