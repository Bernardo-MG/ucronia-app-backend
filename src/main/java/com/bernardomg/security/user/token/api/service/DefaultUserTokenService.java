
package com.bernardomg.security.user.token.api.service;

import java.util.Objects;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.token.api.model.ImmutableUserToken;
import com.bernardomg.security.user.token.api.model.UserToken;
import com.bernardomg.security.user.token.persistence.model.PersistentUserDataToken;
import com.bernardomg.security.user.token.persistence.repository.UserDataTokenRepository;

public final class DefaultUserTokenService implements UserTokenService {

    final UserDataTokenRepository userDataTokenRepository;

    final UserRepository          userRepository;

    public DefaultUserTokenService(final UserDataTokenRepository userDataTokenRepo, final UserRepository userRepo) {
        super();

        userDataTokenRepository = Objects.requireNonNull(userDataTokenRepo);
        userRepository = Objects.requireNonNull(userRepo);
    }

    @Override
    public final Iterable<UserToken> getAll(final Pageable pageable) {
        return userDataTokenRepository.findAll(pageable)
            .map(this::toDto);
    }

    private final UserToken toDto(final PersistentUserDataToken entity) {
        return ImmutableUserToken.builder()
            .id(entity.getId())
            .username(entity.getUsername())
            .name(entity.getName())
            .scope(entity.getScope())
            .token(entity.getToken())
            .creationDate(entity.getCreationDate())
            .expirationDate(entity.getExpirationDate())
            .consumed(entity.isConsumed())
            .revoked(entity.isRevoked())
            .build();
    }

}
