
package com.bernardomg.association.library.publisher.test.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
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

import com.bernardomg.association.library.publisher.adapter.outbound.rest.controller.PublisherController;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherConstants;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;
import com.bernardomg.association.library.publisher.usecase.service.PublisherService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublisherController")
class TestPublisherController {

    private MockMvc          mockMvc;

    @Mock
    private PublisherService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setValidationPropertyMap(Map.of("hibernate.validator.allow_parameter_constraint_override", "true"));
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new PublisherController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating a publisher with valid data, it is accepted")
    void testCreatePublisher() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(Publishers.valid());

        requestBody = """
                {
                    "name": "Publisher"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/publisher").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", equalTo(PublisherConstants.NAME)))
            .andExpect(jsonPath("$.content.number").exists());
    }

    @Test
    @DisplayName("When the publisher is deleted, it is accepted")
    void testDeletePublisher() throws Exception {
        // GIVEN
        given(service.delete(1L)).willReturn(Publishers.valid());

        // WHEN + THEN
        mockMvc.perform(delete("/library/publisher/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.name").exists());
    }

    @Test
    @DisplayName("When there are publishers, they are returned")
    void testGetAllPublishers() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(Publishers.valid()), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/library/publisher").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("When the publisher exists, it is returned")
    void testGetPublisherById() throws Exception {
        // GIVEN
        given(service.getOne(1L)).willReturn(Optional.of(Publishers.valid()));

        // WHEN + THEN
        mockMvc.perform(get("/library/publisher/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.name").exists());
    }

    @Test
    @DisplayName("When updating a valid publisher, it is accepted")
    void testUpdatePublisher() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(any())).willReturn(Publishers.nameChange());

        requestBody = """
                {
                    "name": "Publisher 123"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/publisher/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", equalTo(PublisherConstants.CHANGED_NAME)));
    }

}
