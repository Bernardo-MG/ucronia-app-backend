
package com.bernardomg.association.member.test.config.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;

public final class MemberEntities {

    public static final MemberEntity missingSurname() {
        return MemberEntity.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName(MemberConstants.NAME)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final MemberEntity nameChange() {
        return MemberEntity.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName("Member 123")
            .withSurname("Surname")
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final MemberEntity valid() {
        return MemberEntity.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName(MemberConstants.NAME)
            .withSurname(MemberConstants.SURNAME)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final MemberEntity valid(final int index) {
        return MemberEntity.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName("Member " + index)
            .withSurname("Surname " + index)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

}
