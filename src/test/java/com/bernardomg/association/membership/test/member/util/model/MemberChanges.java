
package com.bernardomg.association.membership.test.member.util.model;

import com.bernardomg.association.membership.member.model.MemberChange;

public final class MemberChanges {

    public static final MemberChange alternative() {
        return MemberChange.builder()
            .name("Member 2")
            .surname("Surname 2")
            .phone("12345 2")
            .identifier("6789 2")
            .build();
    }

    public static final MemberChange missingActive() {
        return MemberChange.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberChange missingIdentifier() {
        return MemberChange.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .build();
    }

    public static final MemberChange missingName() {
        return MemberChange.builder()
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberChange missingSurname() {
        return MemberChange.builder()
            .name("Member")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberChange nameChange() {
        return MemberChange.builder()
            .name("Member 123")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberChange paddedWithWhitespaces() {
        return MemberChange.builder()
            .name(" Member 1 ")
            .surname(" Surname 1 ")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberChange valid() {
        return MemberChange.builder()
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .build();
    }

}
