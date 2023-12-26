
package com.bernardomg.association.membership.test.member.util.model;

import com.bernardomg.association.membership.member.model.request.MemberCreate;
import com.bernardomg.association.membership.member.model.request.MemberCreateRequest;

public final class MembersCreate {

    public static final MemberCreate alternative() {
        return MemberCreateRequest.builder()
            .name("Member 2")
            .surname("Surname 2")
            .phone("12345 2")
            .identifier("6789 2")
            .build();
    }

    public static final MemberCreate missingIdentifier() {
        return MemberCreateRequest.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .build();
    }

    public static final MemberCreate missingName() {
        return MemberCreateRequest.builder()
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberCreate missingSurname() {
        return MemberCreateRequest.builder()
            .name("Member")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberCreate paddedWithWhitespaces() {
        return MemberCreateRequest.builder()
            .name(" Member 1 ")
            .surname(" Surname 1 ")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberCreate valid() {
        return MemberCreateRequest.builder()
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .build();
    }

}
