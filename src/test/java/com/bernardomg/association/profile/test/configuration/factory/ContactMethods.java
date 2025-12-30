
package com.bernardomg.association.profile.test.configuration.factory;

import com.bernardomg.association.profile.domain.model.ContactMethod;

public final class ContactMethods {

    public static final ContactMethod email() {
        return new ContactMethod(ContactMethodConstants.NUMBER, ContactMethodConstants.EMAIL);
    }

    public static final ContactMethod emptyName() {
        return new ContactMethod(ContactMethodConstants.NUMBER, "");
    }

    public static final ContactMethod phone() {
        return new ContactMethod(ContactMethodConstants.ALTERNATIVE_NUMBER, ContactMethodConstants.PHONE);
    }

}
