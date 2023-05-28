
package com.bernardomg.security.data.test.resource.integration.service;

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
import com.bernardomg.security.data.model.Resource;
import com.bernardomg.security.data.model.request.DtoResourceQueryRequest;
import com.bernardomg.security.data.model.request.ResourceQueryRequest;
import com.bernardomg.security.data.service.ResourceService;

@IntegrationTest
@DisplayName("Resource service - get all")
@Sql({ "/db/queries/security/resource/multiple.sql" })
public class ITResourceServiceGetAllPagination {

    @Autowired
    private ResourceService service;

    public ITResourceServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("Returns a page")
    public void testGetAll_Page_Container() {
        final Iterable<Resource>   result;
        final ResourceQueryRequest sample;
        final Pageable             pageable;

        pageable = Pageable.ofSize(10);

        sample = DtoResourceQueryRequest.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    public void testGetAll_Page1_Data() {
        final ResourceQueryRequest sample;
        final Iterator<Resource>   data;
        final Resource             result;
        final Pageable             pageable;

        pageable = PageRequest.of(0, 1);

        sample = DtoResourceQueryRequest.builder()
            .build();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("DATA1", result.getName());
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    public void testGetAll_Page2_Data() {
        final ResourceQueryRequest sample;
        final Iterator<Resource>   data;
        final Resource             result;
        final Pageable             pageable;

        pageable = PageRequest.of(1, 1);

        sample = DtoResourceQueryRequest.builder()
            .build();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("DATA2", result.getName());
    }

    @Test
    @DisplayName("Returns the page entities")
    public void testGetAll_Paged_Count() {
        final ResourceQueryRequest sample;
        final Iterable<Resource>   result;
        final Pageable             pageable;

        pageable = PageRequest.of(0, 1);

        sample = DtoResourceQueryRequest.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns a page when the pagination is disabled")
    public void testGetAll_Unpaged_Container() {
        final Iterable<Resource>   result;
        final ResourceQueryRequest sample;
        final Pageable             pageable;

        pageable = Pageable.unpaged();

        sample = DtoResourceQueryRequest.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

}
