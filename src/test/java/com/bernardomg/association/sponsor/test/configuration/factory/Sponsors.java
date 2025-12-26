
package com.bernardomg.association.sponsor.test.configuration.factory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethods;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntityConstants;
import com.bernardomg.association.sponsor.domain.model.Sponsor;

public final class Sponsors {

    public static final Sponsor created() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new Sponsor(ContactConstants.IDENTIFIER, 1L, name, ContactConstants.BIRTH_DATE, List.of(contactChannel),
            List.of(SponsorConstants.YEAR), ContactConstants.COMMENTS, Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor forNumber(final long number) {
        final ContactName name;

        name = new ContactName("Contact " + number, "Last name " + number);
        return new Sponsor(Objects.toString(number * 10), number * 10, name, ContactConstants.BIRTH_DATE, List.of(),
            List.of(SponsorConstants.YEAR), ContactConstants.COMMENTS, Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor nameChange() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Sponsor(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(), List.of(SponsorConstants.YEAR), ContactConstants.COMMENTS,
            Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor nameChangePatch() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Sponsor(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(), List.of(SponsorConstants.YEAR), ContactConstants.COMMENTS,
            Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor noContactChannel() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Sponsor(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(), List.of(SponsorConstants.YEAR), ContactConstants.COMMENTS,
            Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor padded() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(" " + ContactConstants.FIRST_NAME + " ", " " + ContactConstants.LAST_NAME + " ");
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new Sponsor(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(contactChannel), List.of(SponsorConstants.YEAR), ContactConstants.COMMENTS,
            Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor toConvert() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Sponsor(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(), List.of(), ContactConstants.COMMENTS, Set.of());
    }

    public static final Sponsor toCreate() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new Sponsor(ContactConstants.IDENTIFIER, 0L, name, ContactConstants.BIRTH_DATE, List.of(contactChannel),
            List.of(SponsorConstants.YEAR), ContactConstants.COMMENTS, Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor valid() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new Sponsor(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(contactChannel), List.of(SponsorConstants.YEAR), ContactConstants.COMMENTS,
            Set.of(SponsorEntityConstants.CONTACT_TYPE));
    }

    public static final Sponsor withoutType() {
        final ContactName    name;
        final ContactChannel contactChannel;
        final ContactMethod  contactMethod;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        contactMethod = ContactMethods.email();
        contactChannel = new ContactChannel(contactMethod, ContactConstants.EMAIL);
        return new Sponsor(ContactConstants.IDENTIFIER, ContactConstants.NUMBER, name, ContactConstants.BIRTH_DATE,
            List.of(contactChannel), List.of(SponsorConstants.YEAR), ContactConstants.COMMENTS, Set.of());
    }

}
