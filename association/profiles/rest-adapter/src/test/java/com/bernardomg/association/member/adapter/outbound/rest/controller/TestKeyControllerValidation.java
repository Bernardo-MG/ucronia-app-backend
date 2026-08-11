package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.member.usecase.service.KeyService;

@ExtendWith(MockitoExtension.class)
@DisplayName("KeyController - Validation")
class TestKeyControllerValidation {

    private MockMvc  mockMvc;

    @Mock
    private KeyService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new KeyController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating a key without number, it is rejected")
    void testCreateKey_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "missing": false,
                    "description": "Main entrance"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/profile/key").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating a key without required fields, it is rejected")
    void testUpdateKey_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/profile/key/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

}
