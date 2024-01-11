
package com.bernardomg.association.membership.test.member.config.factory;

import com.bernardomg.association.membership.member.model.Member;

public final class Members {

    public static final Member active() {
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .fullName(MemberConstants.FULL_NAME)
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
            .fullName("Member " + index + " Surname " + index)
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
            .fullName("Member " + index + " Surname " + index)
            .name("Member " + index)
            .surname("Surname " + index)
            .phone(String.valueOf(12344 + index))
            .identifier(String.valueOf(6788 + index))
            .active(true)
            .build();
    }

    public static final Member forIndex(final long index, final boolean active) {
        return Member.builder()
            .number(index * 10)
            .fullName("Member " + index + " Surname " + index)
            .name("Member " + index)
            .surname("Surname " + index)
            .phone(String.valueOf(12344 + index))
            .identifier(String.valueOf(6788 + index))
            .active(active)
            .build();
    }

    public static final Member inactive() {
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .fullName(MemberConstants.FULL_NAME)
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
            .fullName("Member " + index + " Surname " + index)
            .name("Member " + index)
            .surname("Surname " + index)
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final Member inactiveWithNumber(final long number) {
        return Member.builder()
            .number(number)
            .fullName(MemberConstants.FULL_NAME)
            .name(MemberConstants.NAME)
            .surname(MemberConstants.SURNAME)
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final Member nameChange() {
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .fullName("Member 123 Surname")
            .name("Member 123")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final Member nameChangeActive() {
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .fullName("Member 123 Surname")
            .name("Member 123")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

}
