
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.member.domain.model.Member;

public final class Members {

    public static final Member active() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Member(ContactConstants.NUMBER, name, true, true, ContactConstants.COMMENTS);
    }

    public static final Member activeNoRenew() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Member(ContactConstants.NUMBER, name, true, false, ContactConstants.COMMENTS);
    }

    public static final Member alternativeActive() {
        final ContactName name;

        name = new ContactName(ContactConstants.ALTERNATIVE_FIRST_NAME, ContactConstants.ALTERNATIVE_LAST_NAME);
        return new Member(ContactConstants.ALTERNATIVE_NUMBER, name, true, true, ContactConstants.COMMENTS);
    }

    public static final Member alternativeInactive() {
        final ContactName name;

        name = new ContactName(ContactConstants.ALTERNATIVE_FIRST_NAME, ContactConstants.ALTERNATIVE_LAST_NAME);
        return new Member(ContactConstants.ALTERNATIVE_NUMBER, name, false, true, ContactConstants.COMMENTS);
    }

    public static final Member created() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Member(1L, name, true, true, ContactConstants.COMMENTS);
    }

    public static final Member forNumber(final long number) {
        final ContactName name;

        name = new ContactName("Contact " + number, "Last name " + number);
        return new Member(number * 10, name, true, true, ContactConstants.COMMENTS);
    }

    public static final Member inactive() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Member(ContactConstants.NUMBER, name, false, true, ContactConstants.COMMENTS);
    }

    public static final Member inactiveNoRenew() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Member(ContactConstants.NUMBER, name, false, false, ContactConstants.COMMENTS);
    }

    public static final Member nameChange() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Member(ContactConstants.NUMBER, name, true, true, ContactConstants.COMMENTS);
    }

    public static final Member nameChangePatch() {
        final ContactName name;

        name = new ContactName("Contact 123", "Last name");
        return new Member(ContactConstants.NUMBER, name, null, null, ContactConstants.COMMENTS);
    }

    public static final Member padded() {
        final ContactName name;

        name = new ContactName(" " + ContactConstants.FIRST_NAME + " ", " " + ContactConstants.LAST_NAME + " ");
        return new Member(ContactConstants.NUMBER, name, true, true, ContactConstants.COMMENTS);
    }

    public static final Member toCreate() {
        final ContactName name;

        name = new ContactName(ContactConstants.FIRST_NAME, ContactConstants.LAST_NAME);
        return new Member(0L, name, true, true, ContactConstants.COMMENTS);
    }

}
