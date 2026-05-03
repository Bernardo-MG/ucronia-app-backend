
package com.bernardomg.association.profile.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactChannelEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.sponsor.test.configuration.factory.SponsorConstants;

public final class ProfileEntities {

    public static final ProfileEntity firstNameChange() {
        final ProfileEntity entity;

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(SponsorConstants.NUMBER);
        entity.setFirstName(SponsorConstants.CHANGED_FIRST_NAME);
        entity.setLastName(SponsorConstants.LAST_NAME);
        entity.setBirthDate(SponsorConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setAddress(SponsorConstants.ADDRESS);
        entity.setComments(SponsorConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    public static final ProfileEntity valid() {
        final ProfileEntity entity;

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(SponsorConstants.NUMBER);
        entity.setFirstName(SponsorConstants.FIRST_NAME);
        entity.setLastName(SponsorConstants.LAST_NAME);
        entity.setBirthDate(SponsorConstants.BIRTH_DATE);
        entity.setIdentifier(SponsorConstants.IDENTIFIER);
        entity.setContactChannels(new ArrayList<>(List.of()));
        entity.setAddress(SponsorConstants.ADDRESS);
        entity.setComments(SponsorConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    public static final ProfileEntity withEmail() {
        final ProfileEntity        entity;
        final ContactChannelEntity contactChannelEntity;

        contactChannelEntity = new ContactChannelEntity();
        contactChannelEntity.setContactMethod(ContactMethodEntities.email());
        contactChannelEntity.setDetail(SponsorConstants.EMAIL);

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(SponsorConstants.NUMBER);
        entity.setFirstName(SponsorConstants.FIRST_NAME);
        entity.setLastName(SponsorConstants.LAST_NAME);
        entity.setBirthDate(SponsorConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>(List.of(contactChannelEntity)));
        entity.setAddress(SponsorConstants.ADDRESS);
        entity.setComments(SponsorConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        contactChannelEntity.setProfile(entity);

        return entity;
    }

    private ProfileEntities() {
        super();
    }

}
