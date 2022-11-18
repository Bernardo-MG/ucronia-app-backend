
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
import com.bernardomg.security.login.model.DtoLogin;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.TokenLoginStatus;
import com.bernardomg.security.login.service.springframework.SpringSecurityTokenLoginService;
import com.bernardomg.security.token.provider.TokenProvider;

@IntegrationTest
@DisplayName("SpringSecurityTokenLoginService")
public class ITSpringSecurityTokenLoginServiceService {

    @Autowired
    private PasswordEncoder                 passEncoder;

    private SpringSecurityTokenLoginService service;

    @Autowired
    private TokenProvider                   tProvider;

    @Autowired
    private UserDetailsService              userDetService;

    public ITSpringSecurityTokenLoginServiceService() {
        super();
    }

    @BeforeEach
    public void initializeService() {
        service = new SpringSecurityTokenLoginService(userDetService, passEncoder, tProvider);
    }

    @Test
    @DisplayName("Doesn't log in a disabled user")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/disabled.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Disabled() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = service.login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getSuccessful());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Logs in with a valid user")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Valid() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = service.login(login);

        Assertions.assertInstanceOf(TokenLoginStatus.class, status);

        Assertions.assertTrue(status.getSuccessful());
        Assertions.assertEquals("admin", status.getUsername());
        Assertions.assertFalse(((TokenLoginStatus) status).getToken()
            .isBlank());
    }

}
