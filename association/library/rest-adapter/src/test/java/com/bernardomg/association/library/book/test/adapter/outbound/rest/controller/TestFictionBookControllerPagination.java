
package com.bernardomg.association.library.book.test.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

import com.bernardomg.association.library.book.adapter.outbound.rest.controller.FictionBookController;
import com.bernardomg.association.library.book.usecase.service.FictionBookService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("FictionBookController - getAllFictionBooks")
class TestFictionBookControllerPagination {

    @InjectMocks
    private FictionBookController controller;

    private MockMvc               mockMvc;

    @Mock
    private FictionBookService    service;

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
    @DisplayName("When querying the first page, then it is accepted")
    void testGetAllFictionBooks_InvalidSizeZero() throws Exception {
        // GIVEN
        given(service.getAll(any(), eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(), 10, 5, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN
        mockMvc.perform(get("/library/book/fiction").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When querying another page, then it is accepted")
    void testGetAllFictionBooks_PageZero() throws Exception {
        // GIVEN
        given(service.getAll(any(), eq(new Pagination(2, 5)), any()))
            .willReturn(new Page<>(List.of(), 10, 5, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN
        mockMvc.perform(get("/library/book/fiction").param("page", "2")
            .param("size", "5")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When the pagination is valid, then it is accepted")
    void testGetAllFictionBooks_Pagination() throws Exception {
        // GIVEN
        given(service.getAll(any(), eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(), 10, 5, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN
        mockMvc.perform(get("/library/book/fiction").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

}
