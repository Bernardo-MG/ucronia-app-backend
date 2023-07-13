
package com.bernardomg.security.user.test.role.integration.service;

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.Permission;
import com.bernardomg.security.user.service.RoleService;

@IntegrationTest
@DisplayName("Role service - get permissions pagination")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/relationship/role_permission.sql" })
class ITRoleServiceGetPermissionsPagination {

    @Autowired
    private RoleService service;

    public ITRoleServiceGetPermissionsPagination() {
        super();
    }

    @Test
    @DisplayName("Returns the page entities")
    void testGetPermissions_Page_Container() {
        final Iterable<Permission> result;
        final Pageable             pageable;

        pageable = PageRequest.of(0, 1);

        result = service.getPermissions(1l, pageable);

        Assertions.assertThat(result)
            .isInstanceOf(Page.class);
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    void testGetPermissions_Page1_Data() {
        final Iterator<Permission> data;
        final Permission           result;
        final Pageable             pageable;

        pageable = PageRequest.of(0, 1);

        data = service.getPermissions(1l, pageable)
            .iterator();

        result = data.next();
        Assertions.assertThat(result.getResource())
            .isEqualTo("DATA");
        Assertions.assertThat(result.getAction())
            .isEqualTo("CREATE");
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    void testGetPermissions_Page2_Data() {
        final Iterator<Permission> data;
        final Permission           result;
        final Pageable             pageable;

        pageable = PageRequest.of(1, 1);

        data = service.getPermissions(1l, pageable)
            .iterator();

        result = data.next();
        Assertions.assertThat(result.getResource())
            .isEqualTo("DATA");
        Assertions.assertThat(result.getAction())
            .isEqualTo("READ");
    }

    @Test
    @DisplayName("Returns a page")
    void testGetPermissions_Paged_Count() {
        final Iterable<Permission> result;
        final Pageable             pageable;

        pageable = PageRequest.of(0, 1);

        result = service.getPermissions(1l, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Returns a page when the pagination is disabled")
    void testGetPermissions_Unpaged_Container() {
        final Iterable<Permission> result;
        final Pageable             pageable;

        pageable = Pageable.unpaged();

        result = service.getPermissions(1l, pageable);

        Assertions.assertThat(result)
            .isInstanceOf(Page.class);
    }

}
