
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;

public final class Members {

    public static final Member active() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new Member(PersonConstants.NUMBER, name, true);
    }

    public static final Member forNumber(final long number, final boolean active) {
        final PersonName name;

        name = new PersonName("Person " + number, "Last name " + number);
        return new Member(number * 10, name, active);
    }

    public static final Member inactive() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new Member(PersonConstants.NUMBER, name, false);
    }

}
