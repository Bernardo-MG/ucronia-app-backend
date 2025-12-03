
package com.bernardomg.association.contact.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.contact.domain.model.ContactName;

public final class Contacts {

    public static final Contact alternative() {
        final ContactName name;

        name = new ContactName(ContactConstants.ALTERNATIVE_FIRST_NAME, ContactConstants.ALTERNATIVE_LAST_NAME);
        return new Contact(ContactConstants.ALTERNATIVE_IDENTIFIER, ContactConstants.ALTERNATIVE_NUMBER, name,
            ContactConstants.BIRTH_DATE, List.of());
    }

    public static final Contact emptyName() {
        final ContactName name;

        name = new ContactName(" ", " ");
        return new Contact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of());
    }

    public static final Contact nameChange() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Contact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of());
    }

    public static final Contact nameChangePatch() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Contact(null, ContactConstants.NUMBER, name, null, List.of());
    }

    public static final Contact noIdentifier() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Contact("", ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE, List.of());
    }

    public static final Contact padded() {
        final ContactName name;

        name = new ContactName(" " + ContactConstants.FIRST_NAME + " ", " " + ContactConstants.LAST_NAME + " ");
        return new Contact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of());
    }

    public static final Contact toCreate() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Contact(ContactConstants.IDENTIFIER, -1L, name, ContactConstants.BIRTH_DATE, List.of());
    }

    public static final Contact toCreateNoIdentifier() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Contact("", -1L, name, ContactConstants.BIRTH_DATE, List.of());
    }

    public static final Contact valid() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Contact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of());
    }

    public static final Contact validNew() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Contact("", ContactConstants.NUMBER, name, null, List.of());
    }

    public static final Contact withEmail() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new Contact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(contactChannel));
    }

}
