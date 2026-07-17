
package com.bernardomg.association.library.booktype.test.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.bernardomg.association.library.booktype.adapter.outbound.rest.controller.BookTypeController;
import com.bernardomg.association.library.booktype.usecase.service.BookTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookTypeController - Validation")
class TestBookTypeControllerValidation {

    private MockMvc         mockMvc;

    @Mock
    private BookTypeService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new BookTypeController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating an empty book, it is rejected")
    void testCreateBookType_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/bookType").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a book with an empty name, it is rejected")
    void testCreateBookType_EmptyName() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "name": ""
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/bookType").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a book with an oversized name, it is rejected")
    void testCreateBookType_OversizedName() throws Exception {
        final String longName;
        final String requestBody;

        // GIVEN
        longName = "x".repeat(101);
        requestBody = String.format("""
                {
                    "name": "%s"
                }
                """, longName);

        // WHEN + THEN
        mockMvc.perform(post("/library/bookType").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating an empty book, it is rejected")
    void testUpdateBookType_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/bookType/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating a book with an empty name, it is rejected")
    void testUpdateBookType_EmptyName() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "name": ""
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/bookType/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

}
