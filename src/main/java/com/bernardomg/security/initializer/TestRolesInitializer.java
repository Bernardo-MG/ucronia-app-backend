
package com.bernardomg.security.initializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Example;

import com.bernardomg.security.permission.constant.Actions;
import com.bernardomg.security.permission.persistence.model.PersistentAction;
import com.bernardomg.security.permission.persistence.model.PersistentPermission;
import com.bernardomg.security.permission.persistence.model.PersistentResource;
import com.bernardomg.security.permission.persistence.model.PersistentRolePermission;
import com.bernardomg.security.permission.persistence.repository.ActionRepository;
import com.bernardomg.security.permission.persistence.repository.PermissionRepository;
import com.bernardomg.security.permission.persistence.repository.ResourceRepository;
import com.bernardomg.security.permission.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.persistence.model.PersistentRole;
import com.bernardomg.security.user.persistence.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TestRolesInitializer implements ApplicationRunner {

    private final ActionRepository         actionRepository;

    private final PermissionRepository     permissionRepository;

    private final ResourceRepository       resourceRepository;

    private final RolePermissionRepository rolePermissionRepository;

    private final RoleRepository           roleRepository;

    public TestRolesInitializer(final ActionRepository actionRepo, final ResourceRepository resourceRepo,
            final PermissionRepository permissionRepo, final RoleRepository roleRepo,
            final RolePermissionRepository rolePermissionRepo) {
        super();

        actionRepository = actionRepo;
        resourceRepository = resourceRepo;
        permissionRepository = permissionRepo;
        roleRepository = roleRepo;
        rolePermissionRepository = rolePermissionRepo;
    }

    @Override
    public final void run(final ApplicationArguments args) throws Exception {
        final Collection<PersistentPermission> permissions;

        log.debug("Initializing test roles");

        permissions = initializePermissions();

        runIfNotExists(() -> initializeAdminRole(permissions), "ADMIN");
        runIfNotExists(() -> initializeReadRole(permissions), "READ");

        log.debug("Initialized test roles");
    }

    private final PersistentRole getReadRole() {
        return PersistentRole.builder()
            .name("READ")
            .build();
    }

    private final PersistentRole getRootRole() {
        return PersistentRole.builder()
            .name("ADMIN")
            .build();
    }

    private final void initializeAdminRole(final Collection<PersistentPermission> permissions) {
        final PersistentRole     rootRole;
        final PersistentRole     savedRootRole;
        PersistentRolePermission rolePermission;

        // Add read user
        rootRole = getRootRole();
        savedRootRole = roleRepository.save(rootRole);

        for (final PersistentPermission perm : permissions) {
            rolePermission = PersistentRolePermission.builder()
                .roleId(savedRootRole.getId())
                .permissionId(perm.getId())
                .granted(true)
                .build();
            rolePermissionRepository.save(rolePermission);
        }
    }

    private final Collection<PersistentPermission> initializePermissions() {
        final List<PersistentAction>           actions;
        final List<PersistentResource>         resources;
        final Collection<PersistentPermission> permissions;
        final Collection<PersistentPermission> existingPermissions;
        final Collection<PersistentPermission> toSave;
        PersistentPermission                   permission;

        actions = actionRepository.findAll();
        resources = resourceRepository.findAll();

        permissions = new ArrayList<>();
        for (final PersistentResource resource : resources) {
            for (final PersistentAction action : actions) {
                permission = PersistentPermission.builder()
                    .resource(resource.getName())
                    .action(action.getName())
                    .build();
                permissions.add(permission);
            }
        }
        existingPermissions = permissionRepository.findAll();
        toSave = permissions.stream()
            .filter(p -> existingPermissions.stream()
                .noneMatch(e -> (!Objects.equals(e.getAction(), p.getAction()))))
            .toList();

        return permissionRepository.saveAll(toSave);
    }

    private final void initializeReadRole(final Collection<PersistentPermission> permissions) {
        final PersistentRole readRole;
        final PersistentRole savedReadRole;

        // Add read user
        readRole = getReadRole();
        savedReadRole = roleRepository.save(readRole);

        setPermissions(savedReadRole, permissions, Actions.READ);
        setPermissions(savedReadRole, permissions, Actions.VIEW);
    }

    private final void runIfNotExists(final Runnable runnable, final String name) {
        if (!roleRepository.existsByName(name)) {
            runnable.run();
            log.debug("Initialized {} role", name);
        } else {
            log.debug("Role {} already exists. Skipped initialization.", name);
        }
    }

    private final void setPermissions(final PersistentRole role, final Collection<PersistentPermission> permissions,
            final String actionName) {
        final PersistentAction                 example;
        final Optional<PersistentAction>       action;
        PersistentRolePermission               rolePermission;
        final Collection<PersistentPermission> validPermissions;

        example = PersistentAction.builder()
            .name(actionName)
            .build();
        action = actionRepository.findOne(Example.of(example));

        validPermissions = permissions.stream()
            .filter(p -> p.getAction()
                .equals(action.get()
                    .getName()))
            .toList();
        for (final PersistentPermission permission : validPermissions) {
            rolePermission = PersistentRolePermission.builder()
                .roleId(role.getId())
                .permissionId(permission.getId())
                .granted(true)
                .build();
            rolePermissionRepository.save(rolePermission);
        }
    }

}
