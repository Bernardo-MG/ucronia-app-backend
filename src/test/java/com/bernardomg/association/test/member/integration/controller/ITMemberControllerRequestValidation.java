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

package com.bernardomg.association.test.member.integration.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bernardomg.association.member.model.request.MemberCreate;
import com.bernardomg.association.test.config.annotation.MvcIntegrationTest;
import com.bernardomg.association.test.config.constant.TestUrls;
import com.bernardomg.association.test.member.util.model.MembersCreate;
import com.google.gson.Gson;

@MvcIntegrationTest
@DisplayName("Member controller - request validation")
public final class ITMemberControllerRequestValidation {

    private final Gson gson = new Gson();

    @Autowired
    private MockMvc    mockMvc;

    public ITMemberControllerRequestValidation() {
        super();
    }

    @Test
    @DisplayName("With a valid member, returns the created member")
    public final void testPost_Full_Valid() throws Exception {
        final ResultActions result;
        final MemberCreate  member;

        member = MembersCreate.active();

        result = mockMvc.perform(getPostRequest(member));

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response model contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content.name", Matchers.comparesEqualTo("Member")));
    }

    @Test
    @DisplayName("With a member missing the name, returns a bad request response")
    @Disabled("The model rejects this case")
    public final void testPost_NoName_Invalid() throws Exception {
        final ResultActions result;
        final MemberCreate  member;

        member = MembersCreate.missingName();

        result = mockMvc.perform(getPostRequest(member));

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isBadRequest());
    }

    private final RequestBuilder getPostRequest(final MemberCreate member) {
        final String json;

        json = gson.toJson(member);
        return MockMvcRequestBuilders.post(TestUrls.MEMBER)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json);
    }

}
