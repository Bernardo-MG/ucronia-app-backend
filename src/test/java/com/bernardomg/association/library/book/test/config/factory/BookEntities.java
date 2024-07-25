
package com.bernardomg.association.library.book.test.config.factory;

import java.util.List;

import com.bernardomg.association.library.author.test.config.factory.AuthorEntities;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.booktype.test.config.factory.BookTypeEntities;
import com.bernardomg.association.library.gamesystem.test.config.factory.GameSystemEntities;
import com.bernardomg.association.library.publisher.test.config.factory.PublisherEntities;
import com.bernardomg.association.person.test.config.factory.PersonEntities;

public final class BookEntities {

    public static final BookEntity full() {
        return BookEntity.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of(AuthorEntities.valid()))
            .withDonors(List.of(PersonEntities.valid()))
            .withPublishers(List.of(PublisherEntities.valid()))
            .withBookType(BookTypeEntities.valid())
            .withGameSystem(GameSystemEntities.valid())
            .build();
    }

    public static final BookEntity minimal() {
        return BookEntity.builder()
            .withNumber(BookConstants.NUMBER)
            .withTitle(BookConstants.TITLE)
            .withIsbn(BookConstants.ISBN)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withDonors(List.of())
            .withPublishers(List.of())
            .build();
    }

}
