
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
        return new Book(BookConstants.NUMBER, title, BookConstants.ISBN_10, BookConstants.LANGUAGE,
            BookConstants.PUBLISH_DATE, false, List.of(Authors.valid()), List.of(), List.of(Publishers.valid()),
            Optional.of(donation));
    }

}
