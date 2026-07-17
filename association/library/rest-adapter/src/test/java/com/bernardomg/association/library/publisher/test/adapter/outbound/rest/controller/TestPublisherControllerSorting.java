
package com.bernardomg.association.library.publisher.test.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

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
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;
import com.bernardomg.association.library.publisher.usecase.service.PublisherService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublisherController - Sorting")
class TestPublisherControllerSorting {

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
    @DisplayName("When sorting by name descending, it is accepted")
    void testGetAllPublishers_NameAsc() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(Publishers.valid()), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/library/publisher").param("page", "1")
            .param("size", "10")
            .param("sort", "name|desc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("When sorting by name ascending, it is accepted")
    void testGetAllPublishers_NameDesc() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(Publishers.valid()), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/library/publisher").param("page", "1")
            .param("size", "10")
            .param("sort", "name|asc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content", isA(java.util.ArrayList.class)));
    }

}
