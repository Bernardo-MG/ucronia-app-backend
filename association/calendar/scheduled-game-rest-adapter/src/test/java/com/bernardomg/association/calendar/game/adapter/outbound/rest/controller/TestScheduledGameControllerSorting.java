
package com.bernardomg.association.calendar.game.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

import com.bernardomg.association.calendar.game.usecase.service.ScheduledGameService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScheduledGameController - Sorting")
class TestScheduledGameControllerSorting {

    private MockMvc              mockMvc;

    @Mock
    private ScheduledGameService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new ScheduledGameController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When sorting by title ascending, it is accepted")
    void testGetAllScheduledGames_TitleAsc() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/game").param("page", "1")
            .param("size", "10")
            .param("sort", "title|asc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("When sorting by description descending, it is accepted")
    void testGetAllScheduledGames_TitleDesc() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/game").param("page", "1")
            .param("size", "10")
            .param("sort", "description|desc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
