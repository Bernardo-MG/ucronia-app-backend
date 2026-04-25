
package com.bernardomg.association.sponsor.test.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.bernardomg.association.profile.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorContactChannelEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorContactMethodEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntityConstants;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorInnerProfileEntity;
import com.bernardomg.association.sponsor.test.configuration.factory.SponsorConstants;

public final class SponsorEntities {

    public static final SponsorEntity created() {
        final SponsorEntity entity;

        entity = new SponsorEntity();
        entity.setId(1L);
        entity.setProfile(validSponsor());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(SponsorEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final SponsorEntity createdWithEmail() {
        final SponsorEntity entity;

        entity = new SponsorEntity();
        entity.setId(1L);
        entity.setProfile(withEmailSponsor());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(SponsorEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final SponsorEntity firstNameChange() {
        final SponsorEntity entity;

        entity = new SponsorEntity();
        entity.setId(1L);
        entity.setProfile(firstNameChangeProfile());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(SponsorEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final SponsorInnerProfileEntity firstNameChangeProfile() {
        final SponsorInnerProfileEntity entity;

        entity = new SponsorInnerProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.CHANGED_FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setAddress(ProfileConstants.ADDRESS);
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    public static final SponsorEntity valid() {
        final SponsorEntity entity;

        entity = new SponsorEntity();
        entity.setId(1L);
        entity.setProfile(validSponsor());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(SponsorEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final SponsorEntity withEmail() {
        final SponsorEntity entity;

        entity = new SponsorEntity();
        entity.setId(1L);
        entity.setProfile(withEmailSponsor());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(SponsorEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    private static final SponsorContactMethodEntity email() {
        final SponsorContactMethodEntity entity;

        entity = new SponsorContactMethodEntity();
        entity.setId(1L);
        entity.setNumber(ContactMethodConstants.NUMBER);
        entity.setName(ContactMethodConstants.EMAIL);

        return entity;
    }

    private static final SponsorInnerProfileEntity validSponsor() {
        final SponsorInnerProfileEntity entity;

        entity = new SponsorInnerProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier(ProfileConstants.IDENTIFIER);
        entity.setContactChannels(new ArrayList<>(List.of()));
        entity.setAddress(ProfileConstants.ADDRESS);
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    private static final SponsorInnerProfileEntity withEmailSponsor() {
        final SponsorInnerProfileEntity   entity;
        final SponsorContactChannelEntity contactChannelEntity;

        contactChannelEntity = new SponsorContactChannelEntity();
        contactChannelEntity.setContactMethod(email());
        contactChannelEntity.setDetail(ProfileConstants.EMAIL);

        entity = new SponsorInnerProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>(List.of(contactChannelEntity)));
        entity.setAddress(ProfileConstants.ADDRESS);
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        contactChannelEntity.setProfile(entity);

        return entity;
    }

    private SponsorEntities() {
        super();
    }

}
