
package com.bernardomg.security.register.service;

import com.bernardomg.security.data.model.User;

public interface RegisterUserService {

    public User registerUser(final String username, final String email, final String user);

}
