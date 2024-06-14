
package com.bernardomg.association.security.account.test.config.factory;

import com.bernardomg.association.security.user.test.config.factory.UserConstants;
import com.bernardomg.security.account.domain.model.BasicAccount;

public final class BasicAccounts {

    public static final BasicAccount valid() {
        return BasicAccount.builder()
            .withUsername(UserConstants.USERNAME)
            .withName(UserConstants.NAME)
            .withEmail(UserConstants.EMAIL)
            .build();
    }

    private BasicAccounts() {
        super();
    }

}
