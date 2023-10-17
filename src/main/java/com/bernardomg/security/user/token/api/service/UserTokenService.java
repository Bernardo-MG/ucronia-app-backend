
package com.bernardomg.security.user.token.api.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.user.token.api.model.UserToken;

public interface UserTokenService {

    public Iterable<UserToken> getAll(final Pageable pageable);

    public Optional<UserToken> getOne(final long id);

}
