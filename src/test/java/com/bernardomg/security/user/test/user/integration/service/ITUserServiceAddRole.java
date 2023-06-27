
package com.bernardomg.security.user.test.user.integration.service;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.persistence.model.PersistentUserRoles;
import com.bernardomg.security.user.persistence.repository.UserRolesRepository;
import com.bernardomg.security.user.service.UserService;

@IntegrationTest
@DisplayName("User service - add role")
public class ITUserServiceAddRole {

    @Autowired
    private UserService         service;

    @Autowired
    private UserRolesRepository userRolesRepository;

    public ITUserServiceAddRole() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when adding a role")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql" })
    public void testAddRole_AddsEntity() {
        final PersistentUserRoles entity;

        service.addRole(1L, 1L);

        Assertions.assertThat(userRolesRepository.count())
            .isEqualTo(1);

        entity = userRolesRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getUserId())
            .isEqualTo(1);
        Assertions.assertThat(entity.getRoleId())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Reading the roles after adding a role returns the new role")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql" })
    public void testAddRole_CallBack() {
        final Iterable<Role> result;
        final Role           role;
        final Pageable       pageable;

        pageable = Pageable.unpaged();

        service.addRole(1L, 1L);
        result = service.getRoles(1L, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);

        role = result.iterator()
            .next();

        Assertions.assertThat(role.getName())
            .isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("Adding an existing role adds nothing")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testAddRole_Existing() {
        service.addRole(1L, 1L);

        Assertions.assertThat(userRolesRepository.count())
            .isEqualTo(1);
    }

}
