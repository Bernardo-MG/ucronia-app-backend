
package com.bernardomg.association.member.test.config.factory;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberName;

public final class Members {

    public static final Member active() {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withFirstName(MemberConstants.NAME)
            .withLastName(MemberConstants.SURNAME)
            .withFullName(MemberConstants.FULL_NAME)
            .build();
        return Member.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName(memberName)
            .withPhone("12345")
            .withIdentifier("6789")
            .withActive(true)
            .build();
    }

    public static final Member active(final int index) {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withFirstName("Member " + index)
            .withLastName("Surname " + index)
            .withFullName("Member " + index + " Surname " + index)
            .build();
        return Member.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName(memberName)
            .withPhone("12345")
            .withIdentifier("6789")
            .withActive(true)
            .build();
    }

    public static final Member emptyName() {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withFirstName(" ")
            .withLastName(MemberConstants.SURNAME)
            .withFullName(MemberConstants.SURNAME)
            .build();
        return Member.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName(memberName)
            .withPhone("12345")
            .withIdentifier("6789")
            .withActive(true)
            .build();
    }

    public static final Member forIndex(final long index) {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withFirstName("Member " + index)
            .withLastName("Surname " + index)
            .withFullName("Member " + index + " Surname " + index)
            .build();
        return Member.builder()
            .withNumber(index)
            .withName(memberName)
            .withPhone(String.valueOf(12344 + index))
            .withIdentifier(String.valueOf(6788 + index))
            .withActive(true)
            .build();
    }

    public static final Member forIndex(final long index, final boolean active) {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withFirstName("Member " + index)
            .withLastName("Surname " + index)
            .withFullName("Member " + index + " Surname " + index)
            .build();
        return Member.builder()
            .withNumber(index * 10)
            .withName(memberName)
            .withPhone(String.valueOf(12344 + index))
            .withIdentifier(String.valueOf(6788 + index))
            .withActive(active)
            .build();
    }

    public static final Member inactive() {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withFirstName(MemberConstants.NAME)
            .withLastName(MemberConstants.SURNAME)
            .withFullName(MemberConstants.FULL_NAME)
            .build();
        return Member.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName(memberName)
            .withPhone("12345")
            .withIdentifier("6789")
            .withActive(false)
            .build();
    }

    public static final Member inactive(final int index) {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withFirstName("Member " + index)
            .withLastName("Surname " + index)
            .withFullName("Member " + index + " Surname " + index)
            .build();
        return Member.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName(memberName)
            .withPhone("12345")
            .withIdentifier("6789")
            .withActive(false)
            .build();
    }

    public static final Member inactiveWithNumber(final long number) {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withFirstName(MemberConstants.NAME)
            .withLastName(MemberConstants.SURNAME)
            .withFullName(MemberConstants.FULL_NAME)
            .build();
        return Member.builder()
            .withNumber(number)
            .withName(memberName)
            .withPhone("12345")
            .withIdentifier("6789")
            .withActive(false)
            .build();
    }

    public static final Member missingName() {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withLastName(MemberConstants.SURNAME)
            .withFullName(MemberConstants.SURNAME)
            .build();
        return Member.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName(memberName)
            .withPhone("12345")
            .withIdentifier("6789")
            .withActive(false)
            .build();
    }

    public static final Member missingSurname() {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withFirstName(MemberConstants.NAME)
            .withFullName(MemberConstants.NAME + " null")
            .build();
        return Member.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName(memberName)
            .withPhone("12345")
            .withIdentifier("6789")
            .withActive(false)
            .build();
    }

    public static final Member nameChange() {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withFirstName("Member 123")
            .withLastName("Surname")
            .withFullName("Member 123 Surname")
            .build();
        return Member.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName(memberName)
            .withPhone("12345")
            .withIdentifier("6789")
            .withActive(false)
            .build();
    }

    public static final Member nameChangeActive() {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withFirstName("Member 123")
            .withLastName("Surname")
            .withFullName("Member 123 Surname")
            .build();
        return Member.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName(memberName)
            .withPhone("12345")
            .withIdentifier("6789")
            .withActive(true)
            .build();
    }

    public static final Member paddedWithWhitespaces() {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withFirstName(" " + MemberConstants.NAME + " ")
            .withLastName(" " + MemberConstants.SURNAME + " ")
            .withFullName(MemberConstants.FULL_NAME)
            .build();
        return Member.builder()
            .withNumber(MemberConstants.NUMBER)
            .withName(memberName)
            .withPhone("12345")
            .withIdentifier("6789")
            .withActive(true)
            .build();
    }

}
