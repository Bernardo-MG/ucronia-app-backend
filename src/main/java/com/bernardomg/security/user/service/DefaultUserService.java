
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
import org.springframework.stereotype.Service;

import com.bernardomg.exception.InvalidIdException;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.mapper.UserMapper;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.model.request.UserQuery;
import com.bernardomg.security.user.model.request.UserUpdate;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.validation.user.CreateUserValidator;
import com.bernardomg.security.user.validation.user.DeleteUserValidator;
import com.bernardomg.security.user.validation.user.UpdateUserValidator;
import com.bernardomg.validation.Validator;

@Service
public final class DefaultUserService implements UserService {

    private static final String         CACHE_MULTIPLE = "security_users";

    private static final String         CACHE_SINGLE   = "security_user";

    private final UserMapper            mapper;

    private final UserRepository        userRepository;

    private final Validator<UserCreate> validatorCreateUser;

    private final Validator<Long>       validatorDeleteUser;

    private final Validator<UserUpdate> validatorUpdateUser;

    public DefaultUserService(final UserRepository userRepo, final UserMapper userMapper) {
        super();

        userRepository = Objects.requireNonNull(userRepo);
        mapper = Objects.requireNonNull(userMapper);

        validatorCreateUser = new CreateUserValidator(userRepo);
        validatorUpdateUser = new UpdateUserValidator(userRepo);
        validatorDeleteUser = new DeleteUserValidator();
    }

    @Override
    @PreAuthorize("hasAuthority('USER:CREATE')")
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final User create(final UserCreate user) {
        final PersistentUser entity;
        final PersistentUser created;

        validatorCreateUser.validate(user);

        entity = mapper.toEntity(user);
        if (entity.getUsername() != null) {
            entity.setUsername(entity.getUsername()
                .toLowerCase());
        }
        if (entity.getEmail() != null) {
            entity.setEmail(entity.getEmail()
                .toLowerCase());
        }

        // TODO: Handle this better, disable until it has a password
        // TODO: Should be the DB default value
        entity.setPassword("");

        entity.setExpired(false);
        entity.setLocked(false);
        entity.setCredentialsExpired(false);

        created = userRepository.save(entity);

        return mapper.toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('USER:DELETE')")
    @Caching(evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true),
            @CacheEvict(cacheNames = CACHE_SINGLE, key = "#id") })
    public final void delete(final long id) {

        if (!userRepository.existsById(id)) {
            throw new InvalidIdException(String.format("Failed delete. No user with id %s", id));
        }

        validatorDeleteUser.validate(id);
        userRepository.deleteById(id);
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
        return userRepository.findById(id)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('USER:UPDATE')")
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final User update(final long id, final UserUpdate user) {
        final PersistentUser           entity;
        final PersistentUser           created;
        final Optional<PersistentUser> old;

        if (!userRepository.existsById(id)) {
            throw new InvalidIdException(String.format("Failed update. No user with id %s", id));
        }

        validatorUpdateUser.validate(user);

        entity = mapper.toEntity(user);
        entity.setId(id);
        if (entity.getUsername() != null) {
            entity.setUsername(entity.getUsername()
                .toLowerCase());
        }
        if (entity.getEmail() != null) {
            entity.setEmail(entity.getEmail()
                .toLowerCase());
        }

        old = userRepository.findById(user.getId());
        if (old.isPresent()) {
            entity.setPassword(old.get()
                .getPassword());
        }

        // TODO: Should initialize the values in the database
        entity.setExpired(false);
        entity.setLocked(false);
        entity.setCredentialsExpired(false);

        created = userRepository.save(entity);

        return mapper.toDto(created);
    }

}
