
package com.bernardomg.security.password.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.password.service.PasswordRecoveryService;

@IntegrationTest
@DisplayName("PasswordRecoveryService - change password")
public class ITPasswordRecoveryServiceChange {

    @Autowired
    private UserRepository          repository;

    @Autowired
    private PasswordRecoveryService service;

    public ITPasswordRecoveryServiceChange() {
        super();
    }

    @Test
    @DisplayName("Starting password recovery with an existing user gives an OK")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testChangePassword_Changed() {
        final PersistentUser user;

        service.changePassword("token", "1234", "abc");

        user = repository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertEquals("abc", user.getPassword());
    }

}
