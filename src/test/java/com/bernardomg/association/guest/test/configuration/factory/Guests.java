
package com.bernardomg.association.guest.test.configuration.factory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethods;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntityConstants;
import com.bernardomg.association.guest.domain.model.Guest;

public final class Guests {

    public static final Guest created() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new Guest(ContactConstants.IDENTIFIER, 1L, name, ContactConstants.BIRTH_DATE, List.of(contactChannel),
            List.of(GuestConstants.DATE), ContactConstants.COMMENTS, Set.of(GuestEntityConstants.CONTACT_TYPE));
    }

    public static final Guest forNumber(final long number) {
        final ContactName name;

        name = new ContactName("Contact " + number, "Last name " + number);
        return new Guest(Objects.toString(number * 10), number * 10, name, ContactConstants.BIRTH_DATE, List.of(),
            List.of(GuestConstants.DATE), ContactConstants.COMMENTS, Set.of(GuestEntityConstants.CONTACT_TYPE));
    }

    public static final Guest nameChange() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Guest(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(), List.of(GuestConstants.DATE), ContactConstants.COMMENTS,
            Set.of(GuestEntityConstants.CONTACT_TYPE));
    }

    public static final Guest nameChangePatch() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Guest(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(), List.of(GuestConstants.DATE), ContactConstants.COMMENTS,
            Set.of(GuestEntityConstants.CONTACT_TYPE));
    }

    public static final Guest noContactChannel() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Guest(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(), List.of(GuestConstants.DATE), ContactConstants.COMMENTS,
            Set.of(GuestEntityConstants.CONTACT_TYPE));
    }

    public static final Guest noGames() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Guest(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(), List.of(), ContactConstants.COMMENTS, Set.of(GuestEntityConstants.CONTACT_TYPE));
    }

    public static final Guest padded() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(" " + ContactConstants.FIRST_NAME + " ", " " + ContactConstants.LAST_NAME + " ");
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new Guest(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(contactChannel), List.of(GuestConstants.DATE), ContactConstants.COMMENTS,
            Set.of(GuestEntityConstants.CONTACT_TYPE));
    }

    public static final Guest toCreate() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new Guest(ContactConstants.IDENTIFIER, 0L, name, ContactConstants.BIRTH_DATE, List.of(contactChannel),
            List.of(GuestConstants.DATE), ContactConstants.COMMENTS, Set.of(GuestEntityConstants.CONTACT_TYPE));
    }

    public static final Guest valid() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new Guest(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(contactChannel), List.of(GuestConstants.DATE), ContactConstants.COMMENTS,
            Set.of(GuestEntityConstants.CONTACT_TYPE));
    }

}
