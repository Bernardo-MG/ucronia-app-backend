
package com.bernardomg.association.guest.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.guest.domain.model.Guest;

public final class Guests {

    public static final Guest created() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Guest(1L, name, List.of(GuestConstants.DATE));
    }

    public static final Guest forNumber(final long number) {
        final ContactName name;

        name = new ContactName("Contact " + number, "Last name " + number);
        return new Guest(number * 10, name, List.of(GuestConstants.DATE));
    }

    public static final Guest nameChange() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Guest(ContactConstants.NUMBER, name, List.of(GuestConstants.DATE));
    }

    public static final Guest nameChangePatch() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Guest(ContactConstants.NUMBER, name, List.of(GuestConstants.DATE));
    }

    public static final Guest noYears() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Guest(ContactConstants.NUMBER, name, List.of());
    }

    public static final Guest padded() {
        final ContactName name;

        name = new ContactName(" " + ContactConstants.FIRST_NAME + " ", " " + ContactConstants.LAST_NAME + " ");
        return new Guest(ContactConstants.NUMBER, name, List.of(GuestConstants.DATE));
    }

    public static final Guest toCreate() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Guest(0L, name, List.of(GuestConstants.DATE));
    }

    public static final Guest valid() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Guest(ContactConstants.NUMBER, name, List.of(GuestConstants.DATE));
    }

}
