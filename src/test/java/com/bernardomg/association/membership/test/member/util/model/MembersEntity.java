
package com.bernardomg.association.membership.test.member.util.model;

import com.bernardomg.association.membership.member.persistence.model.MemberEntity;

public final class MembersEntity {

    public static final MemberEntity missingSurname() {
        return MemberEntity.builder()
            .name("Member 1")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberEntity valid() {
        return MemberEntity.builder()
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberEntity valid(final int index) {
        return MemberEntity.builder()
            .name("Member " + index)
            .surname("Surname " + index)
            .phone("12345")
            .identifier("6789")
            .build();
    }

}
