
package com.bernardomg.association.security.user.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.person.test.configuration.factory.PersonEntities;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserPersonEntity;
import com.bernardomg.security.user.data.adapter.inbound.jpa.model.UserEntity;

public final class UserMemberEntities {

    private static final String ENCODED_PASSWORD = "$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW";

    public static final UserPersonEntity valid() {
        final UserPersonEntity entity;

        entity = new UserPersonEntity();
        entity.setPerson(PersonEntities.noMembership());
        entity.setUser(enabledUser());

        return entity;
    }

    private static final UserEntity enabledUser() {
        final UserEntity entity;

        entity = new UserEntity();
        entity.setId(1L);
        entity.setName(UserConstants.NAME);
        entity.setUsername(UserConstants.USERNAME);
        entity.setEmail(UserConstants.EMAIL);
        entity.setPassword(ENCODED_PASSWORD);
        entity.setEnabled(true);
        entity.setNotExpired(true);
        entity.setPasswordNotExpired(true);
        entity.setNotLocked(true);
        entity.setRoles(List.of());

        return entity;
    }

    private UserMemberEntities() {
        super();
    }

}
