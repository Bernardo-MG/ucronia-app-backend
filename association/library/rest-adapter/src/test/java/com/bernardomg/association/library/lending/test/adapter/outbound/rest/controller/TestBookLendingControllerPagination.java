
package com.bernardomg.association.library.lending.test.adapter.outbound.rest.controller;

import static com.bernardomg.testing.mvc.MockMvcTestSupport.standalone;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bernardomg.association.library.lending.adapter.outbound.rest.controller.BookLendingController;
import com.bernardomg.association.library.lending.usecase.service.BookLendingService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookLendingController - getAllBookLendings")
class TestBookLendingControllerPagination {

    @InjectMocks
    private BookLendingController controller;

    private MockMvc               mockMvc;

    @Mock
    private BookLendingService    service;

    @BeforeEach
    void setUp() {
        mockMvc = standalone(controller, Map.of("hibernate.validator.allow_parameter_constraint_override", "true"))
            .build();
    }

    @Test
    @DisplayName("When querying the first page, then it is accepted")
    void testGetAllBookLendings_InvalidSizeZero() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(), 10, 5, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN
        mockMvc.perform(get("/library/lending").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When querying another page, then it is accepted")
    void testGetAllBookLendings_PageZero() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(2, 5)), any()))
            .willReturn(new Page<>(List.of(), 10, 5, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN
        mockMvc.perform(get("/library/lending").param("page", "2")
            .param("size", "5")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When the pagination is valid, then it is accepted")
    void testGetAllBookLendings_Pagination() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(), 10, 5, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN
        mockMvc.perform(get("/library/lending").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

}
