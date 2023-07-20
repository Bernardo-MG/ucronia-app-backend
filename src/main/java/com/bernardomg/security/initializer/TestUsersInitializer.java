
package com.bernardomg.security.initializer;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.model.PersistentUserRole;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.persistence.repository.UserRoleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TestUsersInitializer implements ApplicationRunner {

    private final UserRepository     userRepository;

    private final UserRoleRepository userRoleRepository;

    public TestUsersInitializer(final UserRepository userRepo, final UserRoleRepository userRoleRepo) {
        super();

        userRepository = userRepo;
        userRoleRepository = userRoleRepo;
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        log.debug("Initializing test users");

        if (!userRepository.existsByUsername("root")) {
            initializeRootUser();
            log.debug("Initialized root user");
        } else {
            log.debug("User {} already exists. Skipped initialization.", "root");
        }

        if (!userRepository.existsByUsername("read")) {
            initializeReadUser();
            log.debug("Initialized read user");
        } else {
            log.debug("User {} already exists. Skipped initialization.", "read");
        }
    }

    private final PersistentUser getReadUser() {
        return PersistentUser.builder()
            .username("read")
            .name("read")
            .email("email2@nowhere.com")
            .password("$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW")
            .enabled(true)
            .locked(false)
            .expired(false)
            .credentialsExpired(false)
            .build();
    }

    private final PersistentUser getRootUser() {
        return PersistentUser.builder()
            .username("root")
            .name("root")
            .email("email1@nowhere.com")
            .password("$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW")
            .enabled(true)
            .locked(false)
            .expired(false)
            .credentialsExpired(false)
            .build();
    }

    private final void initializeReadUser() {
        final PersistentUser     readUser;
        final PersistentUser     savedReadUser;
        final PersistentUserRole readUserRole;

        // Add read user
        readUser = getReadUser();
        savedReadUser = userRepository.save(readUser);

        // TODO: Load the role id dynamically
        readUserRole = PersistentUserRole.builder()
            .userId(savedReadUser.getId())
            .roleId(2l)
            .build();
        userRoleRepository.save(readUserRole);
    }

    private final void initializeRootUser() {
        final PersistentUser     rootUser;
        final PersistentUser     savedRootUser;
        final PersistentUserRole rootUserRole;

        // Add root user
        rootUser = getRootUser();
        savedRootUser = userRepository.save(rootUser);

        // TODO: Load the role id dynamically
        rootUserRole = PersistentUserRole.builder()
            .userId(savedRootUser.getId())
            .roleId(1l)
            .build();
        userRoleRepository.save(rootUserRole);
    }

}
