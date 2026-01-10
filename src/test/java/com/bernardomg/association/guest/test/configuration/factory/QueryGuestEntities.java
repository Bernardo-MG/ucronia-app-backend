
package com.bernardomg.association.guest.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntityConstants;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.QueryGuestContactChannelEntity;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.QueryGuestEntity;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodEntities;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class QueryGuestEntities {

    public static final QueryGuestEntity alternative() {
        final QueryGuestEntity entity;

        entity = new QueryGuestEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(ProfileConstants.ALTERNATIVE_LAST_NAME);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setGames(new ArrayList<>(List.of(GuestConstants.DATE)));
        entity.setAddress(ProfileConstants.ADDRESS);
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>(Set.of(GuestEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final QueryGuestEntity created() {
        final QueryGuestEntity entity;

        entity = new QueryGuestEntity();
        entity.setId(1L);
        entity.setNumber(1L);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setGames(new ArrayList<>(List.of(GuestConstants.DATE)));
        entity.setAddress(ProfileConstants.ADDRESS);
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>(Set.of(GuestEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final QueryGuestEntity valid() {
        final QueryGuestEntity entity;

        entity = new QueryGuestEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setGames(new ArrayList<>(List.of(GuestConstants.DATE)));
        entity.setAddress(ProfileConstants.ADDRESS);
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>(Set.of(GuestEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final QueryGuestEntity withEmail() {
        final QueryGuestEntity               entity;
        final QueryGuestContactChannelEntity contactChannelEntity;

        contactChannelEntity = new QueryGuestContactChannelEntity();
        contactChannelEntity.setContactMethod(ContactMethodEntities.email());
        contactChannelEntity.setDetail(ProfileConstants.EMAIL);

        entity = new QueryGuestEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>(List.of(contactChannelEntity)));
        entity.setGames(new ArrayList<>(List.of(GuestConstants.DATE)));
        entity.setAddress(ProfileConstants.ADDRESS);
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>(Set.of(GuestEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    private QueryGuestEntities() {
        super();
    }

}
