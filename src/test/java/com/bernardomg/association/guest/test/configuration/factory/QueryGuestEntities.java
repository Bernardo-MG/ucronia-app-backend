
package com.bernardomg.association.guest.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethodEntities;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntityConstants;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.QueryGuestContactChannelEntity;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.QueryGuestEntity;

public final class QueryGuestEntities {

    public static final QueryGuestEntity alternative() {
        final QueryGuestEntity entity;

        entity = new QueryGuestEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(ContactConstants.ALTERNATIVE_LAST_NAME);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of());
        entity.setGames(List.of(GuestConstants.DATE));
        entity.setComments(ContactConstants.COMMENTS);
        entity.setTypes(List.of(GuestEntityConstants.CONTACT_TYPE));

        return entity;
    }

    public static final QueryGuestEntity created() {
        final QueryGuestEntity entity;

        entity = new QueryGuestEntity();
        entity.setId(1L);
        entity.setNumber(1L);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of());
        entity.setGames(List.of(GuestConstants.DATE));
        entity.setComments(ContactConstants.COMMENTS);
        entity.setTypes(List.of(GuestEntityConstants.CONTACT_TYPE));

        return entity;
    }

    public static final QueryGuestEntity valid() {
        final QueryGuestEntity entity;

        entity = new QueryGuestEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of());
        entity.setGames(List.of(GuestConstants.DATE));
        entity.setComments(ContactConstants.COMMENTS);
        entity.setTypes(List.of(GuestEntityConstants.CONTACT_TYPE));

        return entity;
    }

    public static final QueryGuestEntity withEmail() {
        final QueryGuestEntity               entity;
        final QueryGuestContactChannelEntity contactChannelEntity;

        contactChannelEntity = new QueryGuestContactChannelEntity();
        contactChannelEntity.setContactMethod(ContactMethodEntities.email());
        contactChannelEntity.setDetail(ContactConstants.EMAIL);

        entity = new QueryGuestEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of(contactChannelEntity));
        entity.setGames(List.of(GuestConstants.DATE));
        entity.setComments(ContactConstants.COMMENTS);
        entity.setTypes(List.of(GuestEntityConstants.CONTACT_TYPE));

        return entity;
    }

    private QueryGuestEntities() {
        super();
    }

}
