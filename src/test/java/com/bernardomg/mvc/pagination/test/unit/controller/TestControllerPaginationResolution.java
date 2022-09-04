/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.mvc.pagination.test.unit.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bernardomg.mvc.pagination.argument.PaginationArgumentResolver;
import com.bernardomg.mvc.pagination.argument.SortArgumentResolver;
import com.bernardomg.mvc.pagination.model.Direction;
import com.bernardomg.mvc.pagination.model.Pagination;
import com.bernardomg.mvc.pagination.model.Sort;
import com.bernardomg.mvc.pagination.test.tool.controller.PaginationTestingController;
import com.bernardomg.mvc.pagination.test.tool.service.PaginationTestingService;

@DisplayName("Pagination arguments resolution on controllers")
public final class TestControllerPaginationResolution {

    private MockMvc                    mockMvc;

    private ArgumentCaptor<Pagination> paginationCaptor;

    private ArgumentCaptor<Sort>       sortCaptor;

    public TestControllerPaginationResolution() {
        super();
    }

    @BeforeEach
    public final void setUpMockContext() {
        mockMvc = MockMvcBuilders.standaloneSetup(getController())
            .setCustomArgumentResolvers(new PaginationArgumentResolver(), new SortArgumentResolver())
            .alwaysExpect(MockMvcResultMatchers.status()
                .isOk())
            .alwaysExpect(MockMvcResultMatchers.content()
                .contentType(MediaType.APPLICATION_JSON))
            .build();
    }

    @Test
    @DisplayName("A request with no pagination data returns default pagination and sort flags")
    public final void testGet_NoPagination_DefaultFlags() throws Exception {
        final ResultActions result;
        final Pagination    pagination;
        final Sort          sort;

        result = mockMvc.perform(getGetRequest());

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The pagination data was loaded
        pagination = paginationCaptor.getValue();
        Assertions.assertTrue(pagination.getPaged());

        // The sort data was loaded
        sort = sortCaptor.getValue();
        Assertions.assertFalse(sort.getSorted());
    }

    @Test
    @DisplayName("A request with pagination data returns pagination with the received arguments")
    public final void testGet_Pagination_Values() throws Exception {
        final ResultActions result;
        final Pagination    pagination;
        final Sort          sort;

        result = mockMvc.perform(getGetPagedRequest());

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The pagination data was loaded
        pagination = paginationCaptor.getValue();
        Assertions.assertTrue(pagination.getPaged());
        Assertions.assertEquals(123, pagination.getPage());
        Assertions.assertEquals(456, pagination.getSize());

        // The sort data was loaded
        sort = sortCaptor.getValue();
        Assertions.assertFalse(sort.getSorted());
    }

    @Test
    @DisplayName("A request with sort data returns a sort with the received arguments")
    public final void testGet_Sort_Values() throws Exception {
        final ResultActions result;
        final Pagination    pagination;
        final Sort          sort;

        result = mockMvc.perform(getGetSortedRequest());

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The pagination data was loaded
        pagination = paginationCaptor.getValue();
        Assertions.assertTrue(pagination.getPaged());

        // The sort data was loaded
        sort = sortCaptor.getValue();
        Assertions.assertTrue(sort.getSorted());
        Assertions.assertEquals("field", sort.getProperty());
        Assertions.assertEquals(Direction.DESC, sort.getDirection());
    }

    /**
     * Returns a controller with mocked dependencies.
     *
     * @return a controller with mocked dependencies
     */
    private final PaginationTestingController getController() {
        final PaginationTestingService service;
        final Collection<String>       data;

        service = Mockito.mock(PaginationTestingService.class);

        data = new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");

        paginationCaptor = ArgumentCaptor.forClass(Pagination.class);
        sortCaptor = ArgumentCaptor.forClass(Sort.class);

        Mockito.when(service.getAll(paginationCaptor.capture(), sortCaptor.capture()))
            .thenReturn(data);

        return new PaginationTestingController(service);
    }

    private final RequestBuilder getGetPagedRequest() {
        return MockMvcRequestBuilders.get(PaginationTestingController.URL)
            .queryParam("page", "123")
            .queryParam("size", "456")
            .contentType(MediaType.APPLICATION_JSON);
    }

    /**
     * Returns a request builder prepared for reading entities.
     *
     * @return a request builder prepared for reading entities
     */
    private final RequestBuilder getGetRequest() {
        return MockMvcRequestBuilders.get(PaginationTestingController.URL)
            .contentType(MediaType.APPLICATION_JSON);
    }

    private final RequestBuilder getGetSortedRequest() {
        return MockMvcRequestBuilders.get(PaginationTestingController.URL)
            .queryParam("property", "field")
            .queryParam("direction", "desc")
            .contentType(MediaType.APPLICATION_JSON);
    }

}
