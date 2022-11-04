
package com.bernardomg.security.data.test.role;

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
import com.bernardomg.security.data.model.Privilege;
import com.bernardomg.security.data.service.RoleService;

@IntegrationTest
@DisplayName("Role service - get privileges")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/relationship/role_privilege.sql" })
public class ITRoleServiceGetPrivilegesPagination {

    @Autowired
    private RoleService service;

    public ITRoleServiceGetPrivilegesPagination() {
        super();
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    public void testGetAll_Page1_Data() {
        final Iterator<? extends Privilege> data;
        final Privilege                     result;
        final Pageable                      pageable;

        pageable = PageRequest.of(0, 1);

        data = service.getPrivileges(1l, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("CREATE_DATA", result.getName());
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    public void testGetAll_Page2_Data() {
        final Iterator<? extends Privilege> data;
        final Privilege                     result;
        final Pageable                      pageable;

        pageable = PageRequest.of(1, 1);

        data = service.getPrivileges(1l, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("READ_DATA", result.getName());
    }

    @Test
    @DisplayName("Returns a page when the pagination is disabled")
    public void testGetAll_Unpaged_Container() {
        final Iterable<? extends Privilege> result;
        final Pageable                      pageable;

        pageable = Pageable.unpaged();

        result = service.getPrivileges(1l, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

    @Test
    @DisplayName("Returns the page entities")
    public void testGetPrivileges_Page_Container() {
        final Iterable<? extends Privilege> result;
        final Pageable                      pageable;

        pageable = PageRequest.of(0, 1);

        result = service.getPrivileges(1l, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

    @Test
    @DisplayName("Returns a page")
    public void testGetPrivileges_Paged_Count() {
        final Iterable<? extends Privilege> result;
        final Pageable                      pageable;

        pageable = PageRequest.of(0, 1);

        result = service.getPrivileges(1l, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

}
