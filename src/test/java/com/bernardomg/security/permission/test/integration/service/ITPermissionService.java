
package com.bernardomg.security.permission.test.integration.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.permission.model.PermissionsSet;
import com.bernardomg.security.user.permission.service.DefaultPermissionService;

@IntegrationTest
@DisplayName("PermissionService")
public class ITPermissionService {

    @Autowired
    private DefaultPermissionService service;

    public ITPermissionService() {
        super();
    }

    @Test
    @DisplayName("Returns all the permissions")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testGetPermissions() {
        final PermissionsSet permissions;
        final List<String>   actions;
        final String         username;

        username = "admin";
        permissions = service.getPermissions(username);

        Assertions.assertEquals(username, permissions.getUsername());

        Assertions.assertEquals(1, permissions.getPermissions()
            .size());

        actions = permissions.getPermissions()
            .get("DATA");
        Assertions.assertEquals(4, actions.size());

        Assertions.assertTrue(actions.contains("CREATE"));
        Assertions.assertTrue(actions.contains("READ"));
        Assertions.assertTrue(actions.contains("UPDATE"));
        Assertions.assertTrue(actions.contains("DELETE"));
    }

    @Test
    @DisplayName("Returns no permissions when the user credentials are expired")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/credentials_expired.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testGetPermissions_CredentialsExpired() {
        final PermissionsSet permissions;
        final String         username;

        username = "admin";
        permissions = service.getPermissions(username);

        Assertions.assertEquals(username, permissions.getUsername());

        Assertions.assertEquals(0, permissions.getPermissions()
            .size());
    }

    @Test
    @DisplayName("Returns no permissions when the user is disabled")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/disabled.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testGetPermissions_Disabled() {
        final PermissionsSet permissions;
        final String         username;

        username = "admin";
        permissions = service.getPermissions(username);

        Assertions.assertEquals(username, permissions.getUsername());

        Assertions.assertEquals(0, permissions.getPermissions()
            .size());
    }

    @Test
    @DisplayName("Returns no permissions when the user is expired")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/expired.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testGetPermissions_Expired() {
        final PermissionsSet permissions;
        final String         username;

        username = "admin";
        permissions = service.getPermissions(username);

        Assertions.assertEquals(username, permissions.getUsername());

        Assertions.assertEquals(0, permissions.getPermissions()
            .size());
    }

    @Test
    @DisplayName("Returns no permissions when the user is locked")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/locked.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testGetPermissions_Locked() {
        final PermissionsSet permissions;
        final String         username;

        username = "admin";
        permissions = service.getPermissions(username);

        Assertions.assertEquals(username, permissions.getUsername());

        Assertions.assertEquals(0, permissions.getPermissions()
            .size());
    }

    @Test
    @DisplayName("Returns no permissions when there are none")
    @Sql({ "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testGetPermissions_NoPermissions() {
        final PermissionsSet permissions;
        final String         username;

        username = "admin";
        permissions = service.getPermissions(username);

        Assertions.assertEquals(username, permissions.getUsername());

        Assertions.assertEquals(0, permissions.getPermissions()
            .size());
    }

    @Test
    @DisplayName("Returns no permissions when the user doesn't exist")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql" })
    public void testGetPermissions_NotExisting() {
        final PermissionsSet permissions;
        final String         username;

        username = "admin";
        permissions = service.getPermissions(username);

        Assertions.assertEquals(username, permissions.getUsername());

        Assertions.assertEquals(0, permissions.getPermissions()
            .size());
    }

}
