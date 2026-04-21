
package com.bernardomg.association.sponsor.test.factory;

import java.util.HashSet;
import java.util.List;

import com.bernardomg.association.profile.test.configuration.factory.ProfileEntities;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntityConstants;
import com.bernardomg.association.sponsor.test.configuration.factory.SponsorConstants;

public final class SponsorEntities {

    public static final SponsorEntity created() {
        final SponsorEntity entity;

        entity = new SponsorEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.valid());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(SponsorEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final SponsorEntity createdWithEmail() {
        final SponsorEntity entity;

        entity = new SponsorEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.withEmail());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(SponsorEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final SponsorEntity firstNameChange() {
        final SponsorEntity entity;

        entity = new SponsorEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.firstNameChange());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(SponsorEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final SponsorEntity valid() {
        final SponsorEntity entity;

        entity = new SponsorEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.valid());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(SponsorEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final SponsorEntity withEmail() {
        final SponsorEntity entity;

        entity = new SponsorEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.withEmail());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(SponsorEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    private SponsorEntities() {
        super();
    }

}
