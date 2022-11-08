
package com.bernardomg.security.login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.login.model.TokenLoginStatus;
import com.bernardomg.security.login.service.TokenLoginService;

@IntegrationTest
@DisplayName("Token login service")
public class ITTokenLoginService {

    @Autowired
    private TokenLoginService service;

    public ITTokenLoginService() {
        super();
    }

    @Test
    @DisplayName("Doesn't log in a disabled user")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/disabled.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Disabled() {
        final TokenLoginStatus details;

        details = service.login("admin", "1234");

        Assertions.assertFalse(details.getLogged());
        Assertions.assertEquals("admin", details.getUsername());
        Assertions.assertTrue(details.getToken()
            .isBlank());
    }

    @Test
    @DisplayName("Logs in with a valid user")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Existing() {
        final TokenLoginStatus details;

        details = service.login("admin", "1234");

        Assertions.assertTrue(details.getLogged());
        Assertions.assertEquals("admin", details.getUsername());
        Assertions.assertTrue(!details.getToken()
            .isBlank());
    }

    @Test
    @DisplayName("Doesn't log in a expired user")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/expired.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Expired() {
        final TokenLoginStatus details;

        details = service.login("admin", "1234");

        Assertions.assertFalse(details.getLogged());
        Assertions.assertEquals("admin", details.getUsername());
        Assertions.assertTrue(details.getToken()
            .isBlank());
    }

    @Test
    @DisplayName("Doesn't log in a locked user")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/locked.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Locked() {
        final TokenLoginStatus details;

        details = service.login("admin", "1234");

        Assertions.assertFalse(details.getLogged());
        Assertions.assertEquals("admin", details.getUsername());
        Assertions.assertTrue(details.getToken()
            .isBlank());
    }

    @Test
    @DisplayName("Doesn't log in a not existing user")
    public void testLogIn_NotExisting() {
        final TokenLoginStatus details;

        details = service.login("admin", "1234");

        Assertions.assertFalse(details.getLogged());
        Assertions.assertEquals("admin", details.getUsername());
        Assertions.assertTrue(details.getToken()
            .isBlank());
    }

}
