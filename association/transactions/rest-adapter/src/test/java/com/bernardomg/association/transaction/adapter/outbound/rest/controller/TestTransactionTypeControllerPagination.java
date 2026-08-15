
package com.bernardomg.association.transaction.adapter.outbound.rest.controller;

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

import com.bernardomg.association.transaction.usecase.service.TransactionTypeService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransactionTypeController")
class TestTransactionTypeControllerPagination {

    private MockMvc                mockMvc;

    @Mock
    private TransactionTypeService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new TransactionTypeController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When the page is zero, it is rejected")
    void testGetAllTransactions_PageZero() throws Exception {
        // WHEN + THEN
        mockMvc.perform(get("/transaction/type").param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When the pagination is valid, it is accepted")
    void testGetAllTransactions_Pagination() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 20)), any()))
            .willReturn(new Page<>(List.of(), 20, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/transaction/type").param("page", "1")
            .param("size", "20")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("When the size is zero, it is rejected")
    void testGetAllTransactions_SizeZero() throws Exception {
        // WHEN + THEN
        mockMvc.perform(get("/transaction/type").param("page", "1")
            .param("size", "0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

}
