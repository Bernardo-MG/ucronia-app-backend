
package com.bernardomg.association.member.test.config.factory;

import com.bernardomg.association.member.delivery.model.MemberChange;
import com.bernardomg.association.member.delivery.model.MemberChangeName;

public final class MemberChanges {

    public static final MemberChange alternative() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .firstName(MemberConstants.ALTERNATIVE_NAME)
            .lastName(MemberConstants.ALTERNATIVE_SURNAME)
            .build();
        return MemberChange.builder()
            .name(name)
            .phone("12345 2")
            .identifier("6789 2")
            .build();
    }

    public static final MemberChange missingActive() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .firstName("Member")
            .lastName("Surname")
            .build();
        return MemberChange.builder()
            .name(name)
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberChange missingIdentifier() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .firstName("Member")
            .lastName("Surname")
            .build();
        return MemberChange.builder()
            .name(name)
            .phone("12345")
            .build();
    }

    public static final MemberChange missingName() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .lastName("Surname")
            .build();
        return MemberChange.builder()
            .name(name)
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberChange missingSurname() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .firstName("Member")
            .build();
        return MemberChange.builder()
            .name(name)
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberChange nameChange() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .firstName("Member 123")
            .lastName("Surname")
            .build();
        return MemberChange.builder()
            .name(name)
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberChange paddedWithWhitespaces() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .firstName(" " + MemberConstants.NAME + " ")
            .lastName(" " + MemberConstants.SURNAME + " ")
            .build();
        return MemberChange.builder()
            .name(name)
            .phone("12345")
            .identifier("6789")
            .build();
    }

    public static final MemberChange valid() {
        final MemberChangeName name;

        name = MemberChangeName.builder()
            .firstName(MemberConstants.NAME)
            .lastName(MemberConstants.SURNAME)
            .build();
        return MemberChange.builder()
            .name(name)
            .phone("12345")
            .identifier("6789")
            .build();
    }

}
