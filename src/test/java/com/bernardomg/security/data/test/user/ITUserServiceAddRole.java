
package com.bernardomg.security.data.test.user;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.service.UserService;

@IntegrationTest
@DisplayName("User service - set roles")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql" })
public class ITUserServiceAddRole {

    @Autowired
    private UserService service;

    public ITUserServiceAddRole() {
        super();
    }

    @Test
    @DisplayName("Reading the roles after adding a role returns them")
    public void testAddRoles_CallBack() {
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

}
