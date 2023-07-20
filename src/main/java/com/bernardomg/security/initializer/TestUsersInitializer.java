
package com.bernardomg.security.initializer;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.model.PersistentUserRole;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.persistence.repository.UserRoleRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@ConditionalOnProperty(prefix = "initilize.test", name = "user", havingValue = "true")
@Slf4j
public final class TestUsersInitializer implements ApplicationRunner {

    private final UserRepository     userRepository;

    private final UserRoleRepository userRoleRepository;

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        final PersistentUser     rootUser;
        final PersistentUser     savedRootUser;
        final PersistentUser     readUser;
        final PersistentUser     savedReadUser;
        final PersistentUserRole rootUserRole;
        final PersistentUserRole readUserRole;
        
        log.debug("Initializing test users");

        // Add root user
        rootUser = getRootUser();
        savedRootUser = userRepository.save(rootUser);

        // TODO: Load the role id dynamically
        rootUserRole = PersistentUserRole.builder()
            .userId(savedRootUser.getId())
            .roleId(1l)
            .build();
        userRoleRepository.save(rootUserRole);
        
        log.debug("Initialized root user");

        // Add read user
        readUser = getReadUser();
        savedReadUser = userRepository.save(readUser);

        // TODO: Load the role id dynamically
        readUserRole = PersistentUserRole.builder()
            .userId(savedReadUser.getId())
            .roleId(2l)
            .build();
        userRoleRepository.save(readUserRole);
        
        log.debug("Initialized read user");
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

}
