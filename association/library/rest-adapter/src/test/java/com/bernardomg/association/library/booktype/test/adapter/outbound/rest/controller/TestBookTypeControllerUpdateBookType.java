
package com.bernardomg.association.library.booktype.test.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeConstants;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.association.library.booktype.usecase.service.BookTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookTypeController - updateBookType")
class TestBookTypeControllerUpdateBookType {

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
    @DisplayName("When updating a valid book, then it is accepted")
    void testUpdateBookType() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(any())).willReturn(BookTypes.valid());

        requestBody = """
                {
                    "name": "Book type"
                }
                """;

        // WHEN
        mockMvc.perform(put("/library/bookType/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", equalTo(BookTypeConstants.NAME)));
    }

}
