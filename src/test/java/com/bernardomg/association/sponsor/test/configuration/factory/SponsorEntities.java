
package com.bernardomg.association.sponsor.test.configuration.factory;

import java.util.List;
import java.util.Set;

import com.bernardomg.association.profile.test.configuration.factory.ProfileEntities;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntityConstants;

public final class SponsorEntities {

    public static final SponsorEntity created() {
        final SponsorEntity entity;

        entity = new SponsorEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.valid());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.getProfile()
            .setTypes(Set.of(SponsorEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final SponsorEntity createdWithEmail() {
        final SponsorEntity entity;

        entity = new SponsorEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.withEmail());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.getProfile()
            .setTypes(Set.of(SponsorEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final SponsorEntity valid() {
        final SponsorEntity entity;

        entity = new SponsorEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.valid());
        entity.setYears(List.of(SponsorConstants.YEAR));

        return entity;
    }

    private SponsorEntities() {
        super();
    }

}
