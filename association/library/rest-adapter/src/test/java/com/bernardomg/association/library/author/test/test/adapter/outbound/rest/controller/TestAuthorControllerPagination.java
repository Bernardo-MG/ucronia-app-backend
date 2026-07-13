
package com.bernardomg.association.library.author.test.test.adapter.outbound.rest.controller;

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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.library.author.adapter.outbound.rest.controller.AuthorController;
import com.bernardomg.association.library.author.usecase.service.AuthorService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorController")
class TestAuthorControllerPagination {

    private MockMvc       mockMvc;

    @Mock
    private AuthorService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new AuthorController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When the size is zero, it is rejected")
    void testGetAllAuthors_InvalidSizeZero() throws Exception {
        mockMvc.perform(get("/library/author").param("page", "0")
            .param("size", "0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When the page is zero, it is accepted")
    void testGetAllAuthors_PageZero() throws Exception {
        given(service.getAll(eq(new Pagination(0, 10)), any()))
            .willReturn(new Page<>(List.of(), 10, 0, 0, 0, 0, true, true, Sorting.unsorted()));

        mockMvc.perform(get("/library/author").param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("When the pagination is valid, it is accepted")
    void testGetAllAuthors_Pagination() throws Exception {
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(), 10, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        mockMvc.perform(get("/library/author").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

}
