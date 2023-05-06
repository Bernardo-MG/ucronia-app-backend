
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
@DisplayName("Role service - set action - with action")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/relationship/role_permission.sql" })
public class ITRoleServiceRemovePermission {

    @Autowired
    private RoleService service;

    public ITRoleServiceRemovePermission() {
        super();
    }

    @Test
    @DisplayName("Reading the role action after changing action returns them")
    public void testAddAction_Change_CallBack() {
        final Iterable<? extends Action> result;
        final Collection<String>         actionNames;
        final Pageable                   pageable;

        pageable = Pageable.unpaged();

        service.removePermission(1l, 1l, 1l);
        result = service.getPermission(1l, pageable);

        Assertions.assertEquals(3L, IterableUtils.size(result));

        actionNames = StreamSupport.stream(result.spliterator(), false)
            .map(Action::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(actionNames.contains("READ_DATA"));
        Assertions.assertTrue(actionNames.contains("UPDATE_DATA"));
        Assertions.assertTrue(actionNames.contains("DELETE_DATA"));
    }

}
