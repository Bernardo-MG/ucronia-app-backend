
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
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.request.RoleQuery;
import com.bernardomg.security.user.service.RoleService;
import com.bernardomg.security.user.test.util.model.RolesQuery;

@IntegrationTest
@DisplayName("Role service - get all")
@Sql({ "/db/queries/security/role/single.sql" })
public class ITRoleServiceGetAllPagination {

    @Autowired
    private RoleService service;

    public ITRoleServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("Returns a page")
    public void testGetAll_Page_Container() {
        final Iterable<Role> result;
        final RoleQuery      sample;
        final Pageable       pageable;

        pageable = Pageable.ofSize(10);

        sample = RolesQuery.empty();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(result)
            .isInstanceOf(Page.class);
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    public void testGetAll_Page1_Data() {
        final RoleQuery      sample;
        final Iterator<Role> data;
        final Role           result;
        final Pageable       pageable;

        pageable = PageRequest.of(0, 1);

        sample = RolesQuery.empty();

        data = service.getAll(sample, pageable)
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
        final RoleQuery      sample;
        final Iterable<Role> data;
        final Pageable       pageable;

        pageable = PageRequest.of(1, 1);

        sample = RolesQuery.empty();

        data = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.isEmpty(data))
            .isTrue();
    }

    @Test
    @DisplayName("Returns the page entities")
    public void testGetAll_Paged_Count() {
        final RoleQuery      sample;
        final Iterable<Role> result;
        final Pageable       pageable;

        pageable = PageRequest.of(0, 1);

        sample = RolesQuery.empty();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Returns a page when the pagination is disabled")
    public void testGetAll_Unpaged_Container() {
        final Iterable<Role> result;
        final RoleQuery      sample;
        final Pageable       pageable;

        pageable = Pageable.unpaged();

        sample = RolesQuery.empty();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(result)
            .isInstanceOf(Page.class);
    }

}
