
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;

public final class PublicMembers {

    public static final PublicMember active() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new PublicMember(PersonConstants.NUMBER, name, true);
    }

    public static final PublicMember forNumber(final long number, final boolean active) {
        final PersonName name;

        name = new PersonName("Person " + number, "Last name " + number);
        return new PublicMember(number * 10, name, active);
    }

    public static final PublicMember inactive() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new PublicMember(PersonConstants.NUMBER, name, false);
    }

}
