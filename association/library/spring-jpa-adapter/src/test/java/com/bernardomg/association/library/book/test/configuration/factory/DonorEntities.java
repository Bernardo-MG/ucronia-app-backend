
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.book.adapter.inbound.jpa.model.DonorEntity;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class DonorEntities {

    public static final DonorEntity valid() {
        final DonorEntity entity;

        entity = new DonorEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setIdentifier(ProfileConstants.IDENTIFIER);

        return entity;
    }

    private DonorEntities() {
        super();
    }

}
