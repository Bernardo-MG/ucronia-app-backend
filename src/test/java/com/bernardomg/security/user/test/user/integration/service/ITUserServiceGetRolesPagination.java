
package com.bernardomg.security.user.test.user.integration.service;

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
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.service.UserService;

@IntegrationTest
@DisplayName("User service - get roles")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/relationship/role_permission.sql", "/db/queries/security/relationship/user_role.sql" })
public class ITUserServiceGetRolesPagination {

    @Autowired
    private UserService service;

    public ITUserServiceGetRolesPagination() {
        super();
    }

    @Test
    @DisplayName("Returns the page entities")
    public void testGetActions_Page_Container() {
        final Iterable<Role> result;
        final Pageable       pageable;

        pageable = PageRequest.of(0, 1);

        result = service.getRoles(1l, pageable);

        Assertions.assertThat(result)
            .isInstanceOf(Page.class);
    }

    @Test
    @DisplayName("Returns a page")
    public void testGetActions_Paged_Count() {
        final Iterable<Role> result;
        final Pageable       pageable;

        pageable = PageRequest.of(0, 1);

        result = service.getRoles(1l, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    public void testGetAll_Page1_Data() {
        final Iterator<Role> data;
        final Role           result;
        final Pageable       pageable;

        pageable = PageRequest.of(0, 1);

        data = service.getRoles(1l, pageable)
            .iterator();

        result = data.next();
        Assertions.assertThat(result.getId())
            .isNotNull();
        Assertions.assertThat(result.getName())
            .isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    public void testGetAll_Page2_Data() {
        final Iterable<Role> data;
        final Pageable       pageable;

        pageable = PageRequest.of(1, 1);

        data = service.getRoles(1l, pageable);

        Assertions.assertThat(IterableUtils.isEmpty(data))
            .isTrue();
    }

    @Test
    @DisplayName("Returns a page when the pagination is disabled")
    public void testGetAll_Unpaged_Container() {
        final Iterable<Role> result;
        final Pageable       pageable;

        pageable = Pageable.unpaged();

        result = service.getRoles(1l, pageable);

        Assertions.assertThat(result)
            .isInstanceOf(Page.class);
    }

}
