/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.security.user.token.service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.exception.InvalidIdException;
import com.bernardomg.security.user.token.model.ImmutableUserToken;
import com.bernardomg.security.user.token.model.UserToken;
import com.bernardomg.security.user.token.model.UserTokenPartial;
import com.bernardomg.security.user.token.persistence.model.PersistentUserDataToken;
import com.bernardomg.security.user.token.persistence.model.PersistentUserToken;
import com.bernardomg.security.user.token.persistence.repository.UserDataTokenRepository;
import com.bernardomg.security.user.token.persistence.repository.UserTokenRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring-based implementation of the user token service.
 * <h2>Unusable tokens</h2>
 * <p>
 * Cleaning up tokens removes all of these:
 * <p>
 * <ul>
 * <li>Consumed tokens</li>
 * <li>Revoked tokens</li>
 * <li>Expired tokens</li>
 * </ul>
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class SpringUserTokenService implements UserTokenService {

    /**
     * User data token repository. This queries a view joining user tokens with their users.
     */
    private final UserDataTokenRepository userDataTokenRepository;

    /**
     * User token repository.
     */
    private final UserTokenRepository     userTokenRepository;

    public SpringUserTokenService(final UserTokenRepository userTokenRepo,
            final UserDataTokenRepository userDataTokenRepo) {
        super();

        userTokenRepository = Objects.requireNonNull(userTokenRepo);
        userDataTokenRepository = Objects.requireNonNull(userDataTokenRepo);
    }

    @Override
    public final void cleanUpTokens() {
        final Collection<PersistentUserToken> tokens;

        // Expiration date before now
        // Revoked
        // Consumed
        tokens = userTokenRepository.findAllFinished();

        log.info("Removing {} finished tokens", tokens.size());

        userTokenRepository.deleteAll(tokens);
    }

    @Override
    public final Iterable<UserToken> getAll(final Pageable pagination) {
        return userDataTokenRepository.findAll(pagination)
            .map(this::toDto);
    }

    @Override
    public final Optional<UserToken> getOne(final long id) {
        log.debug("Reading role with id {}", id);

        // TODO: if an exception is thrown, then it makes no sense returning an optional
        // TODO: read the optional and check if it is empty
        if (!userDataTokenRepository.existsById(id)) {
            throw new InvalidIdException("userToken", id);
        }

        return userDataTokenRepository.findById(id)
            .map(this::toDto);
    }

    @Override
    public final UserToken patch(final long id, final UserTokenPartial partial) {
        final Optional<PersistentUserDataToken> read;
        final PersistentUserDataToken           toPatch;
        final PersistentUserToken               toSave;
        final PersistentUserToken               saved;

        log.debug("Patching role with id {}", id);

        // TODO: Expiration date can't be before creation date
        // TODO: Can only revoke tokens, not cancel the revoke status

        read = userDataTokenRepository.findById(id);
        if (!read.isPresent()) {
            throw new InvalidIdException("userToken", id);
        }

        toPatch = read.get();

        toSave = toEntity(toPatch);

        if (partial.getExpirationDate() != null) {
            toSave.setExpirationDate(partial.getExpirationDate());
        }
        if (partial.getRevoked() != null) {
            toSave.setRevoked(partial.getRevoked());
        }

        saved = userTokenRepository.save(toSave);

        return toDto(saved, read.get());
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

    private final UserToken toDto(final PersistentUserToken entity, final PersistentUserDataToken data) {
        return ImmutableUserToken.builder()
            .id(entity.getId())
            .username(data.getUsername())
            .name(data.getName())
            .scope(entity.getScope())
            .token(entity.getToken())
            .creationDate(entity.getCreationDate())
            .expirationDate(entity.getExpirationDate())
            .consumed(entity.isConsumed())
            .revoked(entity.isRevoked())
            .build();
    }

    private final PersistentUserToken toEntity(final PersistentUserDataToken dataToken) {
        final PersistentUserToken token;

        token = new PersistentUserToken();
        token.setId(dataToken.getId());
        token.setUserId(dataToken.getUserId());
        token.setToken(dataToken.getToken());
        token.setScope(dataToken.getScope());
        token.setCreationDate(dataToken.getCreationDate());
        token.setExpirationDate(dataToken.getExpirationDate());
        token.setConsumed(dataToken.isConsumed());
        token.setRevoked(dataToken.isRevoked());

        return token;
    }

}
