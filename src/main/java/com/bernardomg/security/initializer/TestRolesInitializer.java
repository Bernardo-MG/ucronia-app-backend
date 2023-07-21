
package com.bernardomg.security.initializer;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Example;

import com.bernardomg.security.user.persistence.model.PersistentAction;
import com.bernardomg.security.user.persistence.model.PersistentResource;
import com.bernardomg.security.user.persistence.model.PersistentRole;
import com.bernardomg.security.user.persistence.model.PersistentRolePermission;
import com.bernardomg.security.user.persistence.repository.ActionRepository;
import com.bernardomg.security.user.persistence.repository.ResourceRepository;
import com.bernardomg.security.user.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.persistence.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TestRolesInitializer implements ApplicationRunner {

    private final ActionRepository         actionRepository;

    private final ResourceRepository       resourceRepository;

    private final RolePermissionRepository rolePermissionRepository;

    private final RoleRepository           roleRepository;

    public TestRolesInitializer(final ActionRepository actionRepo, final ResourceRepository resourceRepo,
            final RoleRepository roleRepo, final RolePermissionRepository rolePermissionRepo) {
        super();

        actionRepository = actionRepo;
        resourceRepository = resourceRepo;
        roleRepository = roleRepo;
        rolePermissionRepository = rolePermissionRepo;
    }

    @Override
    public final void run(final ApplicationArguments args) throws Exception {
        log.debug("Initializing test roles");

        runIfNotExists(this::initializeAdminRole, "ADMIN");
        runIfNotExists(this::initializeReadRole, "READ");

        log.debug("Initialed test roles");
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

    private final void initializeAdminRole() {
        final PersistentRole           rootRole;
        final PersistentRole           savedRootRole;
        final List<PersistentAction>   actions;
        final List<PersistentResource> resources;
        PersistentRolePermission       permission;

        // Add read user
        rootRole = getRootRole();
        savedRootRole = roleRepository.save(rootRole);

        actions = actionRepository.findAll();
        resources = resourceRepository.findAll();

        for (final PersistentResource resource : resources) {
            for (final PersistentAction action : actions) {
                permission = PersistentRolePermission.builder()
                    .roleId(savedRootRole.getId())
                    .actionId(action.getId())
                    .resourceId(resource.getId())
                    .granted(true)
                    .build();
                rolePermissionRepository.save(permission);
            }
        }
    }

    private final void initializeReadRole() {
        final PersistentRole           readRole;
        final PersistentRole           savedReadRole;
        final PersistentAction         example;
        final List<PersistentAction>   actions;
        final List<PersistentResource> resources;
        PersistentRolePermission       permission;

        // Add read user
        readRole = getReadRole();
        savedReadRole = roleRepository.save(readRole);

        example = PersistentAction.builder()
            .name("READ")
            .build();
        actions = actionRepository.findAll(Example.of(example));
        resources = resourceRepository.findAll();

        for (final PersistentResource resource : resources) {
            for (final PersistentAction action : actions) {
                permission = PersistentRolePermission.builder()
                    .roleId(savedReadRole.getId())
                    .actionId(action.getId())
                    .resourceId(resource.getId())
                    .granted(true)
                    .build();
                rolePermissionRepository.save(permission);
            }
        }
    }

    private final void runIfNotExists(final Runnable runnable, final String name) {
        if (!roleRepository.existsByName(name)) {
            runnable.run();
            log.debug("Initialized {} role", name);
        } else {
            log.debug("Role {} already exists. Skipped initialization.", name);
        }
    }

}
