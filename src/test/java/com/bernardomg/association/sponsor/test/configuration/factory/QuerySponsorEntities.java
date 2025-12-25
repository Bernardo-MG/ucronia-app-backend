
package com.bernardomg.association.sponsor.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethodEntities;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.QuerySponsorContactChannelEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.QuerySponsorEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntityConstants;

public final class QuerySponsorEntities {

    public static final QuerySponsorEntity alternative() {
        final QuerySponsorEntity entity;

        entity = new QuerySponsorEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(ContactConstants.ALTERNATIVE_LAST_NAME);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.setComments(ContactConstants.COMMENTS);
        entity.setTypes(List.of(SponsorEntityConstants.CONTACT_TYPE));

        return entity;
    }

    public static final QuerySponsorEntity created() {
        final QuerySponsorEntity entity;

        entity = new QuerySponsorEntity();
        entity.setId(1L);
        entity.setNumber(1L);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.setComments(ContactConstants.COMMENTS);
        entity.setTypes(List.of(SponsorEntityConstants.CONTACT_TYPE));

        return entity;
    }

    public static final QuerySponsorEntity valid() {
        final QuerySponsorEntity entity;

        entity = new QuerySponsorEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.setComments(ContactConstants.COMMENTS);
        entity.setTypes(List.of(SponsorEntityConstants.CONTACT_TYPE));

        return entity;
    }

    public static final QuerySponsorEntity withEmail() {
        final QuerySponsorEntity               entity;
        final QuerySponsorContactChannelEntity contactChannelEntity;

        contactChannelEntity = new QuerySponsorContactChannelEntity();
        contactChannelEntity.setContactMethod(ContactMethodEntities.email());
        contactChannelEntity.setDetail(ContactConstants.EMAIL);

        entity = new QuerySponsorEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of(contactChannelEntity));
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.setComments(ContactConstants.COMMENTS);
        entity.setTypes(List.of(SponsorEntityConstants.CONTACT_TYPE));

        return entity;
    }

    private QuerySponsorEntities() {
        super();
    }

}
