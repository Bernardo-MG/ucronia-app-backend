
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
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(), List.of(Publishers.valid()),
            Optional.of(donation));
    }

    public static final FictionBook donationNoDonors() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of());
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(), List.of(Publishers.valid()),
            Optional.of(donation));
    }

    public static final FictionBook duplicatedAuthor() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid(), Authors.valid()), List.of(),
            List.of(Publishers.valid()), Optional.of(donation));
    }

    public static final FictionBook duplicatedDonor() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid(), Donors.valid()));
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(), List.of(Publishers.valid()),
            Optional.of(donation));
    }

    public static final FictionBook duplicatedPublisher() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(),
            List.of(Publishers.valid(), Publishers.valid()), Optional.of(donation));
    }

    public static final FictionBook emptyIsbn() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new FictionBook(BookConstants.NUMBER, title, "", BookConstants.LANGUAGE, BookConstants.PUBLISH_DATE,
            false, List.of(Authors.valid()), List.of(), List.of(Publishers.valid()), Optional.of(donation));
    }

    public static final FictionBook emptyTitle() {
        final Title title;

        title = new Title("", " ", "");
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(), List.of(), List.of(), Optional.empty());
    }

    public static final FictionBook full() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(), List.of(Publishers.valid()),
            Optional.of(donation));
    }

    public static final FictionBook invalidIsbn() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.INVALID_ISBN, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(), List.of(Publishers.valid()),
            Optional.of(donation));
    }

    public static final FictionBook invalidLanguage() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, "abc", BookConstants.PUBLISH_DATE,
            false, List.of(Authors.valid()), List.of(), List.of(Publishers.valid()), Optional.of(donation));
    }

    public static final FictionBook isbn13() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_13, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(), List.of(Publishers.valid()),
            Optional.of(donation));
    }

    public static final FictionBook lent() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, true, List.of(Authors.valid()), List.of(BookLendings.lent()),
            List.of(Publishers.valid()), Optional.of(donation));
    }

    public static final FictionBook lentHistory() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, true, List.of(Authors.valid()),
            // TODO: user lendings factory
            List.of(BookLendings.returned(LocalDate.of(2020, Month.JANUARY, 1), LocalDate.of(2020, Month.JANUARY, 2)),
                BookLendings.returned(LocalDate.of(2020, Month.JANUARY, 4), LocalDate.of(2020, Month.FEBRUARY, 3)),
                BookLendings.returnedAlternative(LocalDate.of(2020, Month.FEBRUARY, 12),
                    LocalDate.of(2020, Month.FEBRUARY, 13)),
                BookLendings.returned(LocalDate.of(2020, Month.MAY, 4), LocalDate.of(2020, Month.MAY, 6)),
                BookLendings.lent(LocalDate.of(2020, Month.MAY, 10))),
            List.of(Publishers.valid()), Optional.of(donation));
    }

    public static final FictionBook minimal() {
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE, null, false,
            List.of(), List.of(), List.of(), Optional.empty());
    }

    public static final FictionBook noRelationships() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of());
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(), List.of(), List.of(), Optional.of(donation));
    }

    public static final FictionBook padded() {
        final Title    title;
        final Donation donation;

        title = new Title(" " + BookConstants.SUPERTITLE + " ", " " + BookConstants.TITLE + " ",
            " " + BookConstants.SUBTITLE + " ");
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(), List.of(Publishers.valid()),
            Optional.of(donation));
    }

    public static final FictionBook returned() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(BookLendings.returned()),
            List.of(Publishers.valid()), Optional.of(donation));
    }

    public static final FictionBook returnedHistory() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new FictionBook(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()),
            List.of(BookLendings.returned(LocalDate.of(2020, Month.JANUARY, 1), LocalDate.of(2020, Month.JANUARY, 2)),
                BookLendings.returned(LocalDate.of(2020, Month.JANUARY, 4), LocalDate.of(2020, Month.FEBRUARY, 3)),
                BookLendings.returnedAlternative(LocalDate.of(2020, Month.FEBRUARY, 12),
                    LocalDate.of(2020, Month.FEBRUARY, 13)),
                BookLendings.returned(LocalDate.of(2020, Month.MAY, 4), LocalDate.of(2020, Month.MAY, 6)),
                BookLendings.returned(LocalDate.of(2020, Month.MAY, 10), LocalDate.of(2020, Month.MAY, 12))),
            List.of(Publishers.valid()), Optional.of(donation));
    }

}
