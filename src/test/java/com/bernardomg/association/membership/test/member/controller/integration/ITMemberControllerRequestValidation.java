/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.membership.test.member.controller.integration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bernardomg.association.member.model.MemberChange;
import com.bernardomg.association.membership.test.member.config.factory.MemberChanges;
import com.bernardomg.association.test.config.constant.TestUrls;
import com.bernardomg.test.config.annotation.MvcIntegrationTest;
import com.google.gson.Gson;

@MvcIntegrationTest
@AutoConfigureMockMvc
@DisplayName("Member controller - request validation")
class ITMemberControllerRequestValidation {

    private final Gson gson = new Gson();

    @Autowired
    private MockMvc    mockMvc;

    public ITMemberControllerRequestValidation() {
        super();
    }

    private final RequestBuilder getPostRequest(final MemberChange member) {
        final String json;

        json = gson.toJson(member);
        return MockMvcRequestBuilders.post(TestUrls.MEMBER)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json);
    }

    @Test
    @DisplayName("With a valid member, returns the created member")
    void testPost_Full_Valid() throws Exception {
        final ResultActions result;
        final MemberChange  member;

        member = MemberChanges.valid();

        result = mockMvc.perform(getPostRequest(member));

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isCreated());

        // The response model contains the expected attributes
        result.andExpect(
            MockMvcResultMatchers.jsonPath("$.content.name.firstName", Matchers.comparesEqualTo("Member 1")));
    }

    @Test
    @DisplayName("With a member missing the name, returns a bad request response")
    @Disabled("The model rejects this case")
    void testPost_MissingName_Invalid() throws Exception {
        final ResultActions result;
        final MemberChange  member;

        member = MemberChanges.missingName();

        result = mockMvc.perform(getPostRequest(member));

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isBadRequest());
    }

}
