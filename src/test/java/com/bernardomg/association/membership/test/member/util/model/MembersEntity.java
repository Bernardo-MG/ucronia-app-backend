
package com.bernardomg.association.membership.test.member.util.model;

import com.bernardomg.association.membership.member.persistence.model.MemberEntity;

public final class MembersEntity {

    public static final MemberEntity valid() {
        return MemberEntity.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .build();
    }

}
