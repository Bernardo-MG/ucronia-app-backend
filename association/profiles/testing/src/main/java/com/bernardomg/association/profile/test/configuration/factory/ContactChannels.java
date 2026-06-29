
package com.bernardomg.association.profile.test.configuration.factory;

import com.bernardomg.association.profile.domain.model.ContactChannel;

public final class ContactChannels {

    public static final ContactChannel withEmail() {
        return new ContactChannel(ContactMethods.email(), ProfileConstants.EMAIL);
    }

    public static final ContactChannel withPhone() {
        return new ContactChannel(ContactMethods.phone(), ProfileConstants.PHONE);
    }

    private ContactChannels() {
        super();
    }

}
