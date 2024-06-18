
package com.bernardomg.association.library.test.config.factory;

import java.util.List;

import com.bernardomg.association.inventory.test.config.factory.Donors;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.domain.model.Publisher;

public final class Books {

    public static final Book duplicatedAuthor() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid(), Authors.valid()))
            .withPublisher(Publishers.valid())
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .withDonors(List.of(Donors.valid()))
            .build();
    }

    public static final Book emptyIsbn() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn("")
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublisher(Publishers.valid())
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .withDonors(List.of(Donors.valid()))
            .build();
    }

    public static final Book emptyTitle() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(" ")
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withPublisher(Publisher.builder()
                .build())
            .withGameSystem(GameSystem.builder()
                .build())
            .withBookType(BookType.builder()
                .build())
            .withDonors(List.of())
            .build();
    }

    public static final Book full() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublisher(Publishers.valid())
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .withDonors(List.of(Donors.valid()))
            .build();
    }

    public static final Book invalidLanguage() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage("abc")
            .withAuthors(List.of(Authors.valid()))
            .withPublisher(Publishers.valid())
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .withDonors(List.of(Donors.valid()))
            .build();
    }

    public static final Book minimal() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withPublisher(Publisher.builder()
                .build())
            .withGameSystem(GameSystem.builder()
                .build())
            .withBookType(BookType.builder()
                .build())
            .withDonors(List.of())
            .build();
    }

}
