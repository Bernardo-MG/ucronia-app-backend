
package com.bernardomg.association.library.book.test.configuration.factory;

import java.util.List;
import java.util.Optional;

import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;

public final class Books {

    public static final Book full() {
        final Title    title;
        final Donation donation;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        donation = new Donation(BookConstants.DONATION_DATE, List.of(Donors.valid()));
        return Book.builder()
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

}
