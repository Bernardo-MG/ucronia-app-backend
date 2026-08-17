
package com.bernardomg.association.calendar.adapter.outbound.rest.controller;

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

import com.bernardomg.association.calendar.domain.model.CalendarType;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarTypes;
import com.bernardomg.association.calendar.usecase.service.CalendarTypeService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("CalendarTypeController")
class TestCalendarTypeController {

    private MockMvc             mockMvc;

    @Mock
    private CalendarTypeService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new CalendarTypeController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating a calendar type with valid data, it is accepted")
    void testCreateCalendarType() throws Exception {
        // GIVEN
        given(service.create(any())).willReturn(CalendarTypes.custom());

        // WHEN + THEN
        mockMvc.perform(post("/calendar-type").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Evento\",\"color\":\"#FFA500\"}"))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.name").exists());
    }

    @Test
    @DisplayName("When deleting a calendar type, it is accepted")
    void testDeleteCalendarType() throws Exception {
        // GIVEN
        given(service.delete(1L)).willReturn(CalendarTypes.custom());

        // WHEN + THEN
        mockMvc.perform(delete("/calendar-type/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("When there are calendar types, they are returned")
    void testGetAllCalendarTypes() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(CalendarTypes.custom()), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/calendar-type").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("When the calendar type exists, it is returned")
    void testGetOneCalendarType() throws Exception {
        // GIVEN
        given(service.getOne(1L)).willReturn(Optional.of(CalendarTypes.custom()));

        // WHEN + THEN
        mockMvc.perform(get("/calendar-type/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("When updating a calendar type with valid data, it is accepted")
    void testUpdateCalendarType() throws Exception {
        // GIVEN
        given(service.update(any())).willReturn(new CalendarType(1L, "Custom", "#FF0000"));

        // WHEN + THEN
        mockMvc.perform(put("/calendar-type/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Custom\",\"color\":\"#FF0000\"}"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name").exists());
    }

}
