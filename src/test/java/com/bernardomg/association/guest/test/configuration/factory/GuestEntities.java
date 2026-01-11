
package com.bernardomg.association.guest.test.configuration.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntity;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntityConstants;
import com.bernardomg.association.profile.test.configuration.factory.ProfileEntities;

public final class GuestEntities {

    public static final GuestEntity created() {
        final GuestEntity entity;

        entity = new GuestEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.valid());
        entity.setGames(new ArrayList<>(List.of(GuestConstants.DATE)));
        entity.getProfile()
            .setTypes(Set.of(GuestEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final GuestEntity createdWithEmail() {
        final GuestEntity entity;

        entity = new GuestEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.withEmail());
        entity.setGames(new ArrayList<>(List.of(GuestConstants.DATE)));
        entity.getProfile()
            .setTypes(Set.of(GuestEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final GuestEntity valid() {
        final GuestEntity entity;

        entity = new GuestEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.valid());
        entity.setGames(new ArrayList<>(List.of(GuestConstants.DATE)));

        return entity;
    }

    private GuestEntities() {
        super();
    }

}
