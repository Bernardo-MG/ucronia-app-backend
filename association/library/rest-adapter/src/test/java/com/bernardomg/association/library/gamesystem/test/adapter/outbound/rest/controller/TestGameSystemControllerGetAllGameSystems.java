
package com.bernardomg.association.library.gamesystem.test.adapter.outbound.rest.controller;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.library.gamesystem.adapter.outbound.rest.controller.GameSystemController;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystems;
import com.bernardomg.association.library.gamesystem.usecase.service.GameSystemService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("GameSystemController - getAllGameSystems")
class TestGameSystemControllerGetAllGameSystems {

    @InjectMocks
    private GameSystemController controller;

    private MockMvc              mockMvc;

    @Mock
    private GameSystemService    service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator;

        validator = new LocalValidatorFactoryBean();
        validator.setValidationPropertyMap(Map.of("hibernate.validator.allow_parameter_constraint_override", "true"));
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When there are game systems, then they are returned")
    void testGetAllGameSystems() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(GameSystems.valid()), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN
        mockMvc.perform(get("/library/gameSystem").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

}
