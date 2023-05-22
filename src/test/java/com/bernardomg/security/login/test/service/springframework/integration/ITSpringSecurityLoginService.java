
package com.bernardomg.security.login.test.service.springframework.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.login.model.DtoLogin;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.TokenLoginStatus;
import com.bernardomg.security.login.service.DefaultLoginService;
import com.bernardomg.security.test.constant.TokenConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@IntegrationTest
@DisplayName("SpringSecurityLoginService")
public class ITSpringSecurityLoginService {

    @Autowired
    private DefaultLoginService service;

    public ITSpringSecurityLoginService() {
        super();
    }

    @Test
    @DisplayName("Doesn't log in a user with expired credentials")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/credentials_expired.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_CredentialsExpired() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = service.login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Doesn't log in a disabled user")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/disabled.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Disabled() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = service.login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Doesn't log in an expired user")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/expired.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Expired() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = service.login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Doesn't log in a locked user")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/locked.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Locked() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = service.login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Doesn't log in a not existing user")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql" })
    public void testLogIn_NotExisting() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = service.login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Logs in with a valid user")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Valid() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = service.login(login);

        Assertions.assertInstanceOf(TokenLoginStatus.class, status);

        Assertions.assertTrue(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
        Assertions.assertFalse(((TokenLoginStatus) status).getToken()
            .isBlank());
    }

    @Test
    @DisplayName("Logs in with a valid user, ignoring username case")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Valid_Case() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("ADMIN");
        login.setPassword("1234");

        status = service.login(login);

        Assertions.assertInstanceOf(TokenLoginStatus.class, status);

        Assertions.assertTrue(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
        Assertions.assertFalse(((TokenLoginStatus) status).getToken()
            .isBlank());
    }

    @Test
    @DisplayName("On a succesful login returns a valid JWT token")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Valid_JwtToken() {
        final LoginStatus status;
        final DtoLogin    login;
        final JwtParser   parser;
        final Claims      claims;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = service.login(login);

        parser = Jwts.parserBuilder()
            .setSigningKey(TokenConstants.KEY)
            .build();

        claims = parser.parseClaimsJws(((TokenLoginStatus) status).getToken())
            .getBody();

        Assertions.assertEquals("admin", claims.getSubject());
    }

    @Test
    @DisplayName("Doesn't log in a user with no permissions")
    @Sql({ "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Valid_NoPermissions() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = service.login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Doesn't log in a user with no granted permissions")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission_not_granted.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testLogIn_Valid_NotGrantedPermissions() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = service.login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

}
