
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;

public final class PublicMembers {

    public static final PublicMember active() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(PersonConstants.FIRST_NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .build();
        return PublicMember.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withActive(true)
            .build();
    }

    public static final PublicMember forNumber(final long number, final boolean active) {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName("Person " + number)
            .withLastName("Last name " + number)
            .build();
        return PublicMember.builder()
            .withNumber(number * 10)
            .withName(name)
            .withActive(active)
            .build();
    }

    public static final PublicMember inactive() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(PersonConstants.FIRST_NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .build();
        return PublicMember.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withActive(false)
            .build();
    }

}
