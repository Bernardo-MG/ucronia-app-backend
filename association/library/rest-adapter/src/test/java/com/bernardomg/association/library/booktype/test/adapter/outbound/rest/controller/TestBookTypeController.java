
package com.bernardomg.association.library.booktype.test.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.bernardomg.association.library.booktype.adapter.outbound.rest.controller.BookTypeController;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.usecase.service.BookTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookTypeController")
class TestBookTypeController {

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
    @DisplayName("When creating a book with an empty name, it is accepted")
    void testCreateBookType_EmptyName() throws Exception {
        final String requestBody;

        given(service.create(any())).willReturn(new BookType(1L, ""));

        requestBody = """
                {
                    "name": ""
                }
                """;

        mockMvc.perform(post("/library/bookType").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When creating a book with an padded name, it is accepted")
    void testCreateBookType_PaddedName() throws Exception {
        final String requestBody;

        given(service.create(any())).willReturn(new BookType(1L, ""));

        requestBody = """
                {
                    "name": "  Adventure  "
                }
                """;

        mockMvc.perform(post("/library/bookType").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name").exists());
    }

    @Test
    @DisplayName("When creating a book with special characters, it is accepted")
    void testCreateBookType_SpecialCharacters() throws Exception {
        final String requestBody;

        given(service.create(any())).willReturn(new BookType(1L, ""));

        requestBody = """
                {
                    "name": "Fantasy & Sci-Fi (Extended)"
                }
                """;

        mockMvc.perform(post("/library/bookType").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", equalTo("Fantasy & Sci-Fi (Extended)")));
    }

    @Test
    @DisplayName("When creating a book with valid data, it is accepted")
    void testCreateBookType_ValidData() throws Exception {
        final String requestBody;

        given(service.create(any())).willReturn(new BookType(1L, ""));

        requestBody = """
                {
                    "name": "Science Fiction"
                }
                """;

        mockMvc.perform(post("/library/bookType").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", equalTo("Science Fiction")))
            .andExpect(jsonPath("$.content.number").exists());
    }

    @Test
    @DisplayName("When the book is deleted, it is accepted")
    void testDeleteBookType() throws Exception {
        given(service.delete(any())).willReturn(new BookType(1L, ""));

        mockMvc.perform(delete("/library/bookType/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists());
    }

    @Test
    @DisplayName("When the book exists, it is returned")
    void testGetBookTypeById() throws Exception {
        given(service.getOne(1L)).willReturn(Optional.of(new BookType(1L, "")));

        mockMvc.perform(get("/library/bookType/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.name").exists());
    }

    @Test
    @DisplayName("When updating a valid book, it is accepted")
    void testUpdateBookType() throws Exception {
        final String requestBody;

        given(service.update(any())).willReturn(new BookType(1L, ""));

        requestBody = """
                {
                    "name": "Corebook"
                }
                """;

        mockMvc.perform(put("/library/bookType/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", equalTo("Corebook")));
    }

}
