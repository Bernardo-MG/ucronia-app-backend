
package com.bernardomg.security.user.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.exception.InvalidIdException;
import com.bernardomg.security.email.sender.SecurityMessageSender;
import com.bernardomg.security.token.exception.InvalidTokenException;
import com.bernardomg.security.token.model.ImmutableTokenStatus;
import com.bernardomg.security.token.model.TokenStatus;
import com.bernardomg.security.token.store.UserTokenStore;
import com.bernardomg.security.user.cache.UserCaches;
import com.bernardomg.security.user.exception.UserEnabledException;
import com.bernardomg.security.user.exception.UserExpiredException;
import com.bernardomg.security.user.exception.UserLockedException;
import com.bernardomg.security.user.exception.UserNotFoundException;
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

    private final UserMapper            mapper;

    /**
     * Message sender. Registering new users may require emails, or other kind of messaging.
     */
    private final SecurityMessageSender messageSender;

    /**
     * Password encoder, for validating passwords.
     */
    private final PasswordEncoder       passwordEncoder;

    /**
     * Token processor.
     */
    private final UserTokenStore        tokenStore;

    private final UserRepository        userRepository;

    private final Validator<UserCreate> validatorCreateUser;

    private final Validator<Long>       validatorDeleteUser;

    private final Validator<UserUpdate> validatorUpdateUser;

    public DefaultUserService(final UserRepository userRepo, final SecurityMessageSender mSender,
            final UserTokenStore tStore, final PasswordEncoder passEncoder, final UserMapper userMapper) {
        super();

        userRepository = Objects.requireNonNull(userRepo);
        mapper = Objects.requireNonNull(userMapper);

        tokenStore = Objects.requireNonNull(tStore);

        passwordEncoder = Objects.requireNonNull(passEncoder);

        messageSender = Objects.requireNonNull(mSender);

        validatorCreateUser = new CreateUserValidator(userRepo);
        validatorUpdateUser = new UpdateUserValidator(userRepo);
        validatorDeleteUser = new DeleteUserValidator();
    }

    @Override
    public final User activateNewUser(final String token, final String password) {
        final String         tokenUsername;
        final PersistentUser user;
        final String         encodedPassword;

        tokenStore.validate(token);

        tokenUsername = tokenStore.getUsername(token);

        log.debug("Enabling new user {}", tokenUsername);

        user = getUserByUsername(tokenUsername);

        authorizeEnableUser(user);

        user.setEnabled(true);
        user.setPasswordExpired(false);
        encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        userRepository.save(user);
        tokenStore.consumeToken(token);

        log.debug("Enabled new user {}", tokenUsername);

        return mapper.toDto(user);
    }

    @Override
    public final void delete(final long userId) {

        log.debug("Deleting user {}", userId);

        if (!userRepository.existsById(userId)) {
            throw new InvalidIdException("user", userId);
        }

        validatorDeleteUser.validate(userId);

        userRepository.deleteById(userId);
    }

    @Override
    public final Iterable<User> getAll(final UserQuery sample, final Pageable pageable) {
        final PersistentUser entity;

        log.debug("Reading users with sample {} and pagination {}", sample, pageable);

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
    public final Optional<User> getOne(final long id) {

        log.debug("Reading member with id {}", id);

        if (!userRepository.existsById(id)) {
            throw new InvalidIdException("user", id);
        }

        return userRepository.findById(id)
            .map(mapper::toDto);
    }

    @Override
    public final User registerNewUser(final UserCreate user) {
        final PersistentUser userEntity;
        final PersistentUser created;
        final String         token;

        log.debug("Registering new user {} with email {}", user.getUsername(), user.getEmail());

        validatorCreateUser.validate(user);

        userEntity = mapper.toEntity(user);

        // Trim strings
        userEntity.setName(userEntity.getName()
            .trim());
        userEntity.setUsername(userEntity.getUsername()
            .trim());
        userEntity.setEmail(userEntity.getEmail()
            .trim());

        // Remove case
        userEntity.setUsername(userEntity.getUsername()
            .toLowerCase());
        userEntity.setEmail(userEntity.getEmail()
            .toLowerCase());

        // TODO: Handle this better, disable until it has a password
        // TODO: Should be the DB default value
        userEntity.setPassword("");

        // Disabled by default
        userEntity.setEnabled(false);
        userEntity.setExpired(false);
        userEntity.setLocked(false);
        userEntity.setPasswordExpired(true);

        created = userRepository.save(userEntity);

        // Revoke previous tokens
        tokenStore.revokeExistingTokens(created.getUsername());

        // Register new token
        token = tokenStore.createToken(created.getUsername());

        // TODO: Handle through events
        messageSender.sendUserRegisteredMessage(created.getEmail(), user.getUsername(), token);

        log.debug("Registered new user {} with email {}", user.getUsername(), user.getEmail());

        return mapper.toDto(created);
    }

    @Override
    @Caching(put = { @CachePut(cacheNames = UserCaches.USER, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = UserCaches.USERS, allEntries = true) })
    public final User update(final long id, final UserUpdate user) {
        final PersistentUser           userEntity;
        final PersistentUser           created;
        final Optional<PersistentUser> oldRead;
        final PersistentUser           old;

        log.debug("Updating user with id {} using data {}", id, user);

        if (!userRepository.existsById(id)) {
            throw new InvalidIdException("user", id);
        }

        validatorUpdateUser.validate(user);

        userEntity = mapper.toEntity(user);

        // Trim strings
        userEntity.setName(userEntity.getName()
            .trim());
        userEntity.setEmail(userEntity.getEmail()
            .trim());

        // Remove case
        userEntity.setEmail(userEntity.getEmail()
            .toLowerCase());

        oldRead = userRepository.findById(user.getId());
        if (oldRead.isPresent()) {
            old = oldRead.get();

            // Can't change username by updating
            userEntity.setUsername(old.getUsername());

            // Can't change password by updating
            userEntity.setPassword(old.getPassword());

            // Can't change status by updating
            userEntity.setExpired(old.getExpired());
            userEntity.setLocked(old.getLocked());
        }

        created = userRepository.save(userEntity);

        return mapper.toDto(created);
    }

    @Override
    public final TokenStatus validateToken(final String token) {
        boolean      valid;
        final String username;

        try {
            tokenStore.validate(token);
            valid = true;
        } catch (final InvalidTokenException ex) {
            valid = false;
        }
        username = tokenStore.getUsername(token);

        return ImmutableTokenStatus.builder()
            .valid(valid)
            .username(username)
            .build();
    }

    /**
     * Authenticates the new user enabling attempt. If the user is not authenticated, then an exception is thrown.
     *
     * @param user
     *            user for which the password is changed
     */
    private final void authorizeEnableUser(final PersistentUser user) {
        if (user.getExpired()) {
            log.error("Can't enable new user. User {} is expired", user.getUsername());
            throw new UserExpiredException(user.getUsername());
        }
        if (user.getLocked()) {
            log.error("Can't enable new user. User {} is locked", user.getUsername());
            throw new UserLockedException(user.getUsername());
        }
        if (user.getEnabled()) {
            log.error("Can't enable new user. User {} is already enabled", user.getUsername());
            throw new UserEnabledException(user.getUsername());
        }
    }

    private final PersistentUser getUserByUsername(final String username) {
        final Optional<PersistentUser> user;

        user = userRepository.findOneByUsername(username);

        // Validate the user exists
        if (!user.isPresent()) {
            log.error("Couldn't enable new user {}, as it doesn't exist", username);
            throw new UserNotFoundException(username);
        }

        return user.get();
    }

}
