
package com.bernardomg.association.security.account.test.configuration.factory;

import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.security.account.domain.model.BasicAccount;

public final class BasicAccounts {

    public static final BasicAccount valid() {
        return new BasicAccount(UserConstants.EMAIL, UserConstants.USERNAME, UserConstants.NAME);
    }

    private BasicAccounts() {
        super();
    }

}
