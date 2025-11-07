
package com.bernardomg.association.member.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethods;
import com.bernardomg.association.member.domain.model.Member;

public final class Members {

    public static final Member active() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Member(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE, true,
            true, List.of());
    }

    public static final Member activeNew() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Member("", ContactConstants.NUMBER, name, null, true, true, List.of());
    }

    public static final Member activeNoRenew() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Member(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE, true,
            false, List.of());
    }

    public static final Member alternativeActive() {
        final ContactName name;

        name = new ContactName(ContactConstants.ALTERNATIVE_FIRST_NAME, ContactConstants.ALTERNATIVE_LAST_NAME);
        return new Member(ContactConstants.ALTERNATIVE_IDENTIFIER, ContactConstants.ALTERNATIVE_NUMBER, name,
            ContactConstants.BIRTH_DATE, true, true, List.of());
    }

    public static final Member alternativeInactive() {
        final ContactName name;

        name = new ContactName(ContactConstants.ALTERNATIVE_FIRST_NAME, ContactConstants.ALTERNATIVE_LAST_NAME);
        return new Member(ContactConstants.ALTERNATIVE_IDENTIFIER, ContactConstants.ALTERNATIVE_NUMBER, name,
            ContactConstants.BIRTH_DATE, false, true, List.of());
    }

    public static final Member inactive() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Member(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            false, true, List.of());
    }

    public static final Member inactiveNew() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Member("", ContactConstants.NUMBER, name, null, false, false, List.of());
    }

    public static final Member inactiveNoRenew() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Member(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            false, false, List.of());
    }

    public static final Member withEmail() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new Member(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE, true,
            true, List.of(contactChannel));
    }

}
