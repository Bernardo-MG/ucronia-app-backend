
package com.bernardomg.security.data.test.user.integration.service;

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
import com.bernardomg.security.data.model.DtoUser;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.service.UserService;

@IntegrationTest
@DisplayName("User service - get all")
@Sql({ "/db/queries/security/user/single.sql" })
public class ITUserServiceGetAllPagination {

    @Autowired
    private UserService service;

    public ITUserServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("Returns a page")
    public void testGetAll_Page_Container() {
        final Iterable<? extends User> result;
        final DtoUser                  sample;
        final Pageable                 pageable;

        pageable = Pageable.ofSize(10);

        sample = new DtoUser();

        result = service.getAll(sample, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    public void testGetAll_Page1_Data() {
        final DtoUser                  sample;
        final Iterator<? extends User> data;
        final User                     result;
        final Pageable                 pageable;

        pageable = PageRequest.of(0, 1);

        sample = new DtoUser();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("admin", result.getUsername());
        Assertions.assertEquals("email@somewhere.com", result.getEmail());
        Assertions.assertFalse(result.getCredentialsExpired());
        Assertions.assertTrue(result.getEnabled());
        Assertions.assertFalse(result.getExpired());
        Assertions.assertFalse(result.getLocked());
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    public void testGetAll_Page2_Data() {
        final DtoUser                  sample;
        final Iterable<? extends User> data;
        final Pageable                 pageable;

        pageable = PageRequest.of(1, 1);

        sample = new DtoUser();

        data = service.getAll(sample, pageable);

        Assertions.assertTrue(IterableUtils.isEmpty(data));
    }

    @Test
    @DisplayName("Returns the page entities")
    public void testGetAll_Paged_Count() {
        final DtoUser                  sample;
        final Iterable<? extends User> result;
        final Pageable                 pageable;

        pageable = PageRequest.of(0, 1);

        sample = new DtoUser();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns a page when the pagination is disabled")
    public void testGetAll_Unpaged_Container() {
        final Iterable<? extends User> result;
        final DtoUser                  sample;
        final Pageable                 pageable;

        pageable = Pageable.unpaged();

        sample = new DtoUser();

        result = service.getAll(sample, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

}
