
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.adapter.outbound.rest.model.ContactMethodCreation;

public final class ContactMethodCreations {

    public static final ContactMethodCreation valid() {
        return new ContactMethodCreation(ContactMethodConstants.EMAIL);
    }

}
