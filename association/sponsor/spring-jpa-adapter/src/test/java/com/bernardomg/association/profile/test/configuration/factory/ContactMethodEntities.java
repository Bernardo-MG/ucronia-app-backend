
package com.bernardomg.association.profile.test.configuration.factory;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.sponsor.test.configuration.factory.SponsorContactMethodConstants;

public final class ContactMethodEntities {

    public static final ContactMethodEntity email() {
        final ContactMethodEntity entity;

        entity = new ContactMethodEntity();
        entity.setId(1L);
        entity.setNumber(SponsorContactMethodConstants.NUMBER);
        entity.setName(SponsorContactMethodConstants.EMAIL);

        return entity;
    }

    private ContactMethodEntities() {
        super();
    }

}
