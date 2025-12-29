
package com.bernardomg.association.sponsor.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bernardomg.association.profile.test.configuration.factory.ContactMethodEntities;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.QuerySponsorContactChannelEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.QuerySponsorEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntityConstants;

public final class QuerySponsorEntities {

    public static final QuerySponsorEntity alternative() {
        final QuerySponsorEntity entity;

        entity = new QuerySponsorEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(ProfileConstants.ALTERNATIVE_LAST_NAME);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>(Set.of(SponsorEntityConstants.CONTACT_TYPE)));

        return entity;
    }

    public static final QuerySponsorEntity created() {
        final QuerySponsorEntity entity;

        entity = new QuerySponsorEntity();
        entity.setId(1L);
        entity.setNumber(1L);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>(Set.of(SponsorEntityConstants.CONTACT_TYPE)));

        return entity;
    }

    public static final QuerySponsorEntity valid() {
        final QuerySponsorEntity entity;

        entity = new QuerySponsorEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>(Set.of(SponsorEntityConstants.CONTACT_TYPE)));

        return entity;
    }

    public static final QuerySponsorEntity withEmail() {
        final QuerySponsorEntity               entity;
        final QuerySponsorContactChannelEntity contactChannelEntity;

        contactChannelEntity = new QuerySponsorContactChannelEntity();
        contactChannelEntity.setContactMethod(ContactMethodEntities.email());
        contactChannelEntity.setDetail(ProfileConstants.EMAIL);

        entity = new QuerySponsorEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of(contactChannelEntity));
        entity.setYears(List.of(SponsorConstants.YEAR));
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>(Set.of(SponsorEntityConstants.CONTACT_TYPE)));

        return entity;
    }

    private QuerySponsorEntities() {
        super();
    }

}
