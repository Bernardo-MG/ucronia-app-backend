
package com.bernardomg.security.password.change.service;

import com.bernardomg.security.password.change.model.PasswordChangeStatus;

public interface PasswordChangeService {

    public PasswordChangeStatus changePassword(final String currentPassword, final String password);

}
