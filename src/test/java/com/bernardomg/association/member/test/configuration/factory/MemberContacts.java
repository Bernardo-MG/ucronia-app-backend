
package com.bernardomg.association.member.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethods;
import com.bernardomg.association.member.domain.model.MemberContact;

public final class MemberContacts {

    public static final MemberContact active() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, true, true, List.of());
    }

    public static final MemberContact activeNew() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new MemberContact("", ContactConstants.NUMBER, name, null, true, true, List.of());
    }

    public static final MemberContact activeNoRenew() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, true, false, List.of());
    }

    public static final MemberContact alternativeActive() {
        final ContactName name;

        name = new ContactName(ContactConstants.ALTERNATIVE_FIRST_NAME, ContactConstants.ALTERNATIVE_LAST_NAME);
        return new MemberContact(ContactConstants.ALTERNATIVE_IDENTIFIER, ContactConstants.ALTERNATIVE_NUMBER, name,
            ContactConstants.BIRTH_DATE, true, true, List.of());
    }

    public static final MemberContact alternativeInactive() {
        final ContactName name;

        name = new ContactName(ContactConstants.ALTERNATIVE_FIRST_NAME, ContactConstants.ALTERNATIVE_LAST_NAME);
        return new MemberContact(ContactConstants.ALTERNATIVE_IDENTIFIER, ContactConstants.ALTERNATIVE_NUMBER, name,
            ContactConstants.BIRTH_DATE, false, true, List.of());
    }

    public static final MemberContact inactive() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, false, true, List.of());
    }

    public static final MemberContact inactiveNew() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new MemberContact("", ContactConstants.NUMBER, name, null, false, false, List.of());
    }

    public static final MemberContact inactiveNoRenew() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, false, false, List.of());
    }

    public static final MemberContact nameChange() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, true, true, List.of());
    }

    public static final MemberContact nameChangePatch() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new MemberContact(null, ContactConstants.NUMBER, name, null, true, true, List.of());
    }

    public static final MemberContact noIdentifier() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new MemberContact("", ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE, true, true, List.of());
    }

    public static final MemberContact padded() {
        final ContactName name;

        name = new ContactName(" " + ContactConstants.FIRST_NAME + " ", " " + ContactConstants.LAST_NAME + " ");
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, true, true, List.of());
    }

    public static final MemberContact toCreate() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new MemberContact(ContactConstants.IDENTIFIER, -1L, name, ContactConstants.BIRTH_DATE, true, true,
            List.of());
    }

    public static final MemberContact toCreateNoIdentifier() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new MemberContact("", -1L, name, ContactConstants.BIRTH_DATE, true, true, List.of());
    }

    public static final MemberContact withEmail() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, true, true, List.of(contactChannel));
    }

}
