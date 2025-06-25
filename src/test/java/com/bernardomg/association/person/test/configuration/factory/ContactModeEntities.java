
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.adapter.inbound.jpa.model.ContactModeEntity;

public final class ContactModeEntities {

    public static final ContactModeEntity valid() {
        final ContactModeEntity entity;

        entity = new ContactModeEntity();
        entity.setId(1L);
        entity.setNumber(ContactModeConstants.NUMBER);
        entity.setName(ContactModeConstants.NAME);

        return entity;
    }

}
