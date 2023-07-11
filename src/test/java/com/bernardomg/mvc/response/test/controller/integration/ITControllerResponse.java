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

package com.bernardomg.mvc.response.test.controller.integration;

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
import com.bernardomg.mvc.response.test.controller.util.ResponseTestController;

@MvcIntegrationTest
@DisplayName("Controller advices - response structure")
class ITControllerResponse {

    @Autowired
    private MockMvc mockMvc;

    public ITControllerResponse() {
        super();
    }

    private final RequestBuilder getCollectionRequest() {
        return MockMvcRequestBuilders.get(ResponseTestController.PATH_COLLECTION)
            .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("Returns the response structure when returning a collection")
    void testResponseStructure_Collection() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(getCollectionRequest());

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response model has content
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1)));

        // The response contains no pagination properties
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

        // The response contains no error attribute
        result.andExpect(MockMvcResultMatchers.jsonPath("$.errors")
            .doesNotExist());
    }

}
