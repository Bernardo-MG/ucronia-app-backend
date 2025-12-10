
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.contact.test.configuration.factory.ContactEntities;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberContactEntity;

public final class MemberContactEntities {

    public static final MemberContactEntity active() {
        final MemberContactEntity entity;

        entity = new MemberContactEntity();
        entity.setId(1L);
        entity.setContact(ContactEntities.valid());
        entity.setActive(true);
        entity.setRenew(true);
        return entity;
    }

    public static final MemberContactEntity alternative() {
        final MemberContactEntity entity;

        entity = new MemberContactEntity();
        entity.setId(2L);
        entity.setContact(ContactEntities.alternative());
        return entity;
    }

    public static final MemberContactEntity inactive() {
        final MemberContactEntity entity;

        entity = new MemberContactEntity();
        entity.setId(1L);
        entity.setContact(ContactEntities.valid());
        entity.setActive(false);
        entity.setRenew(true);
        return entity;
    }

    public static final MemberContactEntity withEmail() {
        final MemberContactEntity entity;

        entity = new MemberContactEntity();
        entity.setId(1L);
        entity.setContact(ContactEntities.withEmail());
        entity.setActive(true);
        entity.setRenew(true);
        return entity;
    }

    private MemberContactEntities() {
        super();
    }

}
