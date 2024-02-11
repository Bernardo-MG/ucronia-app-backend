
package com.bernardomg.association.member.test.config.factory;

import com.bernardomg.association.member.domain.model.MemberChange;
import com.bernardomg.association.member.domain.model.MemberChangeName;

public final class MemberChanges {

    public static final MemberChange alternative() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .withFirstName(MemberConstants.ALTERNATIVE_NAME)
            .withLastName(MemberConstants.ALTERNATIVE_SURNAME)
            .build();
        return MemberChange.builder()
            .withName(name)
            .withPhone("12345 2")
            .withIdentifier("6789 2")
            .build();
    }

    public static final MemberChange missingActive() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .withFirstName("Member")
            .withLastName("Surname")
            .build();
        return MemberChange.builder()
            .withName(name)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final MemberChange missingIdentifier() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .withFirstName("Member")
            .withLastName("Surname")
            .build();
        return MemberChange.builder()
            .withName(name)
            .withPhone("12345")
            .build();
    }

    public static final MemberChange missingName() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .withLastName("Surname")
            .build();
        return MemberChange.builder()
            .withName(name)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final MemberChange missingSurname() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .withFirstName("Member")
            .build();
        return MemberChange.builder()
            .withName(name)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final MemberChange nameChange() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .withFirstName("Member 123")
            .withLastName("Surname")
            .build();
        return MemberChange.builder()
            .withName(name)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final MemberChange paddedWithWhitespaces() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .withFirstName(" " + MemberConstants.NAME + " ")
            .withLastName(" " + MemberConstants.SURNAME + " ")
            .build();
        return MemberChange.builder()
            .withName(name)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final MemberChange valid() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .withFirstName(MemberConstants.NAME)
            .withLastName(MemberConstants.SURNAME)
            .build();
        return MemberChange.builder()
            .withName(name)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

}
