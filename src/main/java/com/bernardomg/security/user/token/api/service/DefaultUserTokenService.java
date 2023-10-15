
package com.bernardomg.security.user.token.api.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.token.api.model.ImmutableUserToken;
import com.bernardomg.security.user.token.api.model.UserToken;
import com.bernardomg.security.user.token.persistence.model.PersistentUserToken;
import com.bernardomg.security.user.token.persistence.repository.UserTokenRepository;

public final class DefaultUserTokenService implements UserTokenService {

    final UserRepository      userRepository;

    final UserTokenRepository userTokenRepository;

    public DefaultUserTokenService(final UserTokenRepository userTokenRepo, final UserRepository userRepo) {
        super();

        userTokenRepository = Objects.requireNonNull(userTokenRepo);
        userRepository = Objects.requireNonNull(userRepo);
    }

    @Override
    public final Iterable<UserToken> getAll(final Pageable pageable) {
        // TODO: Test this
        return userTokenRepository.findAll(pageable)
            .map(this::toDto);
    }

    private final UserToken toDto(final PersistentUserToken entity) {
        final Optional<PersistentUser> readUser;
        final String                   username;
        // TODO: Optimize to avoid multiple repeated queries
        readUser = userRepository.findById(entity.getUserId());

        if (readUser.isPresent()) {
            username = readUser.get()
                .getUsername();
        } else {
            username = "";
        }

        return ImmutableUserToken.builder()
            .username(username)
            .scope(entity.getScope())
            .build();
    }

}
