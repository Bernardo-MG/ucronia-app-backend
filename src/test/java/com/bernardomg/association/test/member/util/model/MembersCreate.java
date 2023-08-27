
package com.bernardomg.association.test.member.util.model;

import com.bernardomg.association.member.model.request.MemberCreate;
import com.bernardomg.association.member.model.request.ValidatedMemberCreate;

public final class MembersCreate {

    public static final MemberCreate active() {
        return ValidatedMemberCreate.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberCreate alternative() {
        return ValidatedMemberCreate.builder()
            .name("Member 2")
            .surname("Surname 2")
            .phone("12345 2")
            .identifier("6789 2")
            .build();
    }

    public static final MemberCreate missingIdentifier() {
        return ValidatedMemberCreate.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .build();
    }

    public static final MemberCreate missingName() {
        return ValidatedMemberCreate.builder()
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberCreate missingPhone() {
        return ValidatedMemberCreate.builder()
            .name("Member")
            .surname("Surname")
            .identifier("6789")
            .build();
    }

    public static final MemberCreate missingSurname() {
        return ValidatedMemberCreate.builder()
            .name("Member")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberCreate paddedWithWhitespaces() {
        return ValidatedMemberCreate.builder()
            .name(" Member ")
            .surname(" Surname ")
            .phone("12345")
            .identifier("6789")
            .build();
    }

}
