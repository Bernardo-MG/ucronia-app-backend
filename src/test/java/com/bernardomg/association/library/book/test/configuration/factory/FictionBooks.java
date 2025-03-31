
package com.bernardomg.association.library.book.test.configuration.factory;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;

public final class FictionBooks {

    public static final FictionBook donationNoDate() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(null, List.of(Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook donationNoDonors() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of());
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook duplicatedAuthor() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid(), Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook duplicatedDonor() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid(), Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook duplicatedPublisher() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid(), Publishers.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook emptyIsbn() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn("")
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook emptyTitle() {
        final Title title;

        title = new Title("", " ", "");
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withDonation(Optional.empty())
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook full() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook invalidIsbn() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.INVALID_ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook invalidLanguage() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage("abc")
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook isbn13() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_13)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook lent() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of(BookLendings.lent()))
            .withLent(true)
            .build();
    }

    public static final FictionBook lentHistory() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
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

    public static final FictionBook minimal() {
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withDonation(Optional.empty())
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook noRelationships() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of());
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of())
            .withPublishers(List.of())
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook padded() {
        final Title    title;
        final Donation donation;

        title = new Title(" " + BookConstants.SUPERTITLE + " ", " " + BookConstants.TITLE + " ",
            " " + BookConstants.SUBTITLE + " ");
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of())
            .withLent(false)
            .build();
    }

    public static final FictionBook returned() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
            .withDonation(Optional.of(donation))
            .withLendings(List.of(BookLendings.returned()))
            .withLent(false)
            .build();
    }

    public static final FictionBook returnedHistory() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return FictionBook.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(title)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withAuthors(List.of(Authors.valid()))
            .withPublishers(List.of(Publishers.valid()))
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
