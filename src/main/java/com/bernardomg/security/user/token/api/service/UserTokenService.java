
package com.bernardomg.security.user.token.api.service;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.user.token.api.model.UserToken;

public interface UserTokenService {

    public Iterable<UserToken> getAll(final Pageable pageable);

}
