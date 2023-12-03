
package com.bernardomg.association.membership.test.member.util.model;

import com.bernardomg.association.membership.member.model.DtoMember;

public final class DtoMembers {

    public static final DtoMember active() {
        return DtoMember.builder()
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final DtoMember active(final int index) {
        return DtoMember.builder()
            .name("Member " + index)
            .surname("Surname " + index)
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final DtoMember inactive() {
        return DtoMember.builder()
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final DtoMember inactive(final int index) {
        return DtoMember.builder()
            .name("Member " + index)
            .surname("Surname " + index)
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

}
