
package com.bernardomg.security.service;

import com.bernardomg.security.model.User;

public interface RegisterUserService {

    public User registerUser(final String username, final String email, final String user);

}
