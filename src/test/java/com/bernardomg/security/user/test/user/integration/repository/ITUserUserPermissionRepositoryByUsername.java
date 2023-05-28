
package com.bernardomg.security.user.test.user.integration.repository;

import java.util.Collection;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.persistence.model.PersistentUserGrantedPermission;
import com.bernardomg.security.user.persistence.repository.UserGrantedPermissionRepository;

@IntegrationTest
@DisplayName("User repository - find permissions by username")
public class ITUserUserPermissionRepositoryByUsername {

    @Autowired
    private UserGrantedPermissionRepository repository;

    public ITUserUserPermissionRepositoryByUsername() {
        super();
    }

    @Test
    @DisplayName("Finds all the permissions for the user")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindAllByUsername_Count() {
        final Collection<PersistentUserGrantedPermission> read;

        read = repository.findAllByUsername("admin");

        Assertions.assertEquals(4, IterableUtils.size(read));
    }

    @Test
    @DisplayName("Finds no permissions when the user has none")
    @Sql({ "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindAllByUsername_NoPermissions_Count() {
        final Collection<PersistentUserGrantedPermission> read;

        read = repository.findAllByUsername("admin");

        Assertions.assertEquals(0, IterableUtils.size(read));
    }

    @Test
    @DisplayName("Finds no permissions for a not existing user")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindAllByUsername_NotExisting_Count() {
        final Collection<PersistentUserGrantedPermission> read;

        read = repository.findAllByUsername("abc");

        Assertions.assertEquals(0, IterableUtils.size(read));
    }

    @Test
    @DisplayName("Finds no permissions when the permissions are not granted")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission_not_granted.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindAllByUsername_NotGranted_Count() {
        final Collection<PersistentUserGrantedPermission> read;

        read = repository.findAllByUsername("abc");

        Assertions.assertEquals(0, IterableUtils.size(read));
    }

    @Test
    @DisplayName("When the permissions are duplicated, no repeated permission is returned")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/role/alternative.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/role_permission_alternative.sql",
            "/db/queries/security/relationship/user_role.sql",
            "/db/queries/security/relationship/user_role_alternative.sql" })
    public void testFindAllByUsername_Repeated_Count() {
        final Collection<PersistentUserGrantedPermission> read;

        read = repository.findAllByUsername("admin");

        Assertions.assertEquals(4, IterableUtils.size(read));
    }

}