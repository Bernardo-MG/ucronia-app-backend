
package com.bernardomg.association.membership.test.member.config.factory;

import com.bernardomg.association.membership.member.model.Member;

public final class Members {

    public static final Member active() {
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .name(MemberConstants.NAME)
            .surname(MemberConstants.SURNAME)
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final Member active(final int index) {
        return Member.builder()
            .number(MemberConstants.NUMBER)
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
            .number(index*10)
            .name("Member " + index)
            .surname("Surname " + index)
            .phone(String.valueOf(12344 + index))
            .identifier(String.valueOf(6788 + index))
            .active(active)
            .build();
    }

    public static final Member inactiveWithNumber(final long number) {
        return Member.builder()
            .number(number)
            .name(MemberConstants.NAME)
            .surname(MemberConstants.SURNAME)
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final Member inactive() {
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .name(MemberConstants.NAME)
            .surname(MemberConstants.SURNAME)
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final Member inactive(final int index) {
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .name("Member " + index)
            .surname("Surname " + index)
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final Member nameChange() {
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .name("Member 123")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

}
