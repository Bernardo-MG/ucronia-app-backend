
package com.bernardomg.association.library.booktype.test.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.bernardomg.association.library.booktype.adapter.outbound.rest.controller.BookTypeController;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.association.library.booktype.usecase.service.BookTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookTypeController - createBookType")
class TestBookTypeControllerCreateBookType {

    @InjectMocks
    private BookTypeController controller;

    private MockMvc            mockMvc;

    @Mock
    private BookTypeService    service;

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
    @DisplayName("When creating a book with an padded name, then it is accepted")
    void testCreateBookType_PaddedName() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(BookTypes.valid());

        requestBody = """
                {
                    "name": "  Core book  "
                }
                """;

        // WHEN
        mockMvc.perform(post("/library/bookType").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name").exists());
    }

    @Test
    @DisplayName("When creating a book with special characters, then it is accepted")
    void testCreateBookType_SpecialCharacters() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(new BookType(1L, "Type 1 & type 2 (Extended)"));

        requestBody = """
                {
                    "name": "Fantasy & Sci-Fi (Extended)"
                }
                """;

        // WHEN
        mockMvc.perform(post("/library/bookType").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", equalTo("Type 1 & type 2 (Extended)")));
    }

    @Test
    @DisplayName("When creating a book with valid data, then it is accepted")
    void testCreateBookType_ValidData() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(new BookType(1L, "Core book"));

        requestBody = """
                {
                    "name": "Science Fiction"
                }
                """;

        // WHEN
        mockMvc.perform(post("/library/bookType").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", equalTo("Core book")))
            .andExpect(jsonPath("$.content.number").exists());
    }

}
