
package com.bernardomg.security.login.test.service.springframework.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.service.springframework.SpringSecurityLoginService;

@IntegrationTest
@DisplayName("SpringSecurityLoginService")
public class ITSpringSecurityLoginService {

    @Autowired
    private PasswordEncoder            passEncoder;

    private SpringSecurityLoginService service;

    @Autowired
    private UserDetailsService         userDetService;

    public ITSpringSecurityLoginService() {
        super();
    }

    @BeforeEach
    public void initializeService() {
        service = new SpringSecurityLoginService(userDetService, passEncoder);
    }

    @Test
    @DisplayName("Doesn't log in a disabled user")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/disabled.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Disabled() {
        final LoginStatus status;

        status = service.login("admin", "1234");

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Logs in with a valid user")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Valid() {
        final LoginStatus details;

        details = service.login("admin", "1234");

        Assertions.assertTrue(details.getLogged());
        Assertions.assertEquals("admin", details.getUsername());
    }

}
