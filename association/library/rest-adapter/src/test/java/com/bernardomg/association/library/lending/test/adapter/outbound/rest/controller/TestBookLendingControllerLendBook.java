
package com.bernardomg.association.library.lending.test.adapter.outbound.rest.controller;

import static com.bernardomg.testing.mvc.MockMvcBuilder.javaTimeJsonConverter;
import static com.bernardomg.testing.mvc.MockMvcBuilder.standalone;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.library.lending.usecase.service.BookLendingService;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookLendingController - lendBook")
class TestBookLendingControllerLendBook {

    @InjectMocks
    private BookLendingController controller;

    private MockMvc               mockMvc;

    @Mock
    private BookLendingService    service;

    @BeforeEach
    void setUp() {
        mockMvc = standalone(controller, Map.of("hibernate.validator.allow_parameter_constraint_override", "true"))
            .setMessageConverters(javaTimeJsonConverter())
            .build();
    }

    @Test
    @DisplayName("When lending a book with valid data, then it is accepted")
    void testLendBook() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.lendBook(1L, 10L, java.time.Instant.parse("2025-08-01T00:00:00Z")))
            .willReturn(BookLendings.lent());
        requestBody = """
                {
                    "book": 1,
                    "borrower": 10,
                    "lendingDate": "2025-08-01T00:00:00Z"
                }
                """;

        // WHEN
        mockMvc.perform(post("/library/lending").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.book.number").exists());
    }

}
