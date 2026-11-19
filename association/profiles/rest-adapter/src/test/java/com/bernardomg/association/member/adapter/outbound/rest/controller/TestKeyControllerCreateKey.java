
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.key.adapter.outbound.rest.controller.KeyController;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.key.test.configuration.factory.Keys;
import com.bernardomg.association.key.usecase.service.KeyService;

@ExtendWith(MockitoExtension.class)
@DisplayName("KeyController - createKey")
class TestKeyControllerCreateKey {

    @InjectMocks
    private KeyController controller;

    private MockMvc       mockMvc;

    @Mock
    private KeyService    service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator;

        validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating a key with valid data, then it is accepted")
    void testCreateKey() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(Keys.available());

        requestBody = String.format("""
                {
                    "number": %d,
                    "available": true,
                    "description": "%s"
                }
                """, KeyConstants.NUMBER, KeyConstants.DESCRIPTION);

        // WHEN
        mockMvc.perform(post("/profile/key").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number", equalTo((int) KeyConstants.NUMBER)))
            .andExpect(jsonPath("$.content.description", equalTo(KeyConstants.DESCRIPTION)));
    }

}
