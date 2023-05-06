
package com.bernardomg.security.data.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.data.model.Action;
import com.bernardomg.security.data.model.DtoRole;
import com.bernardomg.security.data.model.ImmutableRolePermission;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.model.RolePermission;
import com.bernardomg.security.data.persistence.model.PersistentRole;
import com.bernardomg.security.data.persistence.model.PersistentRolePermission;
import com.bernardomg.security.data.persistence.repository.ActionRepository;
import com.bernardomg.security.data.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.security.data.persistence.repository.UserRolesRepository;
import com.bernardomg.security.data.service.validation.role.AddRolePermissionValidator;
import com.bernardomg.security.data.service.validation.role.CreateRoleValidator;
import com.bernardomg.security.data.service.validation.role.DeleteRoleValidator;
import com.bernardomg.security.data.service.validation.role.UpdateRoleValidator;
import com.bernardomg.validation.Validator;

@Service
public final class DefaultRoleService implements RoleService {

    private final Validator<RolePermission> addRolePermissionValidator;

    private final Validator<Role>           createRoleValidator;

    private final Validator<Long>           deleteRoleValidator;

    private final Validator<RolePermission> removeRolePermissionValidator;

    private final RolePermissionRepository  RolePermissionRepository;

    private final RoleRepository            roleRepository;

    private final Validator<Role>           updateRoleValidator;

    public DefaultRoleService(final RoleRepository roleRepo, final ActionRepository actionRepo,
            final RolePermissionRepository roleActionsRepo, final UserRolesRepository userRolesRepo) {
        super();

        roleRepository = roleRepo;
        RolePermissionRepository = roleActionsRepo;

        createRoleValidator = new CreateRoleValidator(roleRepo);
        updateRoleValidator = new UpdateRoleValidator(roleRepo);
        deleteRoleValidator = new DeleteRoleValidator(roleRepo, userRolesRepo);

        addRolePermissionValidator = new AddRolePermissionValidator(roleRepo, actionRepo);
        removeRolePermissionValidator = new AddRolePermissionValidator(roleRepo, actionRepo);
    }

    @Override
    public final Boolean addPermission(final Long id, final Long resource, final Long action) {
        final PersistentRolePermission relationship;
        final DtoRole                  role;
        final RolePermission           roleAction;

        roleAction = new ImmutableRolePermission(id, resource, action);
        addRolePermissionValidator.validate(roleAction);

        role = new DtoRole();
        role.setId(id);

        // Build relationship entities
        relationship = getRelationships(id, action);

        // Persist relationship entities
        RolePermissionRepository.save(relationship);

        return true;
    }

    @Override
    public final Role create(final Role role) {
        final PersistentRole entity;
        final PersistentRole created;

        createRoleValidator.validate(role);

        entity = toEntity(role);
        entity.setId(null);

        created = roleRepository.save(entity);

        return toDto(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        final DtoRole role;

        deleteRoleValidator.validate(id);

        role = new DtoRole();
        role.setId(id);

        roleRepository.deleteById(id);

        return true;
    }

    @Override
    public final Iterable<? extends Role> getAll(final Role sample, final Pageable pageable) {
        final PersistentRole entity;

        entity = toEntity(sample);

        return roleRepository.findAll(Example.of(entity), pageable)
            .map(this::toDto);
    }

    @Override
    public final Optional<? extends Role> getOne(final Long id) {
        return roleRepository.findById(id)
            .map(this::toDto);
    }

    @Override
    public final Iterable<? extends Action> getPermission(final Long id, final Pageable pageable) {
        return roleRepository.findAllActions(id, pageable);
    }

    @Override
    public final Boolean removePermission(final Long id, final Long resource, final Long action) {
        final PersistentRolePermission relationship;
        final DtoRole                  role;
        final RolePermission           roleAction;

        roleAction = new ImmutableRolePermission(id, resource, action);
        removeRolePermissionValidator.validate(roleAction);

        role = new DtoRole();
        role.setId(id);

        // Build relationship entities
        relationship = getRelationships(id, action);

        // Delete relationship entities
        RolePermissionRepository.delete(relationship);

        return true;
    }

    @Override
    public final Role update(final Role role) {
        final PersistentRole entity;
        final PersistentRole created;

        updateRoleValidator.validate(role);

        entity = toEntity(role);

        created = roleRepository.save(entity);

        return toDto(created);
    }

    private final PersistentRolePermission getRelationships(final Long role, final Long action) {
        final PersistentRolePermission relationship;

        relationship = new PersistentRolePermission();
        relationship.setRoleId(role);
        relationship.setActionId(action);

        return relationship;
    }

    private final Role toDto(final PersistentRole entity) {
        final DtoRole data;

        data = new DtoRole();
        data.setId(entity.getId());
        data.setName(entity.getName());

        return data;
    }

    private final PersistentRole toEntity(final Role data) {
        final PersistentRole entity;

        entity = new PersistentRole();
        entity.setId(data.getId());
        entity.setName(data.getName());

        return entity;
    }

}
