
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
import com.bernardomg.security.user.model.ImmutableUser;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.request.DtoUserQueryRequest;
import com.bernardomg.security.user.model.request.UserQueryRequest;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.security.user.test.assertion.UserAssertions;

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
        final Iterable<User>   result;
        final UserQueryRequest sample;
        final Pageable         pageable;

        pageable = Pageable.ofSize(10);

        sample = DtoUserQueryRequest.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(result)
            .isInstanceOf(Page.class);
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    public void testGetAll_Page1_Data() {
        final UserQueryRequest sample;
        final Iterator<User>   data;
        final User             result;
        final Pageable         pageable;

        pageable = PageRequest.of(0, 1);

        sample = DtoUserQueryRequest.builder()
            .build();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        UserAssertions.isEqualTo(result, ImmutableUser.builder()
            .username("admin")
            .name("Admin")
            .email("email@somewhere.com")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build());
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    public void testGetAll_Page2_Data() {
        final UserQueryRequest sample;
        final Iterable<User>   data;
        final Pageable         pageable;

        pageable = PageRequest.of(1, 1);

        sample = DtoUserQueryRequest.builder()
            .build();

        data = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.isEmpty(data))
            .isTrue();
    }

    @Test
    @DisplayName("Returns the page entities")
    public void testGetAll_Paged_Count() {
        final UserQueryRequest sample;
        final Iterable<User>   result;
        final Pageable         pageable;

        pageable = PageRequest.of(0, 1);

        sample = DtoUserQueryRequest.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Returns a page when the pagination is disabled")
    public void testGetAll_Unpaged_Container() {
        final Iterable<User>   result;
        final UserQueryRequest sample;
        final Pageable         pageable;

        pageable = Pageable.unpaged();

        sample = DtoUserQueryRequest.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(result)
            .isInstanceOf(Page.class);
    }

}
