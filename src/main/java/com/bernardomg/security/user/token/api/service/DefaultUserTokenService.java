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

package com.bernardomg.security.user.token.api.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.exception.InvalidIdException;
import com.bernardomg.security.user.token.api.model.ImmutableUserToken;
import com.bernardomg.security.user.token.api.model.UserToken;
import com.bernardomg.security.user.token.api.model.UserTokenPartial;
import com.bernardomg.security.user.token.persistence.model.PersistentUserDataToken;
import com.bernardomg.security.user.token.persistence.model.PersistentUserToken;
import com.bernardomg.security.user.token.persistence.repository.UserDataTokenRepository;
import com.bernardomg.security.user.token.persistence.repository.UserTokenRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultUserTokenService implements UserTokenService {

    private final UserDataTokenRepository userDataTokenRepository;

    private final UserTokenRepository     userTokenRepository;

    public DefaultUserTokenService(final UserTokenRepository userTokenRepo,
            final UserDataTokenRepository userDataTokenRepo) {
        super();

        userTokenRepository = Objects.requireNonNull(userTokenRepo);
        userDataTokenRepository = Objects.requireNonNull(userDataTokenRepo);
    }

    @Override
    public final Iterable<UserToken> getAll(final Pageable pageable) {
        return userDataTokenRepository.findAll(pageable)
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
    public final UserToken patch(final long id, final UserTokenPartial request) {
        final Optional<PersistentUserDataToken> read;
        final PersistentUserDataToken           patched;
        final PersistentUserToken               toSave;
        final PersistentUserToken               saved;

        log.debug("Patching role with id {}", id);

        // TODO: if an exception is thrown, then it makes no sense returning an optional
        // TODO: read the optional and check if it is empty
        if (!userDataTokenRepository.existsById(id)) {
            throw new InvalidIdException("userToken", id);
        }

        read = userDataTokenRepository.findById(id);
        if (!read.isPresent()) {
            throw new InvalidIdException("userToken", id);
        }

        patched = read.get();

        toSave = toEntity(patched);

        if (request.getExpirationDate() != null) {
            toSave.setExpirationDate(request.getExpirationDate());
        }
        if (request.getConsumed() != null) {
            toSave.setConsumed(request.getConsumed());
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
