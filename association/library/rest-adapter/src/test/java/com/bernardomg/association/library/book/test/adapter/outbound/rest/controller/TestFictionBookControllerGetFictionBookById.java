
package com.bernardomg.association.library.book.test.adapter.outbound.rest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

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
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.usecase.service.FictionBookService;

@ExtendWith(MockitoExtension.class)
@DisplayName("FictionBookController - getFictionBookById")
class TestFictionBookControllerGetFictionBookById {

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
    @DisplayName("When the fiction book exists, then it is returned")
    void testGetFictionBookById() throws Exception {
        // GIVEN
        given(service.getOne(1L)).willReturn(Optional.of(FictionBooks.minimal()));

        // WHEN
        mockMvc.perform(get("/library/book/fiction/1").contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.title.title").exists());
    }

}
