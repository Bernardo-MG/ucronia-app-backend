package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

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

import com.bernardomg.association.member.domain.model.Key;
import com.bernardomg.association.member.usecase.service.KeyService;

@ExtendWith(MockitoExtension.class)
@DisplayName("KeyController")
class TestKeyController {

    private static final Long   KEY_NUMBER  = 100L;

    private static final String DESCRIPTION = "Main entrance key";

    private MockMvc             mockMvc;

    @Mock
    private KeyService          service;

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
    @DisplayName("When creating a key with valid data, it is accepted")
    void testCreateKey() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(new Key(KEY_NUMBER, false, DESCRIPTION));

        requestBody = String.format("""
                {
                    "number": %d,
                    "missing": false,
                    "description": "%s"
                }
                """, KEY_NUMBER, DESCRIPTION);

        // WHEN + THEN
        mockMvc.perform(post("/profile/key").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number", equalTo(KEY_NUMBER.intValue())))
            .andExpect(jsonPath("$.content.missing", equalTo(false)))
            .andExpect(jsonPath("$.content.description", equalTo(DESCRIPTION)));
    }

    @Test
    @DisplayName("When deleting a key, it is accepted")
    void testDeleteKey() throws Exception {
        // GIVEN
        given(service.delete(anyLong())).willReturn(new Key(KEY_NUMBER, false, DESCRIPTION));

        // WHEN + THEN
        mockMvc.perform(delete("/profile/key/{number}", KEY_NUMBER).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number", equalTo(KEY_NUMBER.intValue())));
    }

    @Test
    @DisplayName("When keys exist, they are returned")
    void testGetAllKeys() throws Exception {
        // GIVEN
        given(service.getAll()).willReturn(List.of(new Key(KEY_NUMBER, false, DESCRIPTION)));

        // WHEN + THEN
        mockMvc.perform(get("/profile/key").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content[0].number", equalTo(KEY_NUMBER.intValue())));
    }

    @Test
    @DisplayName("When the key exists, it is returned")
    void testGetKeyByNumber() throws Exception {
        // GIVEN
        given(service.getOne(KEY_NUMBER)).willReturn(Optional.of(new Key(KEY_NUMBER, false, DESCRIPTION)));

        // WHEN + THEN
        mockMvc.perform(get("/profile/key/{number}", KEY_NUMBER).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number", equalTo(KEY_NUMBER.intValue())));
    }

    @Test
    @DisplayName("When updating a key with valid data, it is accepted")
    void testUpdateKey() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(any())).willReturn(new Key(KEY_NUMBER, true, "Missing key"));

        requestBody = """
                {
                    "missing": true,
                    "description": "Missing key"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/profile/key/{number}", KEY_NUMBER).contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number", equalTo(KEY_NUMBER.intValue())))
            .andExpect(jsonPath("$.content.missing", equalTo(true)))
            .andExpect(jsonPath("$.content.description", equalTo("Missing key")));
    }

}
