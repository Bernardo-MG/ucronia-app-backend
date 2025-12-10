
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberContactEntity;

public final class MemberContactEntities {

    public static final MemberContactEntity active() {
        final MemberContactEntity entity = new MemberContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setActive(true);
        entity.setRenew(true);
        return entity;
    }

    public static final MemberContactEntity inactive() {
        final MemberContactEntity entity = new MemberContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setActive(false);
        entity.setRenew(true);
        return entity;
    }

    private MemberContactEntities() {
        super();
    }

}
