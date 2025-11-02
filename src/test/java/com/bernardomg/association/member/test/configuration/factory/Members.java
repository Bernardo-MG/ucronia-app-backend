
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.person.domain.model.ContactName;
import com.bernardomg.association.person.test.configuration.factory.ContactConstants;

public final class Members {

    public static final Member forNumber(final long number) {
        final ContactName name;

        name = new ContactName("Contact " + number, "Last name " + number);
        return new Member(number * 10, name);
    }

    public static final Member valid() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Member(ContactConstants.NUMBER, name);
    }

}
