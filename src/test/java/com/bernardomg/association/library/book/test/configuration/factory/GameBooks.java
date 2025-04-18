
package com.bernardomg.association.library.book.test.configuration.factory;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystems;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;

public final class GameBooks {

    public static final GameBook donationNoDate() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(null, List.of(Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook donationNoDonors() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of());
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook duplicatedAuthor() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid(), Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook duplicatedDonor() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid(), Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook duplicatedPublisher() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid(), Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook emptyIsbn() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn("")
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook emptyTitle() {
        final Title title;

        title = new Title("", " ", "");
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withGameSystem(Optional.empty())
            .withBookType(Optional.empty())
            .withDonation(Optional.empty())
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook full() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook invalidIsbn() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.INVALID_ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook invalidLanguage() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage("abc")
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook isbn13() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_13)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook lent() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of(BookLendings.lent()))
            .withLent(true)
            .build();
    }

    public static final GameBook lentHistory() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            // TODO: user lendings factory
            .withLendings(List.of(
                BookLendings.returned(LocalDate.of(2020, Month.JANUARY, 1), LocalDate.of(2020, Month.JANUARY, 2)),
                BookLendings.returned(LocalDate.of(2020, Month.JANUARY, 4), LocalDate.of(2020, Month.FEBRUARY, 3)),
                BookLendings.returnedAlternative(LocalDate.of(2020, Month.FEBRUARY, 12),
                    LocalDate.of(2020, Month.FEBRUARY, 13)),
                BookLendings.returned(LocalDate.of(2020, Month.MAY, 4), LocalDate.of(2020, Month.MAY, 6)),
                BookLendings.lent(LocalDate.of(2020, Month.MAY, 10))))
            .withLent(true)
            .build();
    }

    public static final GameBook minimal() {
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withGameSystem(Optional.empty())
            .withBookType(Optional.empty())
            .withDonation(Optional.empty())
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook noRelationships() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of());
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withGameSystem(Optional.empty())
            .withBookType(Optional.empty())
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook padded() {
        final Title    title;
        final Donation donation;

        title = new Title(" " + BookConstants.SUPERTITLE + " ", " " + BookConstants.TITLE + " ",
            " " + BookConstants.SUBTITLE + " ");
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final GameBook returned() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of(BookLendings.returned()))
            .withLent(false)
            .build();
    }

    public static final GameBook returnedHistory() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return GameBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withGameSystem(Optional.of(GameSystems.valid()))
            .withBookType(Optional.of(BookTypes.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of(
                BookLendings.returned(LocalDate.of(2020, Month.JANUARY, 1), LocalDate.of(2020, Month.JANUARY, 2)),
                BookLendings.returned(LocalDate.of(2020, Month.JANUARY, 4), LocalDate.of(2020, Month.FEBRUARY, 3)),
                BookLendings.returnedAlternative(LocalDate.of(2020, Month.FEBRUARY, 12),
                    LocalDate.of(2020, Month.FEBRUARY, 13)),
                BookLendings.returned(LocalDate.of(2020, Month.MAY, 4), LocalDate.of(2020, Month.MAY, 6)),
                BookLendings.returned(LocalDate.of(2020, Month.MAY, 10), LocalDate.of(2020, Month.MAY, 12))))
            .withLent(false)
            .build();
    }

}
