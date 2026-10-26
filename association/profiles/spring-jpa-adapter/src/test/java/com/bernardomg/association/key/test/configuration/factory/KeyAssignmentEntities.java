
package com.bernardomg.association.key.test.configuration.factory;

import com.bernardomg.association.key.adapter.inbound.jpa.model.KeyAssignmentEntity;
import com.bernardomg.association.profile.test.factory.ProfileEntities;

public final class KeyAssignmentEntities {

    public static final KeyAssignmentEntity assigned() {
        final KeyAssignmentEntity entity;

        entity = new KeyAssignmentEntity();
        entity.setId(KeyConstants.ID);
        entity.setKey(KeyEntities.available());
        entity.setProfile(ProfileEntities.valid());

        return entity;
    }

    public static final KeyAssignmentEntity assignedAlternative() {
        final KeyAssignmentEntity entity;

        entity = new KeyAssignmentEntity();
        entity.setId(KeyConstants.ALTERNATIVE_ID);
        entity.setKey(KeyEntities.availableAlternative());
        entity.setProfile(ProfileEntities.valid());

        return entity;
    }

    public static final KeyAssignmentEntity unassigned() {
        final KeyAssignmentEntity entity;

        entity = new KeyAssignmentEntity();
        entity.setId(KeyConstants.ID);
        entity.setKey(null);
        entity.setProfile(ProfileEntities.valid());

        return entity;
    }

    private KeyAssignmentEntities() {
        super();
    }

}
