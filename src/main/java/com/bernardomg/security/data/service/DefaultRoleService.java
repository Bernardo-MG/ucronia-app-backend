
package com.bernardomg.security.data.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.data.model.DtoRole;
import com.bernardomg.security.data.model.ImmutableRolePrivilege;
import com.bernardomg.security.data.model.Privilege;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.model.RolePrivilege;
import com.bernardomg.security.data.persistence.model.PersistentRole;
import com.bernardomg.security.data.persistence.model.PersistentRolePrivilege;
import com.bernardomg.security.data.persistence.repository.PrivilegeRepository;
import com.bernardomg.security.data.persistence.repository.RolePrivilegesRepository;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.security.data.persistence.repository.UserRolesRepository;
import com.bernardomg.security.data.service.validation.role.AddRolePrivilegeValidator;
import com.bernardomg.security.data.service.validation.role.CreateRoleValidator;
import com.bernardomg.security.data.service.validation.role.DeleteRoleValidator;
import com.bernardomg.security.data.service.validation.role.UpdateRoleValidator;
import com.bernardomg.validation.Validator;

@Service
public final class DefaultRoleService implements RoleService {

    private final Validator<RolePrivilege> addRolePrivilegeValidator;

    private final Validator<Role>          createRoleValidator;

    private final Validator<Long>          deleteRoleValidator;

    private final Validator<RolePrivilege> removeRolePrivilegeValidator;

    private final RolePrivilegesRepository rolePrivilegesRepository;

    private final RoleRepository           roleRepository;

    private final Validator<Role>          updateRoleValidator;

    public DefaultRoleService(final RoleRepository roleRepo, final PrivilegeRepository privilegeRepo,
            final RolePrivilegesRepository rolePrivilegesRepo, final UserRolesRepository userRolesRepo) {
        super();

        roleRepository = roleRepo;
        rolePrivilegesRepository = rolePrivilegesRepo;

        createRoleValidator = new CreateRoleValidator(roleRepo);
        updateRoleValidator = new UpdateRoleValidator(roleRepo);
        deleteRoleValidator = new DeleteRoleValidator(roleRepo, userRolesRepo);

        addRolePrivilegeValidator = new AddRolePrivilegeValidator(roleRepo, privilegeRepo);
        removeRolePrivilegeValidator = new AddRolePrivilegeValidator(roleRepo, privilegeRepo);
    }

    @Override
    public final Boolean addPrivilege(final Long id, final Long privilege) {
        final PersistentRolePrivilege relationship;
        final DtoRole                 role;
        final RolePrivilege           rolePrivilege;

        rolePrivilege = new ImmutableRolePrivilege(id, privilege);
        addRolePrivilegeValidator.validate(rolePrivilege);

        role = new DtoRole();
        role.setId(id);

        // Build relationship entities
        relationship = getRelationships(id, privilege);

        // Persist relationship entities
        rolePrivilegesRepository.save(relationship);

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
    public final Iterable<? extends Privilege> getPrivileges(final Long id, final Pageable pageable) {
        return roleRepository.findAllPrivileges(id, pageable);
    }

    @Override
    public final Boolean removePrivilege(final Long id, final Long privilege) {
        final PersistentRolePrivilege relationship;
        final DtoRole                 role;
        final RolePrivilege           rolePrivilege;

        rolePrivilege = new ImmutableRolePrivilege(id, privilege);
        removeRolePrivilegeValidator.validate(rolePrivilege);

        role = new DtoRole();
        role.setId(id);

        // Build relationship entities
        relationship = getRelationships(id, privilege);

        // Delete relationship entities
        rolePrivilegesRepository.delete(relationship);

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

    private final PersistentRolePrivilege getRelationships(final Long role, final Long privilege) {
        final PersistentRolePrivilege relationship;

        relationship = new PersistentRolePrivilege();
        relationship.setRoleId(role);
        relationship.setPrivilegeId(privilege);

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
