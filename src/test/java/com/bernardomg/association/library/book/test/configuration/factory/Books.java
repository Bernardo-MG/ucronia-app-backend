
package com.bernardomg.association.library.book.test.configuration.factory;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import com.bernardomg.association.inventory.test.configuration.factory.Donors;
import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystems;
import com.bernardomg.association.library.lending.domain.model.BookBookLending;
import com.bernardomg.association.library.lending.test.configuration.factory.BookBookLendings;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;
import com.bernardomg.association.person.test.configuration.factory.Persons;

public final class Books {

    public static final Book duplicatedAuthor() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid(), Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final Book duplicatedDonor() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid(), Donors.valid()))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final Book duplicatedPublisher() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid(), Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of())
            .withLent(false)
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
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final Book emptyTitle() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(" ")
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withGameSystem(Optional.empty())
            .withBookType(Optional.empty())
            .withDonors(List.of())
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final Book full() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final Book fullLent() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of(BookBookLendings.lent()))
            .withLent(true)
            .build();
    }

    public static final Book fullReturned() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of(BookBookLendings.returned()))
            .withLent(false)
            .build();
    }

    public static final Book invalidIsbn() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.INVALID_ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final Book invalidLanguage() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage("abc")
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final Book lentHistory() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of(
                new BookBookLending(Persons.valid(), LocalDate.of(2020, Month.JANUARY, 1),
                    LocalDate.of(2020, Month.JANUARY, 2)),
                new BookBookLending(Persons.valid(), LocalDate.of(2020, Month.JANUARY, 4),
                    LocalDate.of(2020, Month.FEBRUARY, 3)),
                new BookBookLending(Persons.alternative(), LocalDate.of(2020, Month.FEBRUARY, 12),
                    LocalDate.of(2020, Month.FEBRUARY, 13)),
                new BookBookLending(Persons.valid(), LocalDate.of(2020, Month.MAY, 4),
                    LocalDate.of(2020, Month.MAY, 6)),
                new BookBookLending(Persons.valid(), LocalDate.of(2020, Month.MAY, 10), null)))
            .withLent(true)
            .build();
    }

    public static final Book minimal() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withGameSystem(Optional.empty())
            .withBookType(Optional.empty())
            .withDonors(List.of())
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final Book returnedHistory() {
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of(
                new BookBookLending(Persons.valid(), LocalDate.of(2020, Month.JANUARY, 1),
                    LocalDate.of(2020, Month.JANUARY, 2)),
                new BookBookLending(Persons.valid(), LocalDate.of(2020, Month.JANUARY, 4),
                    LocalDate.of(2020, Month.FEBRUARY, 3)),
                new BookBookLending(Persons.alternative(), LocalDate.of(2020, Month.FEBRUARY, 12),
                    LocalDate.of(2020, Month.FEBRUARY, 13)),
                new BookBookLending(Persons.valid(), LocalDate.of(2020, Month.MAY, 4),
                    LocalDate.of(2020, Month.MAY, 6)),
                new BookBookLending(Persons.valid(), LocalDate.of(2020, Month.MAY, 10),
                    LocalDate.of(2020, Month.MAY, 12))))
            .withLent(false)
            .build();
    }

}
