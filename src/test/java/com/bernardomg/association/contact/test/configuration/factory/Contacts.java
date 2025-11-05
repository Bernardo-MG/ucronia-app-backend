
package com.bernardomg.association.contact.test.configuration.factory;

import java.util.List;
import java.util.Optional;

import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.Contact.Membership;
import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.contact.domain.model.ContactName;

public final class Contacts {

    public static final Contact alternative() {
        final ContactName name;

        name = new ContactName(ContactConstants.ALTERNATIVE_FIRST_NAME, ContactConstants.ALTERNATIVE_LAST_NAME);
        return new Contact(ContactConstants.ALTERNATIVE_IDENTIFIER, ContactConstants.ALTERNATIVE_NUMBER, name,
            ContactConstants.BIRTH_DATE, Optional.empty(), List.of());
    }

    public static final Contact alternativeMembershipActive() {
        final ContactName name;
        final Membership  membership;

        name = new ContactName(ContactConstants.ALTERNATIVE_FIRST_NAME, ContactConstants.ALTERNATIVE_LAST_NAME);
        membership = new Membership(true, true);
        return new Contact(ContactConstants.ALTERNATIVE_IDENTIFIER, ContactConstants.ALTERNATIVE_NUMBER, name,
            ContactConstants.BIRTH_DATE, Optional.of(membership), List.of());
    }

    public static final Contact alternativeMembershipInactive() {
        final ContactName name;
        final Membership  membership;

        name = new ContactName(ContactConstants.ALTERNATIVE_FIRST_NAME, ContactConstants.ALTERNATIVE_LAST_NAME);
        membership = new Membership(false, true);
        return new Contact(ContactConstants.ALTERNATIVE_IDENTIFIER, ContactConstants.ALTERNATIVE_NUMBER, name,
            ContactConstants.BIRTH_DATE, Optional.of(membership), List.of());
    }

    public static final Contact emptyName() {
        final ContactName name;

        name = new ContactName(" ", " ");
        return new Contact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            Optional.empty(), List.of());
    }

    public static final Contact membershipActive() {
        final ContactName name;
        final Membership  membership;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        membership = new Membership(true, true);
        return new Contact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            Optional.of(membership), List.of());
    }

    public static final Contact membershipActiveNew() {
        final ContactName name;
        final Membership  membership;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        membership = new Membership(true, true);
        return new Contact("", ContactConstants.NUMBER, name, null, Optional.of(membership), List.of());
    }

    public static final Contact membershipActiveNoRenew() {
        final ContactName name;
        final Membership  membership;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        membership = new Membership(true, false);
        return new Contact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            Optional.of(membership), List.of());
    }

    public static final Contact nameChange() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Contact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            Optional.empty(), List.of());
    }

    public static final Contact nameChangePatch() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Contact(null, ContactConstants.NUMBER, name, null, Optional.empty(), List.of());
    }

    public static final Contact noIdentifier() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Contact("", ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE, Optional.empty(), List.of());
    }

    public static final Contact padded() {
        final ContactName name;

        name = new ContactName(" " + ContactConstants.FIRST_NAME + " ", " " + ContactConstants.LAST_NAME + " ");
        return new Contact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            Optional.empty(), List.of());
    }

    public static final Contact toCreate() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Contact(ContactConstants.IDENTIFIER, -1L, name, ContactConstants.BIRTH_DATE, Optional.empty(),
            List.of());
    }

    public static final Contact toCreateNoIdentifier() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Contact("", -1L, name, ContactConstants.BIRTH_DATE, Optional.empty(), List.of());
    }

    public static final Contact valid() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Contact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            Optional.empty(), List.of());
    }

    public static final Contact validNew() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Contact("", ContactConstants.NUMBER, name, null, Optional.empty(), List.of());
    }

    public static final Contact withEmail() {
        final ContactName    name;
        final Membership     membership;
        final ContactChannel personContact;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        membership = new Membership(true, true);
        contactMethod = ContactMethods.email();
        personContact = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new Contact(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            Optional.of(membership), List.of(personContact));
    }

}
