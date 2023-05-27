
package com.bernardomg.security.data.test.action.integration.service;

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
import com.bernardomg.security.data.model.Action;
import com.bernardomg.security.data.model.DtoAction;
import com.bernardomg.security.data.service.ActionService;

@IntegrationTest
@DisplayName("Action service - get all")
@Sql({ "/db/queries/security/action/crud.sql" })
public class ITActionServiceGetAllPagination {

    @Autowired
    private ActionService service;

    public ITActionServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("Returns a page")
    public void testGetAll_Page_Container() {
        final Iterable<Action> result;
        final DtoAction        sample;
        final Pageable         pageable;

        pageable = Pageable.ofSize(10);

        sample = new DtoAction();

        result = service.getAll(sample, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    public void testGetAll_Page1_Data() {
        final DtoAction        sample;
        final Iterator<Action> data;
        final Action           result;
        final Pageable         pageable;

        pageable = PageRequest.of(0, 1);

        sample = new DtoAction();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("CREATE", result.getName());
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    public void testGetAll_Page2_Data() {
        final DtoAction        sample;
        final Iterator<Action> data;
        final Action           result;
        final Pageable         pageable;

        pageable = PageRequest.of(1, 1);

        sample = new DtoAction();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("DELETE", result.getName());
    }

    @Test
    @DisplayName("Returns the page entities")
    public void testGetAll_Paged_Count() {
        final DtoAction        sample;
        final Iterable<Action> result;
        final Pageable         pageable;

        pageable = PageRequest.of(0, 1);

        sample = new DtoAction();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns a page when the pagination is disabled")
    public void testGetAll_Unpaged_Container() {
        final Iterable<Action> result;
        final DtoAction        sample;
        final Pageable         pageable;

        pageable = Pageable.unpaged();

        sample = new DtoAction();

        result = service.getAll(sample, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

}
