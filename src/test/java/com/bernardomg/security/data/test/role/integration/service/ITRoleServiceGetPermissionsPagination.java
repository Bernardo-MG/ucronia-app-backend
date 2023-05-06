
package com.bernardomg.security.data.test.role.integration.service;

import java.util.Iterator;

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
import com.bernardomg.security.data.service.RoleService;

@IntegrationTest
@DisplayName("Role service - get permissions pagination")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/relationship/role_permission.sql" })
public class ITRoleServiceGetPermissionsPagination {

    @Autowired
    private RoleService service;

    public ITRoleServiceGetPermissionsPagination() {
        super();
    }

    @Test
    @DisplayName("Returns the page entities")
    public void testGetPermissions_Page_Container() {
        final Iterable<? extends Permission> result;
        final Pageable                       pageable;

        pageable = PageRequest.of(0, 1);

        result = service.getPermission(1l, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    public void testGetPermissions_Page1_Data() {
        final Iterator<? extends Permission> data;
        final Permission                     result;
        final Pageable                       pageable;

        pageable = PageRequest.of(0, 1);

        data = service.getPermission(1l, pageable)
            .iterator();

        result = data.next();
        Assertions.assertEquals("DATA", result.getResource());
        Assertions.assertEquals("CREATE", result.getAction());
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    public void testGetPermissions_Page2_Data() {
        final Iterator<? extends Permission> data;
        final Permission                     result;
        final Pageable                       pageable;

        pageable = PageRequest.of(1, 1);

        data = service.getPermission(1l, pageable)
            .iterator();

        result = data.next();
        Assertions.assertEquals("DATA", result.getResource());
        Assertions.assertEquals("READ", result.getAction());
    }

    @Test
    @DisplayName("Returns a page")
    public void testGetPermissions_Paged_Count() {
        final Iterable<? extends Permission> result;
        final Pageable                       pageable;

        pageable = PageRequest.of(0, 1);

        result = service.getPermission(1l, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns a page when the pagination is disabled")
    public void testGetPermissions_Unpaged_Container() {
        final Iterable<? extends Permission> result;
        final Pageable                       pageable;

        pageable = Pageable.unpaged();

        result = service.getPermission(1l, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

}
