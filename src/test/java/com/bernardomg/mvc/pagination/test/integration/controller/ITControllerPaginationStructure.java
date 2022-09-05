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

package com.bernardomg.mvc.pagination.test.integration.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bernardomg.association.test.config.annotation.MvcIntegrationTest;
import com.bernardomg.mvc.pagination.model.Direction;
import com.bernardomg.mvc.pagination.test.tool.controller.PaginationTestingController;

@MvcIntegrationTest
@DisplayName("Pagination arguments resolution on controllers with full context")
public final class ITControllerPaginationStructure {

    @Autowired
    private MockMvc mockMvc;

    public ITControllerPaginationStructure() {
        super();
    }

    @Test
    @DisplayName("Handles pagination arguments")
    public final void testGet_Pagination() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(getGetPaginationRequest());

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response model contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content.page", Matchers.comparesEqualTo(123)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content.size", Matchers.comparesEqualTo(456)));
    }

    @Test
    @DisplayName("Handles sort arguments")
    public final void testGet_Sort() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(getGetSortRequest());

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response model contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content.property", Matchers.comparesEqualTo("field")));
        result.andExpect(
            MockMvcResultMatchers.jsonPath("$.content.direction", Matchers.comparesEqualTo(Direction.DESC.toString())));
    }

    private final RequestBuilder getGetPaginationRequest() {
        return MockMvcRequestBuilders.get(PaginationTestingController.URL + "/pass/pagination")
            .queryParam("page", "123")
            .queryParam("size", "456")
            .contentType(MediaType.APPLICATION_JSON);
    }

    private final RequestBuilder getGetSortRequest() {
        return MockMvcRequestBuilders.get(PaginationTestingController.URL + "/pass/sort")
            .queryParam("property", "field")
            .queryParam("direction", "desc")
            .contentType(MediaType.APPLICATION_JSON);
    }

}
