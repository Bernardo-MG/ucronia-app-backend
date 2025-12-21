
package com.bernardomg.association.guest.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.QueryGuestEntity;

public final class GuestEntities {

    public static final QueryGuestEntity alternative() {
        final QueryGuestEntity entity;

        entity = new QueryGuestEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(ContactConstants.ALTERNATIVE_LAST_NAME);
        entity.setGames(List.of(GuestConstants.DATE));

        return entity;
    }

    public static final QueryGuestEntity created() {
        final QueryGuestEntity entity;

        entity = new QueryGuestEntity();
        entity.setId(1L);
        entity.setNumber(1L);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setGames(List.of(GuestConstants.DATE));

        return entity;
    }

    public static final QueryGuestEntity valid() {
        final QueryGuestEntity entity;

        entity = new QueryGuestEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setGames(List.of(GuestConstants.DATE));

        return entity;
    }

    private GuestEntities() {
        super();
    }

}
