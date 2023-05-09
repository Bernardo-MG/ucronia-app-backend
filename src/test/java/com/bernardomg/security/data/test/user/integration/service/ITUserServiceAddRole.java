
package com.bernardomg.security.data.test.user.integration.service;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.persistence.model.PersistentUserRoles;
import com.bernardomg.security.data.persistence.repository.UserRolesRepository;
import com.bernardomg.security.data.service.UserService;

@IntegrationTest
@DisplayName("User service - add role")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/relationship/role_permission.sql" })
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
    public void testAddRole_AddsEntity() {
        service.addRole(1L, 1L);

        Assertions.assertEquals(1L, userRolesRepository.count());
    }

    @Test
    @DisplayName("Reading the roles after adding a role returns the new role")
    public void testAddRole_CallBack() {
        final Iterable<? extends Role> result;
        final Role                     role;
        final Pageable                 pageable;

        pageable = Pageable.unpaged();

        service.addRole(1L, 1L);
        result = service.getRoles(1L, pageable);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        role = result.iterator()
            .next();

        Assertions.assertEquals("ADMIN", role.getName());
    }

    @Test
    @DisplayName("Persists the data")
    public void testAddRole_PersistedData() {
        final PersistentUserRoles entity;

        service.addRole(1L, 1L);

        entity = userRolesRepository.findAll()
            .iterator()
            .next();

        Assertions.assertEquals(1L, entity.getUserId());
        Assertions.assertEquals(1L, entity.getRoleId());
    }

}
