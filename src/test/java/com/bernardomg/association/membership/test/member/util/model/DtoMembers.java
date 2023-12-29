
package com.bernardomg.association.membership.test.member.util.model;

import com.bernardomg.association.membership.member.model.Member;

public final class DtoMembers {

    public static final Member active() {
        return Member.builder()
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final Member active(final int index) {
        return Member.builder()
            .name("Member " + index)
            .surname("Surname " + index)
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final Member inactive() {
        return Member.builder()
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final Member inactive(final int index) {
        return Member.builder()
            .name("Member " + index)
            .surname("Surname " + index)
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

}
