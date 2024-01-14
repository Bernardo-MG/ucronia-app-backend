
package com.bernardomg.association.membership.test.member.config.factory;

import com.bernardomg.association.member.persistence.model.MemberEntity;

public final class MemberEntities {

    public static final MemberEntity missingSurname() {
        return MemberEntity.builder()
            .name(MemberConstants.NAME)
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberEntity nameChange() {
        return MemberEntity.builder()
            .name("Member 123")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberEntity valid() {
        return MemberEntity.builder()
            .name(MemberConstants.NAME)
            .surname(MemberConstants.SURNAME)
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
