
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.domain.model.ContactMode;

public final class ContactModes {

    public static final ContactMode valid() {
        return new ContactMode(ContactModeConstants.NUMBER, ContactModeConstants.NAME);
    }

}
