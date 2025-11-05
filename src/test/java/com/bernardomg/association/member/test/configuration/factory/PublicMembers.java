
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.member.domain.model.PublicMember;

public final class PublicMembers {

    public static final PublicMember forNumber(final long number) {
        final ContactName name;

        name = new ContactName("Contact " + number, "Last name " + number);
        return new PublicMember(number * 10, name);
    }

    public static final PublicMember valid() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new PublicMember(ContactConstants.NUMBER, name);
    }

}
