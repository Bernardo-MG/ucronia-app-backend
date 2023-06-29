
package com.bernardomg.association.test.member.util.model;

import com.bernardomg.association.member.model.request.MemberUpdate;
import com.bernardomg.association.member.model.request.ValidatedMemberUpdate;

public final class MembersUpdate {

    public static final MemberUpdate active() {
        return ValidatedMemberUpdate.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final MemberUpdate missingActive() {
        return ValidatedMemberUpdate.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberUpdate missingName() {
        return ValidatedMemberUpdate.builder()
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final MemberUpdate nameChange() {
        return ValidatedMemberUpdate.builder()
            .name("Member 123")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

}
