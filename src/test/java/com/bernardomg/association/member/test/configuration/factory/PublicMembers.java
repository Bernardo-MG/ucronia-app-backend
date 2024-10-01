
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
        return new PublicMember(PersonConstants.NUMBER, name, true);
    }

    public static final PublicMember forNumber(final long number, final boolean active) {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName("Person " + number)
            .withLastName("Last name " + number)
            .build();
        return new PublicMember(number * 10, name, active);
    }

    public static final PublicMember inactive() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(PersonConstants.FIRST_NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .build();
        return new PublicMember(PersonConstants.NUMBER, name, false);
    }

}
