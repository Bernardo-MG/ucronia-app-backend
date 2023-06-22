
package com.bernardomg.association.test.member.util.model;

import com.bernardomg.association.member.model.request.MemberUpdate;
import com.bernardomg.association.member.model.request.ValidatedMemberUpdate;

public final class MembersUpdate {

    public static final MemberUpdate nameChange() {
        return ValidatedMemberUpdate.builder()
            .id(1L)
            .name("Member 123")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

}
