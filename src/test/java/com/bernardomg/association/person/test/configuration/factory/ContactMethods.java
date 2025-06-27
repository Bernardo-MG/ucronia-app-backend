
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.domain.model.ContactMethod;

public final class ContactMethods {

    public static final ContactMethod email() {
        return new ContactMethod(ContactMethodConstants.NUMBER, ContactMethodConstants.EMAIL);
    }

    public static final ContactMethod emptyName() {
        return new ContactMethod(ContactMethodConstants.NUMBER, "");
    }

}
