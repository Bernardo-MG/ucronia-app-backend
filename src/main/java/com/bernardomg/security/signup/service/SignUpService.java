
package com.bernardomg.security.signup.service;

import com.bernardomg.security.data.model.User;

public interface SignUpService {

    public User registerUser(final String username, final String email);

}
