
package com.bernardomg.security.data.test.user;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
@DisplayName("User service - set roles - with role")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql" })
public class ITUserServiceAddRoleWithRoles {

    @Autowired
    private UserService service;

    public ITUserServiceAddRoleWithRoles() {
        super();
    }

    @Test
    @DisplayName("Reading the roles after adding a role returns them")
    public void testAddRoles_Change_CallBack() {
        final Iterable<Role>     result;
        final Collection<String> roleNames;
        final Pageable           pageable;

        pageable = Pageable.unpaged();

        service.addRole(1l, 1l);
        result = service.getRoles(1l, pageable);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        roleNames = StreamSupport.stream(result.spliterator(), false)
            .map(Role::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(roleNames.contains("ADMIN"));
    }

}
