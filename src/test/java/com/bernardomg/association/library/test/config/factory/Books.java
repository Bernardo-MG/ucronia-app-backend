
package com.bernardomg.association.library.test.config.factory;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import com.bernardomg.association.inventory.test.config.factory.Donors;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.model.BookBookLending;
import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.person.test.config.factory.Persons;

public final class Books {

    public static final Book duplicatedAuthor() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid(), Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of())
            .build();
    }

    public static final Book emptyIsbn() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn("")
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of())
            .build();
    }

    public static final Book emptyTitle() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(" ")
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withGameSystem(GameSystem.builder()
                .build())
            .withBookType(BookType.builder()
                .build())
            .withDonors(List.of())
            .withLendings(List.of())
            .build();
    }

    public static final Book full() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of())
            .build();
    }

    public static final Book invalidLanguage() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage("abc")
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of())
            .build();
    }

    public static final Book lent() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .withDonors(List.of(Donors.valid()))
            .withLent(true)
            .withLendings(List.of(BookBookLendings.lent()))
            .build();
    }

    public static final Book lentHistory() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .withDonors(List.of(Donors.valid()))
            .withLent(true)
            .withLendings(List.of(BookBookLending.builder()
                .withPerson(Persons.valid())
                .withLendingDate(LocalDate.of(2020, Month.JANUARY, 1))
                .withReturnDate(LocalDate.of(2020, Month.JANUARY, 2))
                .build(),
                BookBookLending.builder()
                    .withPerson(Persons.valid())
                    .withLendingDate(LocalDate.of(2020, Month.JANUARY, 4))
                    .withReturnDate(LocalDate.of(2020, Month.FEBRUARY, 3))
                    .build(),
                BookBookLending.builder()
                    .withPerson(Persons.alternative())
                    .withLendingDate(LocalDate.of(2020, Month.FEBRUARY, 12))
                    .withReturnDate(LocalDate.of(2020, Month.FEBRUARY, 13))
                    .build(),
                BookBookLending.builder()
                    .withPerson(Persons.valid())
                    .withLendingDate(LocalDate.of(2020, Month.MAY, 4))
                    .withReturnDate(LocalDate.of(2020, Month.MAY, 6))
                    .build(),
                BookBookLending.builder()
                    .withPerson(Persons.valid())
                    .withLendingDate(LocalDate.of(2020, Month.MAY, 10))
                    .build()))
            .build();
    }

    public static final Book minimal() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withGameSystem(GameSystem.builder()
                .build())
            .withBookType(BookType.builder()
                .build())
            .withDonors(List.of())
            .withLendings(List.of())
            .build();
    }

    public static final Book returned() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .withDonors(List.of(Donors.valid()))
            .withLent(false)
            .withLendings(List.of(BookBookLendings.returned()))
            .build();
    }

    public static final Book returnedHistory() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(GameSystems.valid())
            .withBookType(BookTypes.valid())
            .withDonors(List.of(Donors.valid()))
            .withLent(false)
            .withLendings(List.of(BookBookLending.builder()
                .withPerson(Persons.valid())
                .withLendingDate(LocalDate.of(2020, Month.JANUARY, 1))
                .withReturnDate(LocalDate.of(2020, Month.JANUARY, 2))
                .build(),
                BookBookLending.builder()
                    .withPerson(Persons.valid())
                    .withLendingDate(LocalDate.of(2020, Month.JANUARY, 4))
                    .withReturnDate(LocalDate.of(2020, Month.FEBRUARY, 3))
                    .build(),
                BookBookLending.builder()
                    .withPerson(Persons.alternative())
                    .withLendingDate(LocalDate.of(2020, Month.FEBRUARY, 12))
                    .withReturnDate(LocalDate.of(2020, Month.FEBRUARY, 13))
                    .build(),
                BookBookLending.builder()
                    .withPerson(Persons.valid())
                    .withLendingDate(LocalDate.of(2020, Month.MAY, 4))
                    .withReturnDate(LocalDate.of(2020, Month.MAY, 6))
                    .build(),
                BookBookLending.builder()
                    .withPerson(Persons.valid())
                    .withLendingDate(LocalDate.of(2020, Month.MAY, 10))
                    .withReturnDate(LocalDate.of(2020, Month.MAY, 12))
                    .build()))
            .build();
    }

}
