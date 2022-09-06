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

package com.bernardomg.mvc.response.test.unit.controller;

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
import com.bernardomg.mvc.response.test.util.controller.ResponseTestController;

@MvcIntegrationTest
@DisplayName("Controller advices - response structure")
public final class ITControllerResponse {

    @Autowired
    private MockMvc mockMvc;

    public ITControllerResponse() {
        super();
    }

    @Test
    @DisplayName("Returns the response structure when returning a collection")
    public final void testResponseStructure_Collection_Response() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(getCollectionRequest());

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response model contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content")
            .exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)));

        // The response contains no pagination attribute
        result.andExpect(MockMvcResultMatchers.jsonPath("$.page")
            .doesNotExist());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.size")
            .doesNotExist());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.elementsInPage")
            .doesNotExist());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements")
            .doesNotExist());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.totalPages")
            .doesNotExist());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.first")
            .doesNotExist());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.last")
            .doesNotExist());
    }

    @Test
    @DisplayName("Returns the paginated response structure when returning a page")
    public final void testResponseStructure_Page_Response() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(getPageRequest());

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response model contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content")
            .exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(5)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.page", Matchers.equalTo(0)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.size", Matchers.equalTo(5)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.elementsInPage", Matchers.equalTo(5)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.equalTo(20)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.totalPages", Matchers.equalTo(4)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.first", Matchers.equalTo(true)));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.last", Matchers.equalTo(false)));
    }

    private final RequestBuilder getCollectionRequest() {
        return MockMvcRequestBuilders.get(ResponseTestController.PATH_COLLECTION)
            .contentType(MediaType.APPLICATION_JSON);
    }

    private final RequestBuilder getPageRequest() {
        return MockMvcRequestBuilders.get(ResponseTestController.PATH_PAGE)
            .contentType(MediaType.APPLICATION_JSON);
    }

}
