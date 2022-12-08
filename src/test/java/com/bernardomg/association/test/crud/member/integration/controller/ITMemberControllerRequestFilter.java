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

package com.bernardomg.association.test.crud.member.integration.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bernardomg.association.test.config.annotation.MvcIntegrationTest;
import com.bernardomg.association.test.config.constant.TestUrls;

@MvcIntegrationTest
@DisplayName("Member controller - filtering")
@Sql({ "/db/queries/member/multiple.sql" })
public final class ITMemberControllerRequestFilter {

    @Autowired
    private MockMvc mockMvc;

    public ITMemberControllerRequestFilter() {
        super();
    }

    @Test
    @DisplayName("Filters by active")
    public final void testGet_Active() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(getGetRequestActive());

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response model contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(4)));
    }

    @Test
    @DisplayName("Reads all when not using a filter")
    public final void testGet_NoFilter() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(getGetRequest());

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response model contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(5)));
    }

    @Test
    @DisplayName("Filters by not active")
    public final void testGet_NotActive() throws Exception {
        final ResultActions result;

        result = mockMvc.perform(getGetRequestNotActive());

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response model contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(1)));
    }

    private final RequestBuilder getGetRequest() {
        return MockMvcRequestBuilders.get(TestUrls.MEMBER);
    }

    private final RequestBuilder getGetRequestActive() {
        return MockMvcRequestBuilders.get(TestUrls.MEMBER)
            .param("active", "true");
    }

    private final RequestBuilder getGetRequestNotActive() {
        return MockMvcRequestBuilders.get(TestUrls.MEMBER)
            .param("active", "false");
    }

}
