
package com.bernardomg.security.data.test.user.integration.repository;

import java.util.Collection;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.Permission;
import com.bernardomg.security.data.persistence.repository.UserRepository;

@IntegrationTest
@DisplayName("User repository - find permissions")
public class ITUserRepositoryFindPermissions {

    @Autowired
    private UserRepository repository;

    public ITUserRepositoryFindPermissions() {
        super();
    }

    @Test
    @DisplayName("Finds all the permissions for the user")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindForUser_Count() {
        final Collection<Permission> read;

        read = repository.findPermissions(1L);

        Assertions.assertEquals(4, IterableUtils.size(read));
    }

    @Test
    @DisplayName("Finds no permissions when the user has none")
    @Sql({ "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindForUser_NoPermissions_Count() {
        final Collection<Permission> read;

        read = repository.findPermissions(1L);

        Assertions.assertEquals(0, IterableUtils.size(read));
    }

    @Test
    @DisplayName("Finds no permissions for a not existing user")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindForUser_NotExisting_Count() {
        final Collection<Permission> read;

        read = repository.findPermissions(-1L);

        Assertions.assertEquals(0, IterableUtils.size(read));
    }

    @Test
    @DisplayName("Finds no permissions when the permissions are not granted")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission_not_granted.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindForUser_NotGrantedPermissions_Count() {
        final Collection<Permission> read;

        read = repository.findPermissions(-1L);

        Assertions.assertEquals(0, IterableUtils.size(read));
    }

}
