
package com.bernardomg.association.security.account.test.configuration.factory;

import com.bernardomg.association.contact.test.configuration.factory.Contacts;
import com.bernardomg.association.security.account.domain.model.ContactAccount;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;

public final class ContactAccounts {

    public static final ContactAccount noContact() {
        return new ContactAccount(UserConstants.EMAIL, UserConstants.USERNAME, UserConstants.NAME, null);
    }

    public static final ContactAccount valid() {
        return new ContactAccount(UserConstants.EMAIL, UserConstants.USERNAME, UserConstants.NAME, Contacts.valid());
    }

    private ContactAccounts() {
        super();
    }

}
