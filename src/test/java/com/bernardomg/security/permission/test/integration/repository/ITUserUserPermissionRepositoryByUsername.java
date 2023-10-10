
package com.bernardomg.security.permission.test.integration.repository;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.permission.persistence.model.PersistentUserGrantedPermission;
import com.bernardomg.security.permission.persistence.repository.UserGrantedPermissionRepository;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("User repository - find permissions by username")
class ITUserUserPermissionRepositoryByUsername {

    @Autowired
    private UserGrantedPermissionRepository repository;

    public ITUserUserPermissionRepositoryByUsername() {
        super();
    }

    @Test
    @DisplayName("Finds all the permissions for the user")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/permission/crud.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testFindAllByUsername_Count() {
        final Collection<PersistentUserGrantedPermission> read;

        read = repository.findAllByUsername("admin");

        Assertions.assertThat(read)
            .hasSize(4);
    }

    @Test
    @DisplayName("Finds no permissions when the user has none")
    @Sql({ "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testFindAllByUsername_NoPermissions_Count() {
        final Collection<PersistentUserGrantedPermission> read;

        read = repository.findAllByUsername("admin");

        Assertions.assertThat(read)
            .isEmpty();
    }

    @Test
    @DisplayName("Finds no permissions for a not existing user")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/permission/crud.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testFindAllByUsername_NotExisting_Count() {
        final Collection<PersistentUserGrantedPermission> read;

        read = repository.findAllByUsername("abc");

        Assertions.assertThat(read)
            .isEmpty();
    }

    @Test
    @DisplayName("Finds no permissions when the permissions are not granted")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/permission/crud.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_permission_not_granted.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testFindAllByUsername_NotGranted_Count() {
        final Collection<PersistentUserGrantedPermission> read;

        read = repository.findAllByUsername("abc");

        Assertions.assertThat(read)
            .isEmpty();
    }

    @Test
    @DisplayName("When the permissions are duplicated, no repeated permission is returned")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/permission/crud.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/role/alternative.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/role_permission_alternative.sql",
            "/db/queries/security/relationship/user_role.sql",
            "/db/queries/security/relationship/user_role_alternative.sql" })
    void testFindAllByUsername_Repeated_Count() {
        final Collection<PersistentUserGrantedPermission> read;

        read = repository.findAllByUsername("admin");

        Assertions.assertThat(read)
            .hasSize(4);
    }

}
