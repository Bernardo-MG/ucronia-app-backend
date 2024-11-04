
package com.bernardomg.association.library.book.test.configuration.factory;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystems;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;
import com.bernardomg.association.person.test.configuration.factory.Persons;

public final class Books {

    public static final Book duplicatedAuthor() {
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
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
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
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
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
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
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
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
        final Title title;

        title = new Title("", " ", "");
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
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
        final Title title;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
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
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of(BookLendings.lent()))
            .withLent(true)
            .build();
    }

    public static final Book fullReturned() {
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of(BookLendings.returned()))
            .withLent(false)
            .build();
    }

    public static final Book invalidIsbn() {
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
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
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
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

    public static final Book isbn13() {
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_13)
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

    public static final Book lentHistory() {
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of(
                new BookLending(BookConstants.NUMBER, Persons.noMembership(), LocalDate.of(2020, Month.JANUARY, 1),
                    LocalDate.of(2020, Month.JANUARY, 2)),
                new BookLending(BookConstants.NUMBER, Persons.noMembership(), LocalDate.of(2020, Month.JANUARY, 4),
                    LocalDate.of(2020, Month.FEBRUARY, 3)),
                new BookLending(BookConstants.NUMBER, Persons.alternative(), LocalDate.of(2020, Month.FEBRUARY, 12),
                    LocalDate.of(2020, Month.FEBRUARY, 13)),
                new BookLending(BookConstants.NUMBER, Persons.noMembership(), LocalDate.of(2020, Month.MAY, 4),
                    LocalDate.of(2020, Month.MAY, 6)),
                new BookLending(BookConstants.NUMBER, Persons.noMembership(), LocalDate.of(2020, Month.MAY, 10), null)))
            .withLent(true)
            .build();
    }

    public static final Book minimal() {
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
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
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return Book.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonors(List.of(Donors.valid()))
            .withLendings(List.of(
                new BookLending(BookConstants.NUMBER, Persons.noMembership(), LocalDate.of(2020, Month.JANUARY, 1),
                    LocalDate.of(2020, Month.JANUARY, 2)),
                new BookLending(BookConstants.NUMBER, Persons.noMembership(), LocalDate.of(2020, Month.JANUARY, 4),
                    LocalDate.of(2020, Month.FEBRUARY, 3)),
                new BookLending(BookConstants.NUMBER, Persons.alternative(), LocalDate.of(2020, Month.FEBRUARY, 12),
                    LocalDate.of(2020, Month.FEBRUARY, 13)),
                new BookLending(BookConstants.NUMBER, Persons.noMembership(), LocalDate.of(2020, Month.MAY, 4),
                    LocalDate.of(2020, Month.MAY, 6)),
                new BookLending(BookConstants.NUMBER, Persons.noMembership(), LocalDate.of(2020, Month.MAY, 10),
                    LocalDate.of(2020, Month.MAY, 12))))
            .withLent(false)
            .build();
    }

}
