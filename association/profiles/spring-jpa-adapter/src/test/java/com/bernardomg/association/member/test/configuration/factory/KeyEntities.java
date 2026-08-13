
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.KeyEntity;

public final class KeyEntities {

    public static final KeyEntity available() {
        final KeyEntity entity;

        entity = new KeyEntity();
        entity.setId(KeyConstants.ID);
        entity.setNumber(KeyConstants.NUMBER);
        entity.setAvailable(true);
        entity.setDescription(KeyConstants.DESCRIPTION);

        return entity;
    }

    public static final KeyEntity descriptionChange() {
        final KeyEntity entity;

        entity = new KeyEntity();
        entity.setId(KeyConstants.ID);
        entity.setNumber(KeyConstants.NUMBER);
        entity.setAvailable(true);
        entity.setDescription(KeyConstants.DESCRIPTION_CHANGE);

        return entity;
    }

    private KeyEntities() {
        super();
    }

}
