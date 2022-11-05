
package com.bernardomg.security.data.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.data.model.DtoRole;
import com.bernardomg.security.data.model.Privilege;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.persistence.model.PersistentRole;
import com.bernardomg.security.data.persistence.model.PersistentRolePrivileges;
import com.bernardomg.security.data.persistence.repository.RolePrivilegesRepository;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.security.data.validation.role.RoleCreateValidator;
import com.bernardomg.security.data.validation.role.RoleDeleteValidator;
import com.bernardomg.security.data.validation.role.RolePrivilegeUpdateValidator;
import com.bernardomg.security.data.validation.role.RoleUpdateValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultRoleService implements RoleService {

    private final RoleDeleteValidator          deleteValidator;

    private final RoleRepository               repository;

    private final RoleCreateValidator          roleCreateValidator;

    private final RolePrivilegesRepository     rolePrivilegesRepository;

    private final RolePrivilegeUpdateValidator rolePrivilegeUpdateValidator;

    private final RoleUpdateValidator          updateValidator;

    @Override
    public final Boolean addPrivilege(final Long id, final Long privilege) {
        final PersistentRolePrivileges relationship;
        final DtoRole                  role;

        role = new DtoRole();
        role.setId(id);

        updateValidator.validate(role);
        rolePrivilegeUpdateValidator.validate(privilege);

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

        roleCreateValidator.validate(role);

        entity = toEntity(role);
        entity.setId(null);

        created = repository.save(entity);

        return toDto(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        final DtoRole role;

        role = new DtoRole();
        role.setId(id);

        deleteValidator.validate(role);

        repository.deleteById(id);

        return true;
    }

    @Override
    public final Iterable<? extends Role> getAll(final Role sample, final Pageable pageable) {
        final PersistentRole entity;

        entity = toEntity(sample);

        return repository.findAll(Example.of(entity), pageable)
            .map(this::toDto);
    }

    @Override
    public final Optional<? extends Role> getOne(final Long id) {
        return repository.findById(id)
            .map(this::toDto);
    }

    @Override
    public final Iterable<? extends Privilege> getPrivileges(final Long id, final Pageable pageable) {
        return repository.findAllPrivileges(id, pageable);
    }

    @Override
    public final Boolean removePrivilege(final Long id, final Long privilege) {
        final PersistentRolePrivileges relationship;
        final DtoRole                  role;

        role = new DtoRole();
        role.setId(id);

        updateValidator.validate(role);
        rolePrivilegeUpdateValidator.validate(privilege);

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

        updateValidator.validate(role);

        entity = toEntity(role);

        created = repository.save(entity);

        return toDto(created);
    }

    private final PersistentRolePrivileges getRelationships(final Long role, final Long privilege) {
        final PersistentRolePrivileges relationship;

        relationship = new PersistentRolePrivileges();
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
