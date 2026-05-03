
package com.bernardomg.association.guest.test.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestContactChannelEntity;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestContactMethodEntity;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntity;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntityConstants;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestInnerProfileEntity;
import com.bernardomg.association.guest.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.guest.test.configuration.factory.GuestConstants;
import com.bernardomg.association.guest.test.configuration.factory.Guests;

public final class GuestEntities {

    public static final GuestEntity created() {
        final GuestEntity entity;

        entity = new GuestEntity();
        entity.setId(1L);
        entity.setProfile(valid());
        entity.setGames(new ArrayList<>(List.of(Guests.DATE)));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(GuestEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final GuestEntity createdWithEmail() {
        final GuestEntity entity;

        entity = new GuestEntity();
        entity.setId(1L);
        entity.setProfile(withEmail());
        entity.setGames(new ArrayList<>(List.of(Guests.DATE)));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(GuestEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final GuestEntity firstNameChange() {
        final GuestEntity entity;

        entity = new GuestEntity();
        entity.setId(1L);
        entity.setProfile(firstNameChangeProfile());
        entity.setGames(new ArrayList<>(List.of(Guests.DATE)));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(GuestEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final GuestEntity noGames() {
        final GuestEntity entity;

        entity = new GuestEntity();
        entity.setId(1L);
        entity.setProfile(valid());
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(GuestEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final GuestEntity withGames() {
        final GuestEntity entity;

        entity = new GuestEntity();
        entity.setId(1L);
        entity.setProfile(valid());
        entity.setGames(new ArrayList<>(List.of(Guests.DATE)));
        entity.getProfile()
            .setTypes(new HashSet<>(List.of(GuestEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    private static final GuestContactMethodEntity email() {
        final GuestContactMethodEntity entity;

        entity = new GuestContactMethodEntity();
        entity.setId(1L);
        entity.setNumber(ContactMethodConstants.NUMBER);
        entity.setName(ContactMethodConstants.EMAIL);

        return entity;
    }

    private static final GuestInnerProfileEntity firstNameChangeProfile() {
        final GuestInnerProfileEntity entity;

        entity = new GuestInnerProfileEntity();
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

    private static final GuestInnerProfileEntity valid() {
        final GuestInnerProfileEntity entity;

        entity = new GuestInnerProfileEntity();
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

    private static final GuestInnerProfileEntity withEmail() {
        final GuestInnerProfileEntity   entity;
        final GuestContactChannelEntity contactChannelEntity;

        contactChannelEntity = new GuestContactChannelEntity();
        contactChannelEntity.setContactMethod(email());
        contactChannelEntity.setDetail(GuestConstants.EMAIL);

        entity = new GuestInnerProfileEntity();
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

    private GuestEntities() {
        super();
    }

}
