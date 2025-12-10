
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.contact.test.configuration.factory.ContactEntities;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;

public final class MemberEntities {

    public static final MemberEntity active() {
        final MemberEntity entity;

        entity = new MemberEntity();
        entity.setId(1L);
        entity.setContact(ContactEntities.valid());
        entity.setActive(true);
        entity.setRenew(true);
        return entity;
    }

    public static final MemberEntity alternative() {
        final MemberEntity entity;

        entity = new MemberEntity();
        entity.setId(1L);
        entity.setContact(ContactEntities.alternative());
        entity.setActive(true);
        entity.setRenew(true);
        return entity;
    }

    public static final MemberEntity inactive() {
        final MemberEntity entity;

        entity = new MemberEntity();
        entity.setId(1L);
        entity.setContact(ContactEntities.valid());
        entity.setActive(false);
        entity.setRenew(true);
        return entity;
    }

    private MemberEntities() {
        super();
    }

}
