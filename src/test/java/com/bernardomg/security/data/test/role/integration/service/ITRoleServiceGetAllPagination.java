
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
import com.bernardomg.security.data.model.ImmutableRole;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.service.RoleService;

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
        final ImmutableRole  sample;
        final Pageable       pageable;

        pageable = Pageable.ofSize(10);

        sample = ImmutableRole.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    public void testGetAll_Page1_Data() {
        final ImmutableRole  sample;
        final Iterator<Role> data;
        final Role           result;
        final Pageable       pageable;

        pageable = PageRequest.of(0, 1);

        sample = ImmutableRole.builder()
            .build();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("ADMIN", result.getName());
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    public void testGetAll_Page2_Data() {
        final ImmutableRole  sample;
        final Iterable<Role> data;
        final Pageable       pageable;

        pageable = PageRequest.of(1, 1);

        sample = ImmutableRole.builder()
            .build();

        data = service.getAll(sample, pageable);

        Assertions.assertTrue(IterableUtils.isEmpty(data));
    }

    @Test
    @DisplayName("Returns the page entities")
    public void testGetAll_Paged_Count() {
        final ImmutableRole  sample;
        final Iterable<Role> result;
        final Pageable       pageable;

        pageable = PageRequest.of(0, 1);

        sample = ImmutableRole.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns a page when the pagination is disabled")
    public void testGetAll_Unpaged_Container() {
        final Iterable<Role> result;
        final ImmutableRole  sample;
        final Pageable       pageable;

        pageable = Pageable.unpaged();

        sample = ImmutableRole.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

}
