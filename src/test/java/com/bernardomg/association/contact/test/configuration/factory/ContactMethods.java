
package com.bernardomg.association.contact.test.configuration.factory;

import com.bernardomg.association.contact.domain.model.ContactMethod;

public final class ContactMethods {

    public static final ContactMethod email() {
        return new ContactMethod(ContactMethodConstants.NUMBER, ContactMethodConstants.EMAIL);
    }

    public static final ContactMethod emptyName() {
        return new ContactMethod(ContactMethodConstants.NUMBER, "");
    }

}
