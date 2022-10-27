
package com.bernardomg.security.service;

import org.springframework.stereotype.Service;

import com.bernardomg.security.model.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultRegisterUserService implements RegisterUserService {

    @Override
    public final User registerUser(final String username, final String email, final String user) {
        // TODO Auto-generated method stub
        return null;
    }

}
