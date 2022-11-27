
package com.bernardomg.security.data.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.data.model.DtoUser;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.model.PersistentUserRoles;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.data.persistence.repository.UserRolesRepository;
import com.bernardomg.security.validation.EmailValidationRule;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.failure.Failure;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public final class DefaultUserService implements UserService {

    /**
     * Email validation rule. To check the email fits into the valid email pattern.
     */
    private final ValidationRule<String> emailValidationRule = new EmailValidationRule();

    private final UserRepository         repository;

    private final UserRolesRepository    userRolesRepository;

    @Override
    public final Boolean addRole(final Long id, final Long role) {
        final PersistentUserRoles      relationship;
        final DtoUser                  user;
        final Collection<FieldFailure> failures;

        failures = validateUserRoleChange(id);
        failures.addAll(validateAddUserRole(role));

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        user = new DtoUser();
        user.setId(id);

        relationship = getRelationships(id, role);

        // Persist relationship
        userRolesRepository.save(relationship);

        return true;
    }

    @Override
    public final User create(final User user) {
        final PersistentUser           entity;
        final PersistentUser           created;
        final Collection<FieldFailure> failures;

        failures = validateCreation(user);

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        entity = toEntity(user);
        entity.setId(null);
        entity.setPassword("");

        created = repository.save(entity);

        return toDto(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        final DtoUser                  user;
        final Collection<FieldFailure> failures;

        failures = validateDelete(id);

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        user = new DtoUser();
        user.setId(id);

        repository.deleteById(id);

        return true;
    }

    @Override
    public final Iterable<? extends User> getAll(final User sample, final Pageable pageable) {
        final PersistentUser entity;

        entity = toEntity(sample);

        return repository.findAll(Example.of(entity), pageable)
            .map(this::toDto);
    }

    @Override
    public final Optional<? extends User> getOne(final Long id) {
        return repository.findById(id)
            .map(this::toDto);
    }

    @Override
    public final Iterable<Role> getRoles(final Long id, final Pageable pageable) {
        return repository.findAllRoles(id, pageable);
    }

    @Override
    public final Boolean removeRole(final Long id, final Long role) {
        final PersistentUserRoles      relationship;
        final DtoUser                  user;
        final Collection<FieldFailure> failures;

        failures = validateUserRoleChange(id);
        failures.addAll(validateAddUserRole(role));

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        user = new DtoUser();
        user.setId(id);

        relationship = getRelationships(id, role);

        // Persist relationship
        userRolesRepository.delete(relationship);

        return true;
    }

    @Override
    public final User update(final User user) {
        final PersistentUser           entity;
        final PersistentUser           created;
        final PersistentUser           old;
        final Collection<FieldFailure> failures;

        failures = validateUpdate(user);

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        entity = toEntity(user);
        entity.setPassword("");

        old = repository.findById(user.getId())
            .get();
        entity.setPassword(old.getPassword());

        created = repository.save(entity);

        return toDto(created);
    }

    private final PersistentUserRoles getRelationships(final Long user, final Long role) {
        final PersistentUserRoles relationship;

        relationship = new PersistentUserRoles();
        relationship.setUserId(user);
        relationship.setRoleId(role);

        return relationship;
    }

    private final User toDto(final PersistentUser entity) {
        final DtoUser data;

        data = new DtoUser();
        data.setId(entity.getId());
        data.setUsername(entity.getUsername());
        data.setName(entity.getName());
        data.setEmail(entity.getEmail());
        data.setCredentialsExpired(entity.getCredentialsExpired());
        data.setEnabled(entity.getEnabled());
        data.setExpired(entity.getExpired());
        data.setLocked(entity.getLocked());

        return data;
    }

    private final PersistentUser toEntity(final User data) {
        final PersistentUser entity;

        entity = new PersistentUser();
        entity.setId(data.getId());
        if (data.getUsername() != null) {
            entity.setUsername(data.getUsername()
                .toLowerCase());
        }
        entity.setName(data.getName());
        if (data.getEmail() != null) {
            entity.setEmail(data.getEmail()
                .toLowerCase());
        }
        entity.setCredentialsExpired(data.getCredentialsExpired());
        entity.setEnabled(data.getEnabled());
        entity.setExpired(data.getExpired());
        entity.setLocked(data.getLocked());

        return entity;
    }

    private final Collection<FieldFailure> validateAddUserRole(final Long id) {
        final Collection<FieldFailure> failures;
        FieldFailure                   failure;

        failures = new ArrayList<>();

        if (!repository.existsById(id)) {
            log.error("Found no role with id {}", id);
            failure = FieldFailure.of("role", "notExisting", id);
            failures.add(failure);
        }

        return failures;
    }

    private final Collection<FieldFailure> validateCreation(final User user) {
        final Collection<FieldFailure> failures;
        final Optional<Failure>        optFailure;
        FieldFailure                   failure;

        failures = new ArrayList<>();

        // Verify the username is not registered
        if (repository.existsByUsername(user.getUsername())) {
            log.error("A user already exists with the username {}", user.getUsername());
            failure = FieldFailure.of("username", "existing", user.getUsername());
            failures.add(failure);
        }

        // TODO: Don't give hints about existing emails
        // Verify the email is not registered
        if (repository.existsByEmail(user.getEmail())) {
            log.error("A user already exists with the username {}", user.getUsername());
            failure = FieldFailure.of("email", "existing", user.getEmail());
            failures.add(failure);
        }

        // Verify the email matches the valid pattern
        optFailure = emailValidationRule.test(user.getEmail());
        if (optFailure.isPresent()) {
            failure = FieldFailure.of(optFailure.get()
                .getMessage(), "email",
                optFailure.get()
                    .getCode(),
                user.getEmail());
            failures.add(failure);
        }

        return failures;
    }

    private final Collection<FieldFailure> validateDelete(final Long id) {
        final Collection<FieldFailure> failures;
        FieldFailure                   failure;

        failures = new ArrayList<>();

        if (!repository.existsById(id)) {
            log.error("Found no user with id {}", id);
            failure = FieldFailure.of("id", "notExisting", id);
            failures.add(failure);
        }

        return failures;
    }

    private final Collection<FieldFailure> validateUpdate(final User user) {
        final Collection<FieldFailure> failures;
        final Optional<Failure>        optFailure;
        final Boolean                  exists;
        FieldFailure                   failure;

        failures = new ArrayList<>();

        // Verify the id exists
        if (!repository.existsById(user.getId())) {
            log.error("No user exists for id {}", user.getId());
            failure = FieldFailure.of("id", "notExisting", user.getUsername());
            failures.add(failure);
            exists = false;
        } else {
            exists = true;
        }

        if (exists) {
            // Verify the email is not registered
            if (repository.existsByIdNotAndEmail(user.getId(), user.getEmail())) {
                log.error("A user already exists with the username {}", user.getUsername());
                failure = FieldFailure.of("email", "existing", user.getEmail());
                failures.add(failure);
            }

            // Verify the email matches the valid pattern
            optFailure = emailValidationRule.test(user.getEmail());
            if (optFailure.isPresent()) {
                failure = FieldFailure.of(optFailure.get()
                    .getMessage(), "email",
                    optFailure.get()
                        .getCode(),
                    user.getEmail());
                failures.add(failure);
            }

            // Verify the name is not changed
            if (!repository.existsByIdAndUsername(user.getId(), user.getUsername())) {
                log.error("Tried to change username for {} with id {}", user.getUsername(), user.getId());
                failure = FieldFailure.of("username", "immutable", user.getId());
                failures.add(failure);
            }
        }

        return failures;
    }

    private final Collection<FieldFailure> validateUserRoleChange(final Long id) {
        final Collection<FieldFailure> failures;
        FieldFailure                   failure;

        failures = new ArrayList<>();

        if (!repository.existsById(id)) {
            log.error("Found no user with id {}", id);
            failure = FieldFailure.of("id", "notExisting", id);
            failures.add(failure);
        }

        return failures;
    }

}
