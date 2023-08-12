
package com.bernardomg.security.user.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.bernardomg.exception.InvalidIdException;
import com.bernardomg.security.email.sender.SecurityMessageSender;
import com.bernardomg.security.token.exception.InvalidTokenException;
import com.bernardomg.security.token.exception.MissingTokenException;
import com.bernardomg.security.token.store.TokenStore;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.mapper.UserMapper;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.model.request.UserQuery;
import com.bernardomg.security.user.model.request.UserUpdate;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.validation.CreateUserValidator;
import com.bernardomg.security.user.validation.DeleteUserValidator;
import com.bernardomg.security.user.validation.UpdateUserValidator;
import com.bernardomg.validation.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultUserService implements UserService {

    private static final String         CACHE_MULTIPLE = "security_users";

    private static final String         CACHE_SINGLE   = "security_user";

    private final UserMapper            mapper;

    /**
     * Message sender. Registering new users may require emails, or other kind of messaging.
     */
    private final SecurityMessageSender messageSender;

    /**
     * Token scope for reseting passwords.
     */
    private final String                tokenScope;

    /**
     * Token processor.
     */
    private final TokenStore            tokenStore;

    private final UserRepository        userRepository;

    private final Validator<UserCreate> validatorCreateUser;

    private final Validator<Long>       validatorDeleteUser;

    private final Validator<UserUpdate> validatorUpdateUser;

    public DefaultUserService(final UserRepository userRepo, final SecurityMessageSender mSender,
            final TokenStore tStore, final UserMapper userMapper, final String scope) {
        super();

        userRepository = Objects.requireNonNull(userRepo);
        mapper = Objects.requireNonNull(userMapper);

        tokenStore = Objects.requireNonNull(tStore);
        tokenScope = Objects.requireNonNull(scope);

        messageSender = Objects.requireNonNull(mSender);

        validatorCreateUser = new CreateUserValidator(userRepo);
        validatorUpdateUser = new UpdateUserValidator(userRepo);
        validatorDeleteUser = new DeleteUserValidator();
    }

    @Override
    @PreAuthorize("hasAuthority('USER:DELETE')")
    @Caching(evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true),
            @CacheEvict(cacheNames = CACHE_SINGLE, key = "#id") })
    public final void delete(final long id) {

        if (!userRepository.existsById(id)) {
            throw new InvalidIdException("user", id);
        }

        validatorDeleteUser.validate(id);
        userRepository.deleteById(id);
    }

    @Override
    public final void enableNewUser(final String token, final String username) {
        final String tokenUsername;

        // TODO: User a token validator which takes care of the exceptions
        if (!tokenStore.exists(token, tokenScope)) {
            log.error("Token missing: {}", token);
            throw new MissingTokenException(token);
        }

        // TODO: Validate scope
        if (!tokenStore.isValid(token, tokenScope)) {
            // TODO: Throw an exception for each possible case
            log.error("Token expired: {}", token);
            throw new InvalidTokenException(token);
        }

        tokenUsername = tokenStore.getUsername(token);
        if (!Objects.equals(username, tokenUsername)) {
            log.error("The token is registered for {} but {} attempted to use it", username, tokenUsername);
            // TODO: User a better exception
            throw new InvalidTokenException(token);
        }

        log.debug("Enabling new user {}", username);

        log.debug("Enabled new user {}", username);
    }

    @Override
    @PreAuthorize("hasAuthority('USER:READ')")
    @Cacheable(cacheNames = CACHE_MULTIPLE)
    public final Iterable<User> getAll(final UserQuery sample, final Pageable pageable) {
        final PersistentUser entity;

        entity = mapper.toEntity(sample);
        if (entity.getUsername() != null) {
            entity.setUsername(entity.getUsername()
                .toLowerCase());
        }
        if (entity.getEmail() != null) {
            entity.setEmail(entity.getEmail()
                .toLowerCase());
        }

        return userRepository.findAll(Example.of(entity), pageable)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('USER:READ')")
    @Cacheable(cacheNames = CACHE_SINGLE, key = "#id")
    public final Optional<User> getOne(final long id) {

        if (!userRepository.existsById(id)) {
            throw new InvalidIdException("user", id);
        }

        return userRepository.findById(id)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('USER:CREATE')")
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final User registerNewUser(final UserCreate user) {
        final PersistentUser userEntity;
        final PersistentUser created;
        final String         token;

        log.debug("Registering new user {} with email {}", user.getUsername(), user.getEmail());

        validatorCreateUser.validate(user);

        userEntity = mapper.toEntity(user);

        handleCase(userEntity);

        // TODO: Handle this better, disable until it has a password
        // TODO: Should be the DB default value
        userEntity.setPassword("");

        // Disabled by default
        userEntity.setEnabled(false);
        userEntity.setExpired(false);
        userEntity.setLocked(false);
        userEntity.setCredentialsExpired(false);

        created = userRepository.save(userEntity);

        // Revoke previous tokens
        tokenStore.revokeExistingTokens(created.getId(), tokenScope);

        // Register new token
        token = tokenStore.createToken(created.getId(), created.getUsername(), tokenScope);

        // TODO: Handle through events
        messageSender.sendUserRegisteredMessage(created.getEmail(), token);

        log.debug("Registered new user {} with email {}", user.getUsername(), user.getEmail());

        return mapper.toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('USER:UPDATE')")
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final User update(final long id, final UserUpdate user) {
        final PersistentUser           userEntity;
        final PersistentUser           created;
        final Optional<PersistentUser> oldRead;
        final PersistentUser           old;

        if (!userRepository.existsById(id)) {
            throw new InvalidIdException("user", id);
        }

        validatorUpdateUser.validate(user);

        userEntity = mapper.toEntity(user);

        handleCase(userEntity);

        oldRead = userRepository.findById(user.getId());
        if (oldRead.isPresent()) {
            old = oldRead.get();

            // Can't change password by updating
            userEntity.setPassword(old.getPassword());

            // Can't change status by updating
            userEntity.setEnabled(old.getEnabled());
            userEntity.setExpired(old.getExpired());
            userEntity.setLocked(old.getLocked());
            userEntity.setCredentialsExpired(old.getCredentialsExpired());
        }

        created = userRepository.save(userEntity);

        return mapper.toDto(created);
    }

    @Override
    public final boolean validateToken(final String token) {
        return tokenStore.isValid(token, tokenScope);
    }

    private final void handleCase(final PersistentUser user) {
        // Name and email should be case insensitive
        // For this reason they are always stored in lower case
        if (user.getUsername() != null) {
            user.setUsername(user.getUsername()
                .toLowerCase());
        }
        if (user.getEmail() != null) {
            user.setEmail(user.getEmail()
                .toLowerCase());
        }
    }

}
