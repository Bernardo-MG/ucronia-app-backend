
package com.bernardomg.association.profile.test.configuration.factory;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactMethodEntity;

public final class ContactMethodEntities {

    public static final ContactMethodEntity email() {
        final ContactMethodEntity entity;

        entity = new ContactMethodEntity();
        entity.setId(1L);
        entity.setNumber(ContactMethodConstants.NUMBER);
        entity.setName(ContactMethodConstants.EMAIL);

        return entity;
    }

    private ContactMethodEntities() {
        super();
    }

}
