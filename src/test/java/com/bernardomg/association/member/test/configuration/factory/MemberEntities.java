
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberEntity;

public final class MemberEntities {

    public static final QueryMemberEntity active() {
        final QueryMemberEntity entity;

        entity = new QueryMemberEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setComments(ContactConstants.COMMENTS);

        return entity;
    }

    public static final QueryMemberEntity alternative() {
        final QueryMemberEntity entity;

        entity = new QueryMemberEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(ContactConstants.ALTERNATIVE_LAST_NAME);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setComments(ContactConstants.COMMENTS);

        return entity;
    }

    public static final QueryMemberEntity created() {
        final QueryMemberEntity entity;

        entity = new QueryMemberEntity();
        entity.setId(1L);
        entity.setNumber(1L);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setComments(ContactConstants.COMMENTS);

        return entity;
    }

    public static final QueryMemberEntity inactive() {
        final QueryMemberEntity entity;

        entity = new QueryMemberEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setActive(false);
        entity.setRenew(true);
        entity.setComments(ContactConstants.COMMENTS);

        return entity;
    }

    private MemberEntities() {
        super();
    }

}
