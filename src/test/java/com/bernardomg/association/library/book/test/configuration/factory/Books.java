
package com.bernardomg.association.library.book.test.configuration.factory;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendingInfos;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;

public final class Books {

    public static final Book donationNoDate() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(null, List.of(Donors.valid()));
        return new Book(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(), List.of(Publishers.valid()),
            Optional.of(donation));
    }

    public static final Book donationNoDonors() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of());
        return new Book(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(), List.of(Publishers.valid()),
            Optional.of(donation));
    }

    public static final Book full() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new Book(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(), List.of(Publishers.valid()),
            Optional.of(donation));
    }

    public static final Book lent() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new Book(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, true, List.of(Authors.valid()), List.of(BookLendingInfos.lent()),
            List.of(Publishers.valid()), Optional.of(donation));
    }

    public static final Book lentHistory() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new Book(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, true, List.of(Authors.valid()),
            // TODO: user lendings factory
            List.of(BookLendingInfos.returned(LocalDate.of(2020, Month.JANUARY, 1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant(),
                LocalDate.of(2020, Month.JANUARY, 2)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant()),
                BookLendingInfos.returned(LocalDate.of(2020, Month.JANUARY, 4)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant(),
                    LocalDate.of(2020, Month.FEBRUARY, 3)
                        .atStartOfDay(ZoneOffset.UTC)
                        .toInstant()),
                BookLendingInfos.returnedAlternative(LocalDate.of(2020, Month.FEBRUARY, 12)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant(),
                    LocalDate.of(2020, Month.FEBRUARY, 13)
                        .atStartOfDay(ZoneOffset.UTC)
                        .toInstant()),
                BookLendingInfos.returned(LocalDate.of(2020, Month.MAY, 4)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant(),
                    LocalDate.of(2020, Month.MAY, 6)
                        .atStartOfDay(ZoneOffset.UTC)
                        .toInstant()),
                BookLendingInfos.lent(LocalDate.of(2020, Month.MAY, 10)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant())),
            List.of(Publishers.valid()), Optional.of(donation));
    }

    public static final Book minimal() {
        final Title title;

        title = new Title("", BookConstants.TITLE, "");
        return new Book(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE, null, false,
            List.of(), List.of(), List.of(), Optional.empty());
    }

    public static final Book returned() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new Book(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(BookLendingInfos.returned()),
            List.of(Publishers.valid()), Optional.of(donation));
    }

    public static final Book returnedHistory() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return new Book(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()),
            List.of(BookLendingInfos.returned(LocalDate.of(2020, Month.JANUARY, 1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant(),
                LocalDate.of(2020, Month.JANUARY, 2)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant()),
                BookLendingInfos.returned(LocalDate.of(2020, Month.JANUARY, 4)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant(),
                    LocalDate.of(2020, Month.FEBRUARY, 3)
                        .atStartOfDay(ZoneOffset.UTC)
                        .toInstant()),
                BookLendingInfos.returnedAlternative(LocalDate.of(2020, Month.FEBRUARY, 12)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant(),
                    LocalDate.of(2020, Month.FEBRUARY, 13)
                        .atStartOfDay(ZoneOffset.UTC)
                        .toInstant()),
                BookLendingInfos.returned(LocalDate.of(2020, Month.MAY, 4)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant(),
                    LocalDate.of(2020, Month.MAY, 6)
                        .atStartOfDay(ZoneOffset.UTC)
                        .toInstant()),
                BookLendingInfos.returned(LocalDate.of(2020, Month.MAY, 10)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant(),
                    LocalDate.of(2020, Month.MAY, 12)
                        .atStartOfDay(ZoneOffset.UTC)
                        .toInstant())),
            List.of(Publishers.valid()), Optional.of(donation));
    }

}
