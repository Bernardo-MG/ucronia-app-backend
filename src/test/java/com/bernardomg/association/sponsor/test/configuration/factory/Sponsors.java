
package com.bernardomg.association.sponsor.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.sponsor.domain.model.Sponsor;

public final class Sponsors {

    public static final Sponsor created() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Sponsor(1L, name, List.of());
    }

    public static final Sponsor nameChange() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Sponsor(ContactConstants.NUMBER, name, List.of());
    }

    public static final Sponsor nameChangePatch() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Sponsor(ContactConstants.NUMBER, name, List.of());
    }

    public static final Sponsor padded() {
        final ContactName name;

        name = new ContactName(" " + ContactConstants.FIRST_NAME + " ", " " + ContactConstants.LAST_NAME + " ");
        return new Sponsor(ContactConstants.NUMBER, name, List.of());
    }

    public static final Sponsor toCreate() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Sponsor(0L, name, List.of());
    }

    public static final Sponsor valid() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Sponsor(ContactConstants.NUMBER, name, List.of());
    }

}
