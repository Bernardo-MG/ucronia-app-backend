
package com.bernardomg.association.library.book.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.library.author.test.configuration.factory.AuthorEntities;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeEntities;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystemEntities;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherEntities;
import com.bernardomg.association.person.test.configuration.factory.PersonEntities;

public final class BookEntities {

    public static final BookEntity full() {
        return BookEntity.builder()
            .withNumber(BookConstants.NUMBER)
            .withSupertitle(BookConstants.SUPERTITLE)
            .withTitle(BookConstants.TITLE)
            .withSubtitle(BookConstants.SUBTITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withDonationDate(BookConstants.DONATION_DATE)
            .withAuthors(List.of(AuthorEntities.valid()))
            .withDonors(List.of(PersonEntities.noMembership()))
            .withPublishers(List.of(PublisherEntities.valid()))
            .withBookType(BookTypeEntities.valid())
            .withGameSystem(GameSystemEntities.valid())
            .build();
    }

    public static final BookEntity isbn13() {
        return BookEntity.builder()
            .withNumber(BookConstants.NUMBER)
            .withSupertitle(BookConstants.SUPERTITLE)
            .withTitle(BookConstants.TITLE)
            .withSubtitle(BookConstants.SUBTITLE)
            .withIsbn(BookConstants.ISBN_13)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withDonationDate(BookConstants.DONATION_DATE)
            .withAuthors(List.of(AuthorEntities.valid()))
            .withDonors(List.of(PersonEntities.noMembership()))
            .withPublishers(List.of(PublisherEntities.valid()))
            .withBookType(BookTypeEntities.valid())
            .withGameSystem(GameSystemEntities.valid())
            .build();
    }

    public static final BookEntity minimal() {
        return BookEntity.builder()
            .withNumber(BookConstants.NUMBER)
            .withSupertitle("")
            .withTitle(BookConstants.TITLE)
            .withSubtitle("")
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withAuthors(List.of())
            .withDonors(List.of())
            .withPublishers(List.of())
            .build();
    }

    public static final BookEntity noRelationships() {
        return BookEntity.builder()
            .withNumber(BookConstants.NUMBER)
            .withSupertitle(BookConstants.SUPERTITLE)
            .withTitle(BookConstants.TITLE)
            .withSubtitle(BookConstants.SUBTITLE)
            .withIsbn(BookConstants.ISBN_10)
            .withLanguage(BookConstants.LANGUAGE)
            .withPublishDate(BookConstants.PUBLISH_DATE)
            .withDonationDate(BookConstants.DONATION_DATE)
            .withAuthors(List.of())
            .withDonors(List.of())
            .withPublishers(List.of())
            .build();
    }

}
