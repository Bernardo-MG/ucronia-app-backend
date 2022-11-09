
package com.bernardomg.security.registration.service;

import com.bernardomg.security.data.model.User;

public interface UserRegistrationService {

    public User registerUser(final String username, final String email);

}
