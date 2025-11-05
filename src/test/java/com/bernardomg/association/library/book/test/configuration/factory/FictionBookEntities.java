
package com.bernardomg.association.library.book.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.test.configuration.factory.ContactEntities;
import com.bernardomg.association.library.author.test.configuration.factory.AuthorEntities;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.FictionBookEntity;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherEntities;

public final class FictionBookEntities {

    public static final FictionBookEntity full() {
        final FictionBookEntity entity = new FictionBookEntity();
        entity.setNumber(BookConstants.NUMBER);
        entity.setSupertitle(BookConstants.SUPERTITLE);
        entity.setTitle(BookConstants.TITLE);
        entity.setSubtitle(BookConstants.SUBTITLE);
        entity.setIsbn(BookConstants.ISBN_10);
        entity.setLanguage(BookConstants.LANGUAGE);
        entity.setPublishDate(BookConstants.PUBLISH_DATE);
        entity.setDonationDate(BookConstants.DONATION_DATE);
        entity.setAuthors(List.of(AuthorEntities.valid()));
        entity.setDonors(List.of(ContactEntities.minimal()));
        entity.setPublishers(List.of(PublisherEntities.valid()));
        return entity;
    }

    public static final FictionBookEntity isbn13() {
        final FictionBookEntity entity = new FictionBookEntity();
        entity.setNumber(BookConstants.NUMBER);
        entity.setSupertitle(BookConstants.SUPERTITLE);
        entity.setTitle(BookConstants.TITLE);
        entity.setSubtitle(BookConstants.SUBTITLE);
        entity.setIsbn(BookConstants.ISBN_13);
        entity.setLanguage(BookConstants.LANGUAGE);
        entity.setPublishDate(BookConstants.PUBLISH_DATE);
        entity.setDonationDate(BookConstants.DONATION_DATE);
        entity.setAuthors(List.of(AuthorEntities.valid()));
        entity.setDonors(List.of(ContactEntities.minimal()));
        entity.setPublishers(List.of(PublisherEntities.valid()));
        return entity;
    }

    public static final FictionBookEntity minimal() {
        final FictionBookEntity entity = new FictionBookEntity();
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

    public static final FictionBookEntity noRelationships() {
        final FictionBookEntity entity = new FictionBookEntity();
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

    private FictionBookEntities() {
        super();
    }
}
