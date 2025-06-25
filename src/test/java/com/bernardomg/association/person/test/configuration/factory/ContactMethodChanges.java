
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.adapter.outbound.rest.model.ContactMethodChange;

public final class ContactMethodChanges {

    public static final ContactMethodChange valid() {
        return new ContactMethodChange(ContactMethodConstants.NAME);
    }

}
