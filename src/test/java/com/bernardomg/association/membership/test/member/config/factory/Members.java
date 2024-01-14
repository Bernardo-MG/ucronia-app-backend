
package com.bernardomg.association.membership.test.member.config.factory;

import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.MemberName;

public final class Members {

    public static final Member active() {
        final MemberName memberName;

        memberName = MemberName.builder()
            .firstName(MemberConstants.NAME)
            .lastName(MemberConstants.SURNAME)
            .fullName(MemberConstants.FULL_NAME)
            .build();
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .name(memberName)
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final Member active(final int index) {
        final MemberName memberName;

        memberName = MemberName.builder()
            .firstName("Member " + index)
            .lastName("Surname " + index)
            .fullName("Member " + index + " Surname " + index)
            .build();
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .name(memberName)
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

    public static final Member forIndex(final long index) {
        final MemberName memberName;

        memberName = MemberName.builder()
            .firstName("Member " + index)
            .lastName("Surname " + index)
            .fullName("Member " + index + " Surname " + index)
            .build();
        return Member.builder()
            .number(index)
            .name(memberName)
            .phone(String.valueOf(12344 + index))
            .identifier(String.valueOf(6788 + index))
            .active(true)
            .build();
    }

    public static final Member forIndex(final long index, final boolean active) {
        final MemberName memberName;

        memberName = MemberName.builder()
            .firstName("Member " + index)
            .lastName("Surname " + index)
            .fullName("Member " + index + " Surname " + index)
            .build();
        return Member.builder()
            .number(index * 10)
            .name(memberName)
            .phone(String.valueOf(12344 + index))
            .identifier(String.valueOf(6788 + index))
            .active(active)
            .build();
    }

    public static final Member inactive() {
        final MemberName memberName;

        memberName = MemberName.builder()
            .firstName(MemberConstants.NAME)
            .lastName(MemberConstants.SURNAME)
            .fullName(MemberConstants.FULL_NAME)
            .build();
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .name(memberName)
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final Member inactive(final int index) {
        final MemberName memberName;

        memberName = MemberName.builder()
            .firstName("Member " + index)
            .lastName("Surname " + index)
            .fullName("Member " + index + " Surname " + index)
            .build();
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .name(memberName)
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final Member inactiveWithNumber(final long number) {
        final MemberName memberName;

        memberName = MemberName.builder()
            .firstName(MemberConstants.NAME)
            .lastName(MemberConstants.SURNAME)
            .fullName(MemberConstants.FULL_NAME)
            .build();
        return Member.builder()
            .number(number)
            .name(memberName)
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final Member nameChange() {
        final MemberName memberName;

        memberName = MemberName.builder()
            .firstName("Member 123")
            .lastName("Surname")
            .fullName("Member 123 Surname")
            .build();
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .name(memberName)
            .phone("12345")
            .identifier("6789")
            .active(false)
            .build();
    }

    public static final Member nameChangeActive() {
        final MemberName memberName;

        memberName = MemberName.builder()
            .firstName("Member 123")
            .lastName("Surname")
            .fullName("Member 123 Surname")
            .build();
        return Member.builder()
            .number(MemberConstants.NUMBER)
            .name(memberName)
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build();
    }

}
