
package com.bernardomg.security.data.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.data.model.DtoUser;
import com.bernardomg.security.data.model.ImmutableUserRole;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.model.UserRole;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.model.PersistentUserRoles;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.data.persistence.repository.UserRolesRepository;
import com.bernardomg.security.data.service.validation.user.AddUserRoleValidator;
import com.bernardomg.security.data.service.validation.user.CreateUserValidator;
import com.bernardomg.security.data.service.validation.user.DeleteUserValidator;
import com.bernardomg.security.data.service.validation.user.UpdateUserValidator;
import com.bernardomg.validation.Validator;

@Service
public final class DefaultUserService implements UserService {

    private final Validator<UserRole> addUserRoleValidator;

    private final Validator<User>     createUserValidator;

    private final Validator<Long>     deleteUserValidator;

    private final Validator<UserRole> removeUserRoleValidator;

    private final Validator<User>     updateUserValidator;

    private final UserRepository      userRepository;

    private final UserRolesRepository userRolesRepository;

    public DefaultUserService(final UserRepository userRepo, final RoleRepository roleRepo,
            final UserRolesRepository userRolesRepo) {
        super();

        userRepository = userRepo;
        userRolesRepository = userRolesRepo;

        createUserValidator = new CreateUserValidator(userRepo);
        updateUserValidator = new UpdateUserValidator(userRepo);
        deleteUserValidator = new DeleteUserValidator(userRepo);

        addUserRoleValidator = new AddUserRoleValidator(userRepo, roleRepo);
        removeUserRoleValidator = new AddUserRoleValidator(userRepo, roleRepo);
    }

    @Override
    public final Boolean addRole(final Long id, final Long role) {
        final PersistentUserRoles relationship;
        final DtoUser             user;
        final UserRole            userRole;

        userRole = new ImmutableUserRole(id, role);
        addUserRoleValidator.validate(userRole);

        user = new DtoUser();
        user.setId(id);

        relationship = getRelationships(id, role);

        // Persist relationship
        userRolesRepository.save(relationship);

        return true;
    }

    @Override
    public final User create(final User user) {
        final PersistentUser entity;
        final PersistentUser created;

        createUserValidator.validate(user);

        entity = toEntity(user);
        entity.setId(null);
        entity.setPassword("");

        created = userRepository.save(entity);

        return toDto(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        final DtoUser user;

        deleteUserValidator.validate(id);

        user = new DtoUser();
        user.setId(id);

        userRepository.deleteById(id);

        return true;
    }

    @Override
    public final Iterable<? extends User> getAll(final User sample, final Pageable pageable) {
        final PersistentUser entity;

        entity = toEntity(sample);

        return userRepository.findAll(Example.of(entity), pageable)
            .map(this::toDto);
    }

    @Override
    public final Optional<? extends User> getOne(final Long id) {
        return userRepository.findById(id)
            .map(this::toDto);
    }

    @Override
    public final Iterable<Role> getRoles(final Long id, final Pageable pageable) {
        return userRepository.findAllRoles(id, pageable);
    }

    @Override
    public final Boolean removeRole(final Long id, final Long role) {
        final PersistentUserRoles relationship;
        final DtoUser             user;
        final UserRole            userRole;

        userRole = new ImmutableUserRole(id, role);
        removeUserRoleValidator.validate(userRole);

        user = new DtoUser();
        user.setId(id);

        relationship = getRelationships(id, role);

        // Persist relationship
        userRolesRepository.delete(relationship);

        return true;
    }

    @Override
    public final User update(final User user) {
        final PersistentUser entity;
        final PersistentUser created;
        final PersistentUser old;

        updateUserValidator.validate(user);

        entity = toEntity(user);
        entity.setPassword("");

        old = userRepository.findById(user.getId())
            .get();
        entity.setPassword(old.getPassword());

        created = userRepository.save(entity);

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

}
