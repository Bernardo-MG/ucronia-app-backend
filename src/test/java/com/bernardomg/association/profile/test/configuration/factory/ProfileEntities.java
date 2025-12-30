
package com.bernardomg.association.profile.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactChannelEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;

public final class ProfileEntities {

    public static final ProfileEntity alternative() {
        final ProfileEntity entity;

        entity = new ProfileEntity();
        entity.setId(2L);
        entity.setNumber(ProfileConstants.ALTERNATIVE_NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    public static final ProfileEntity createdWithEmail() {
        final ProfileEntity        entity;
        final ContactChannelEntity contactChannelEntity;

        contactChannelEntity = new ContactChannelEntity();
        contactChannelEntity.setContactMethod(ContactMethodEntities.email());
        contactChannelEntity.setDetail(ProfileConstants.EMAIL);

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(1L);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>(List.of(contactChannelEntity)));
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        contactChannelEntity.setProfile(entity);

        return entity;
    }

    public static final ProfileEntity firstNameChange() {
        final ProfileEntity entity;

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName("Profile 123");
        entity.setLastName("Last name");
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    public static final ProfileEntity missingLastName() {
        final ProfileEntity entity;

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    public static final ProfileEntity valid() {
        final ProfileEntity entity;

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier(ProfileConstants.IDENTIFIER);
        entity.setContactChannels(new ArrayList<>());
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    public static final ProfileEntity withEmail() {
        final ProfileEntity        entity;
        final ContactChannelEntity contactChannelEntity;

        contactChannelEntity = new ContactChannelEntity();
        contactChannelEntity.setContactMethod(ContactMethodEntities.email());
        contactChannelEntity.setDetail(ProfileConstants.EMAIL);

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>(List.of(contactChannelEntity)));
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        contactChannelEntity.setProfile(entity);

        return entity;
    }

    public static final ProfileEntity withEmailAndPhone() {
        final ProfileEntity        entity;
        final ContactChannelEntity contactChannelEntityA;
        final ContactChannelEntity contactChannelEntityB;

        contactChannelEntityA = new ContactChannelEntity();
        contactChannelEntityA.setContactMethod(ContactMethodEntities.email());
        contactChannelEntityA.setDetail(ProfileConstants.EMAIL);

        contactChannelEntityB = new ContactChannelEntity();
        contactChannelEntityB.setContactMethod(ContactMethodEntities.phone());
        contactChannelEntityB.setDetail(ProfileConstants.PHONE);

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>(List.of(contactChannelEntityA, contactChannelEntityB)));
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        contactChannelEntityA.setProfile(entity);

        return entity;
    }

    public static final ProfileEntity withTwoEmails() {
        final ProfileEntity        entity;
        final ContactChannelEntity contactChannelEntityA;
        final ContactChannelEntity contactChannelEntityB;

        contactChannelEntityA = new ContactChannelEntity();
        contactChannelEntityA.setContactMethod(ContactMethodEntities.email());
        contactChannelEntityA.setDetail(ProfileConstants.EMAIL);

        contactChannelEntityB = new ContactChannelEntity();
        contactChannelEntityB.setContactMethod(ContactMethodEntities.email());
        contactChannelEntityB.setDetail(ProfileConstants.ALTERNATIVE_EMAIL);

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>(List.of(contactChannelEntityA, contactChannelEntityB)));
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        contactChannelEntityA.setProfile(entity);

        return entity;
    }

    public static final ProfileEntity withType(final String type) {
        final ProfileEntity entity;

        entity = new ProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier(ProfileConstants.IDENTIFIER);
        entity.setContactChannels(new ArrayList<>());
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>(List.of(type)));

        return entity;
    }

    private ProfileEntities() {
        super();
    }

}
