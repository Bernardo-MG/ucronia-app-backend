
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.book.adapter.inbound.jpa.model.DonorEntity;

public final class DonorEntities {

    public static final DonorEntity valid() {
        final DonorEntity entity;

        entity = new DonorEntity();
        entity.setId(1L);
        entity.setNumber(DonorConstants.NUMBER);
        entity.setFirstName(DonorConstants.FIRST_NAME);
        entity.setLastName(DonorConstants.LAST_NAME);
        entity.setIdentifier(DonorConstants.IDENTIFIER);

        return entity;
    }

    private DonorEntities() {
        super();
    }

}
