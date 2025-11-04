
package com.bernardomg.association.library.book.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.test.configuration.factory.ContactEntities;
import com.bernardomg.association.library.author.test.configuration.factory.AuthorEntities;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.GameBookEntity;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeEntities;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystemEntities;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherEntities;

public final class GameBookEntities {

    public static final GameBookEntity full() {
        final GameBookEntity entity = new GameBookEntity();
        entity.setNumber(BookConstants.NUMBER);
        entity.setSupertitle(BookConstants.SUPERTITLE);
        entity.setTitle(BookConstants.TITLE);
        entity.setSubtitle(BookConstants.SUBTITLE);
        entity.setIsbn(BookConstants.ISBN_10);
        entity.setLanguage(BookConstants.LANGUAGE);
        entity.setPublishDate(BookConstants.PUBLISH_DATE);
        entity.setDonationDate(BookConstants.DONATION_DATE);
        entity.setAuthors(List.of(AuthorEntities.valid()));
        entity.setDonors(List.of(ContactEntities.noMembership()));
        entity.setPublishers(List.of(PublisherEntities.valid()));
        entity.setBookType(BookTypeEntities.valid());
        entity.setGameSystem(GameSystemEntities.valid());
        return entity;
    }

    public static final GameBookEntity isbn13() {
        final GameBookEntity entity = new GameBookEntity();
        entity.setNumber(BookConstants.NUMBER);
        entity.setSupertitle(BookConstants.SUPERTITLE);
        entity.setTitle(BookConstants.TITLE);
        entity.setSubtitle(BookConstants.SUBTITLE);
        entity.setIsbn(BookConstants.ISBN_13);
        entity.setLanguage(BookConstants.LANGUAGE);
        entity.setPublishDate(BookConstants.PUBLISH_DATE);
        entity.setDonationDate(BookConstants.DONATION_DATE);
        entity.setAuthors(List.of(AuthorEntities.valid()));
        entity.setDonors(List.of(ContactEntities.noMembership()));
        entity.setPublishers(List.of(PublisherEntities.valid()));
        entity.setBookType(BookTypeEntities.valid());
        entity.setGameSystem(GameSystemEntities.valid());
        return entity;
    }

    public static final GameBookEntity minimal() {
        final GameBookEntity entity = new GameBookEntity();
        entity.setNumber(BookConstants.NUMBER);
        entity.setSupertitle("");
        entity.setTitle(BookConstants.TITLE);
        entity.setSubtitle("");
        entity.setIsbn(BookConstants.ISBN_10);
        entity.setLanguage(BookConstants.LANGUAGE);
        entity.setAuthors(List.of());
        entity.setDonors(List.of());
        entity.setPublishers(List.of());
        return entity;
    }

    public static final GameBookEntity noRelationships() {
        final GameBookEntity entity = new GameBookEntity();
        entity.setNumber(BookConstants.NUMBER);
        entity.setSupertitle(BookConstants.SUPERTITLE);
        entity.setTitle(BookConstants.TITLE);
        entity.setSubtitle(BookConstants.SUBTITLE);
        entity.setIsbn(BookConstants.ISBN_10);
        entity.setLanguage(BookConstants.LANGUAGE);
        entity.setPublishDate(BookConstants.PUBLISH_DATE);
        entity.setDonationDate(BookConstants.DONATION_DATE);
        entity.setAuthors(List.of());
        entity.setDonors(List.of());
        entity.setPublishers(List.of());
        return entity;
    }

    private GameBookEntities() {
        super();
    }

}
