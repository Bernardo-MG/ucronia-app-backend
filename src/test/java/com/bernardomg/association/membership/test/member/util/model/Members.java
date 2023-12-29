
package com.bernardomg.association.membership.test.member.util.model;

import com.bernardomg.association.membership.member.model.Member;

public final class Members {

    public static final Member active() {
        return Member.builder()
            .number(1L)
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final Member active(final int index) {
        return Member.builder()
            .number(1L)
            .name("Member " + index)
            .surname("Surname " + index)
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final Member forIndex(final long index) {
        return Member.builder()
            .number(index)
            .name("Member " + index)
            .surname("Surname " + index)
            .phone(String.valueOf(12344 + index))
            .identifier(String.valueOf(6788 + index))
            .active(true)
            .build();
    }

    public static final Member forIndex(final long index, final boolean active) {
        return Member.builder()
            .number(index)
            .name("Member " + index)
            .surname("Surname " + index)
            .phone(String.valueOf(12344 + index))
            .identifier(String.valueOf(6788 + index))
            .active(active)
            .build();
    }

    public static final Member inactive() {
        return Member.builder()
            .number(1L)
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final Member inactive(final int index) {
        return Member.builder()
            .number(1L)
            .name("Member " + index)
            .surname("Surname " + index)
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final Member nameChange() {
        return Member.builder()
            .number(1L)
            .name("Member 123")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

}
