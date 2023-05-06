
package com.bernardomg.security.data.test.role.integration.service;

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
import com.bernardomg.security.data.model.Action;
import com.bernardomg.security.data.service.RoleService;

@IntegrationTest
@DisplayName("Role service - get action")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/relationship/role_permission.sql" })
public class ITRoleServiceGetPermissions {

    @Autowired
    private RoleService service;

    public ITRoleServiceGetPermissions() {
        super();
    }

    @Test
    @DisplayName("Returns the action for a role")
    public void testGetActions() {
        final Iterable<? extends Action> result;
        final Collection<String>         action;
        final Pageable                   pageable;

        pageable = Pageable.unpaged();

        result = service.getPermission(1l, pageable);

        Assertions.assertEquals(4L, IterableUtils.size(result));

        action = StreamSupport.stream(result.spliterator(), false)
            .map(Action::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(action.contains("CREATE_DATA"));
        Assertions.assertTrue(action.contains("READ_DATA"));
        Assertions.assertTrue(action.contains("UPDATE_DATA"));
        Assertions.assertTrue(action.contains("DELETE_DATA"));
    }

    @Test
    @DisplayName("Returns no action for a not existing role")
    public void testGetActions_NotExisting() {
        final Iterable<? extends Action> result;
        final Pageable                   pageable;

        pageable = Pageable.unpaged();

        result = service.getPermission(-1l, pageable);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

}
