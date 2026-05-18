
package com.bernardomg.association.profile.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.bernardomg.association.guest.test.configuration.factory.GuestConstants;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactChannelEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;

public final class ProfileEntities {

    public static final ProfileEntity firstNameChange() {
        final ProfileEntity entity;

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(GuestConstants.NUMBER);
        entity.setFirstName(GuestConstants.CHANGED_FIRST_NAME);
        entity.setLastName(GuestConstants.LAST_NAME);
        entity.setBirthDate(GuestConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setAddress(GuestConstants.ADDRESS);
        entity.setComments(GuestConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    public static final ProfileEntity valid() {
        final ProfileEntity entity;

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(GuestConstants.NUMBER);
        entity.setFirstName(GuestConstants.FIRST_NAME);
        entity.setLastName(GuestConstants.LAST_NAME);
        entity.setBirthDate(GuestConstants.BIRTH_DATE);
        entity.setIdentifier(GuestConstants.IDENTIFIER);
        entity.setContactChannels(new ArrayList<>(List.of()));
        entity.setAddress(GuestConstants.ADDRESS);
        entity.setComments(GuestConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    public static final ProfileEntity withEmail() {
        final ProfileEntity        entity;
        final ContactChannelEntity contactChannelEntity;

        contactChannelEntity = new ContactChannelEntity();
        contactChannelEntity.setContactMethod(ContactMethodEntities.email());
        contactChannelEntity.setDetail(GuestConstants.EMAIL);

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(GuestConstants.NUMBER);
        entity.setFirstName(GuestConstants.FIRST_NAME);
        entity.setLastName(GuestConstants.LAST_NAME);
        entity.setBirthDate(GuestConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>(List.of(contactChannelEntity)));
        entity.setAddress(GuestConstants.ADDRESS);
        entity.setComments(GuestConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        contactChannelEntity.setProfile(entity);

        return entity;
    }

    private ProfileEntities() {
        super();
    }

}
