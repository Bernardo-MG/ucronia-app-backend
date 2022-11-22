
package com.bernardomg.security.data.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.data.model.DtoUser;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.model.PersistentUserRoles;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.data.persistence.repository.UserRolesRepository;
import com.bernardomg.security.data.validation.user.RoleInUserUpdateValidator;
import com.bernardomg.security.data.validation.user.UserDeleteValidator;
import com.bernardomg.security.data.validation.user.UserRoleUpdateValidator;
import com.bernardomg.security.validation.EmailValidationRule;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.exception.ValidationException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public final class DefaultUserService implements UserService {

    private final UserDeleteValidator       deleteValidator;

    /**
     * Email validation rule. To check the email fits into the valid email pattern.
     */
    private final ValidationRule<String>    emailValidationRule = new EmailValidationRule();

    private final UserRepository            repository;

    private final RoleInUserUpdateValidator roleUpdateValidator;

    private final UserRolesRepository       userRolesRepository;

    private final UserRoleUpdateValidator   userRoleUpdateValidator;

    @Override
    public final Boolean addRole(final Long id, final Long role) {
        final PersistentUserRoles relationship;
        final DtoUser             user;

        user = new DtoUser();
        user.setId(id);

        userRoleUpdateValidator.validate(user);
        roleUpdateValidator.validate(role);

        relationship = getRelationships(id, role);

        // Persist relationship
        userRolesRepository.save(relationship);

        return true;
    }

    @Override
    public final User create(final User user) {
        final PersistentUser      entity;
        final PersistentUser      created;
        final Collection<Failure> failures;

        failures = validateCreation(user);

        if (!failures.isEmpty()) {
            log.debug("Got errors: {}", failures);
            throw new ValidationException(failures);
        }

        entity = toEntity(user);
        entity.setId(null);
        entity.setPassword("");

        created = repository.save(entity);

        return toDto(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        final DtoUser user;

        user = new DtoUser();
        user.setId(id);

        deleteValidator.validate(user);

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
        final PersistentUserRoles relationship;
        final DtoUser             user;

        user = new DtoUser();
        user.setId(id);

        userRoleUpdateValidator.validate(user);
        roleUpdateValidator.validate(role);

        relationship = getRelationships(id, role);

        // Persist relationship
        userRolesRepository.delete(relationship);

        return true;
    }

    @Override
    public final User update(final User user) {
        final PersistentUser      entity;
        final PersistentUser      created;
        final PersistentUser      old;
        final Collection<Failure> failures;

        failures = validateUpdate(user);

        if (!failures.isEmpty()) {
            log.debug("Got errors: {}", failures);
            throw new ValidationException(failures);
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

    private final Collection<Failure> validateCreation(final User user) {
        final Collection<Failure> failures;
        final Optional<Failure>   optFailure;
        Failure                   failure;

        failures = new ArrayList<>();

        // Verify the username is not registered
        if (repository.existsByUsername(user.getUsername())) {
            log.error("A user already exists with the username {}", user.getUsername());
            failure = FieldFailure.of("error.username.existing", "memberId", user.getUsername());
            failures.add(failure);
        }

        // Verify the email is not registered
        if (repository.existsByEmail(user.getEmail())) {
            log.error("A user already exists with the username {}", user.getUsername());
            failure = FieldFailure.of("error.email.existing", "memberId", user.getEmail());
            failures.add(failure);
        }

        // Verify the email matches the valid pattern
        optFailure = emailValidationRule.test(user.getEmail());
        if (optFailure.isPresent()) {
            failures.add(optFailure.get());
        }

        return failures;
    }

    private final Collection<Failure> validateUpdate(final User user) {
        final Collection<Failure> failures;
        final Optional<Failure>   optFailure;
        final Boolean             exists;
        Failure                   failure;

        failures = new ArrayList<>();

        // Verify the id exists
        if (!repository.existsById(user.getId())) {
            log.error("No user exists for id {}", user.getId());
            failure = FieldFailure.of("error.id.notExisting", "memberId", user.getUsername());
            failures.add(failure);
            exists = false;
        } else {
            exists = true;
        }

        if (exists) {
            // Verify the email is not registered
            if (repository.existsByIdNotAndEmail(user.getId(), user.getEmail())) {
                log.error("A user already exists with the username {}", user.getUsername());
                failure = FieldFailure.of("error.email.existing", "memberId", user.getEmail());
                failures.add(failure);
            }

            // Verify the email matches the valid pattern
            optFailure = emailValidationRule.test(user.getEmail());
            if (optFailure.isPresent()) {
                failures.add(optFailure.get());
            }

            // Verify the name is not changed
            if (!repository.existsByIdAndUsername(user.getId(), user.getUsername())) {
                log.error("Tried to change username for {} with id {}", user.getUsername(), user.getId());
                failure = FieldFailure.of("error.username.immutable", "id", user.getId());
                failures.add(failure);
            }
        }

        return failures;
    }

}
