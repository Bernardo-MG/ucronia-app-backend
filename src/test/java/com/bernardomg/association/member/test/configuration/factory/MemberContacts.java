
package com.bernardomg.association.member.test.configuration.factory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethods;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.domain.model.MemberContact;

public final class MemberContacts {

    public static final MemberContact active() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, List.of(contactChannel), ContactConstants.COMMENTS, true, true,
            Set.of(MemberEntityConstants.CONTACT_TYPE));
    }

    public static final MemberContact created() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new MemberContact(ContactConstants.IDENTIFIER, 1L, name, ContactConstants.BIRTH_DATE,
            List.of(contactChannel), ContactConstants.COMMENTS, true, true, Set.of(MemberEntityConstants.CONTACT_TYPE));
    }

    public static final MemberContact forNumber(final long number) {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName("Contact " + number, "Last name " + number);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new MemberContact(Objects.toString(number * 10), number * 10, name, ContactConstants.BIRTH_DATE,
            List.of(contactChannel), ContactConstants.COMMENTS, true, true, Set.of(MemberEntityConstants.CONTACT_TYPE));
    }

    public static final MemberContact inactive() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, List.of(contactChannel), ContactConstants.COMMENTS, false, false,
            Set.of(MemberEntityConstants.CONTACT_TYPE));
    }

    public static final MemberContact nameChange() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, List.of(), ContactConstants.COMMENTS, true, true,
            Set.of(MemberEntityConstants.CONTACT_TYPE));
    }

    public static final MemberContact nameChangePatch() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, List.of(), ContactConstants.COMMENTS, true, true,
            Set.of(MemberEntityConstants.CONTACT_TYPE));
    }

    public static final MemberContact noContactChannel() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, List.of(), ContactConstants.COMMENTS, true, true,
            Set.of(MemberEntityConstants.CONTACT_TYPE));
    }

    public static final MemberContact noGames() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, List.of(), ContactConstants.COMMENTS, true, true,
            Set.of(MemberEntityConstants.CONTACT_TYPE));
    }

    public static final MemberContact padded() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(" " + ContactConstants.FIRST_NAME + " ", " " + ContactConstants.LAST_NAME + " ");
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new MemberContact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name,
            ContactConstants.BIRTH_DATE, List.of(contactChannel), ContactConstants.COMMENTS, true, true,
            Set.of(MemberEntityConstants.CONTACT_TYPE));
    }

    public static final MemberContact toCreate() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new MemberContact(ContactConstants.IDENTIFIER, 0L, name, ContactConstants.BIRTH_DATE,
            List.of(contactChannel), ContactConstants.COMMENTS, true, true, Set.of(MemberEntityConstants.CONTACT_TYPE));
    }

}
