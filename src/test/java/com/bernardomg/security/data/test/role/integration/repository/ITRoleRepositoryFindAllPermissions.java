
package com.bernardomg.security.data.test.role.integration.repository;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.Permission;
import com.bernardomg.security.data.persistence.repository.RoleRepository;

@IntegrationTest
@DisplayName("Role repository - find all permissions")
public class ITRoleRepositoryFindAllPermissions {

    @Autowired
    private RoleRepository repository;

    public ITRoleRepositoryFindAllPermissions() {
        super();
    }

    @Test
    @DisplayName("Finds all the permissions for the role")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindAllPermissions_Count() {
        final Page<Permission> read;
        final Pageable         pageable;

        pageable = Pageable.unpaged();

        read = repository.findAllPermissions(1L, pageable);

        Assertions.assertEquals(4, IterableUtils.size(read));
    }

    @Test
    @DisplayName("Applies pagination")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindAllPermissions_FirstPage_Count() {
        final Page<Permission> read;
        final Pageable         pageable;

        pageable = PageRequest.of(0, 1);

        read = repository.findAllPermissions(1L, pageable);

        Assertions.assertEquals(1, IterableUtils.size(read));
    }

    @Test
    @DisplayName("Finds no permissions when the role has none")
    @Sql({ "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindAllPermissions_NoPermissions_Count() {
        final Page<Permission> read;
        final Pageable         pageable;

        pageable = Pageable.unpaged();

        read = repository.findAllPermissions(1L, pageable);

        Assertions.assertEquals(0, IterableUtils.size(read));
    }

    @Test
    @DisplayName("Finds no permissions for a not existing role")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindAllPermissions_NotExisting_Count() {
        final Page<Permission> read;
        final Pageable         pageable;

        pageable = Pageable.unpaged();

        read = repository.findAllPermissions(-1L, pageable);

        Assertions.assertEquals(0, IterableUtils.size(read));
    }

}
