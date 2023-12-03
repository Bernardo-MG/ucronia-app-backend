
package com.bernardomg.association.membership.test.member.util.model;

import com.bernardomg.association.membership.member.model.request.MemberUpdate;
import com.bernardomg.association.membership.member.model.request.MemberUpdateRequest;

public final class MembersUpdate {

    public static final MemberUpdate active() {
        return MemberUpdateRequest.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final MemberUpdate missingActive() {
        return MemberUpdateRequest.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberUpdate missingName() {
        return MemberUpdateRequest.builder()
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final MemberUpdate nameChange() {
        return MemberUpdateRequest.builder()
            .name("Member 123")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final MemberUpdate paddedWithWhitespaces() {
        return MemberUpdateRequest.builder()
            .name(" Member 123 ")
            .surname(" Surname 123 ")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

}
