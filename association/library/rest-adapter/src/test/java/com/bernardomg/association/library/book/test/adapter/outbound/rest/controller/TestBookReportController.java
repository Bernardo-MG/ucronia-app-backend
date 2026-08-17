
package com.bernardomg.association.library.book.test.adapter.outbound.rest.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayOutputStream;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.library.book.adapter.outbound.rest.controller.BookReportController;
import com.bernardomg.association.library.book.usecase.service.BookReportService;

import jakarta.servlet.ServletException;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookReportController")
class TestBookReportController {

    private MockMvc           mockMvc;

    @Mock
    private BookReportService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new BookReportController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When report generation is successful, it is returned")
    void testGetBookReport() throws Exception {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // GIVEN
        stream.writeBytes(new byte[] { 1, 2, 3 });
        given(service.getReport()).willReturn(stream);

        // WHEN + THEN
        mockMvc.perform(get("/library/book"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When report generation fails, it returns server error")
    void testGetBookReport_IOException() throws Exception {
        // GIVEN
        given(service.getReport()).willThrow(new RuntimeException("Failed report"));

        // WHEN + THEN
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/library/book")));
    }

}
