
package com.bernardomg.association.membership.test.member.util.model;

import com.bernardomg.association.membership.member.model.DtoMember;

public final class DtoMembers {

    public static final DtoMember valid() {
        return DtoMember.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .build();
    }

}
