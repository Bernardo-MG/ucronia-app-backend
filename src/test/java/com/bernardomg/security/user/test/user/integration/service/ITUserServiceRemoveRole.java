
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
import com.bernardomg.security.user.model.UserRole;
import com.bernardomg.security.user.persistence.repository.UserRoleRepository;
import com.bernardomg.security.user.service.UserService;

@IntegrationTest
@DisplayName("User service - remove role")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/relationship/role_permission.sql", "/db/queries/security/relationship/user_role.sql" })
class ITUserServiceRemoveRole {

    @Autowired
    private UserService        service;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public ITUserServiceRemoveRole() {
        super();
    }

    @Test
    @DisplayName("Removes the entity when removing a role")
    void testAddRole_RemovesEntity() {
        service.removeRole(1L, 1L);

        Assertions.assertThat(userRoleRepository.count())
            .isZero();
    }

    @Test
    @DisplayName("Returns the removed data")
    void testAddRole_ReturnedData() {
        final UserRole entity;

        entity = service.removeRole(1L, 1L);

        Assertions.assertThat(entity.getUserId())
            .isEqualTo(1);
        Assertions.assertThat(entity.getRoleId())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Reading the roles after removing a role doesn't return it")
    void testAddRoles_CallBack() {
        final Iterable<Role> result;
        final Pageable       pageable;

        pageable = Pageable.unpaged();

        service.removeRole(1L, 1L);
        result = service.getRoles(1L, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isZero();
    }

}
