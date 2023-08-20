
package com.bernardomg.security.permission.test.resource.integration.service;

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

import com.bernardomg.security.permission.service.ResourceService;
import com.bernardomg.security.user.model.Resource;
import com.bernardomg.security.user.model.request.ResourceQuery;
import com.bernardomg.security.user.model.request.ValidatedResourceQuery;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Resource service - get all")
@Sql({ "/db/queries/security/resource/multiple.sql" })
class ITResourceServiceGetAllPagination {

    @Autowired
    private ResourceService service;

    public ITResourceServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("Returns a page")
    void testGetAll_Page_Container() {
        final Iterable<Resource> result;
        final ResourceQuery      sample;
        final Pageable           pageable;

        pageable = Pageable.ofSize(10);

        sample = ValidatedResourceQuery.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(result)
            .isInstanceOf(Page.class);
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    void testGetAll_Page1_Data() {
        final ResourceQuery      sample;
        final Iterator<Resource> data;
        final Resource           result;
        final Pageable           pageable;

        pageable = PageRequest.of(0, 1);

        sample = ValidatedResourceQuery.builder()
            .build();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertThat(result.getId())
            .isNotNull();
        Assertions.assertThat(result.getName())
            .isEqualTo("DATA1");
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    void testGetAll_Page2_Data() {
        final ResourceQuery      sample;
        final Iterator<Resource> data;
        final Resource           result;
        final Pageable           pageable;

        pageable = PageRequest.of(1, 1);

        sample = ValidatedResourceQuery.builder()
            .build();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertThat(result.getId())
            .isNotNull();
        Assertions.assertThat(result.getName())
            .isEqualTo("DATA2");
    }

    @Test
    @DisplayName("Returns the page entities")
    void testGetAll_Paged_Count() {
        final ResourceQuery      sample;
        final Iterable<Resource> result;
        final Pageable           pageable;

        pageable = PageRequest.of(0, 1);

        sample = ValidatedResourceQuery.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Returns a page when the pagination is disabled")
    void testGetAll_Unpaged_Container() {
        final Iterable<Resource> result;
        final ResourceQuery      sample;
        final Pageable           pageable;

        pageable = Pageable.unpaged();

        sample = ValidatedResourceQuery.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(result)
            .isInstanceOf(Page.class);
    }

}
