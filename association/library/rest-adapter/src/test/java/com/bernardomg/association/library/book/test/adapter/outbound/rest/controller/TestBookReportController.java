
package com.bernardomg.association.library.book.test.adapter.outbound.rest.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayOutputStream;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.library.book.adapter.outbound.rest.controller.BookReportController;
import com.bernardomg.association.library.book.usecase.service.BookReportService;

import jakarta.servlet.ServletException;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookReportController - getBookReport")
class TestBookReportController {

    @InjectMocks
    private BookReportController controller;

    private MockMvc              mockMvc;

    @Mock
    private BookReportService    service;

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
    @DisplayName("When report generation is successful, then it is returned")
    void testGetBookReport() throws Exception {
        final ByteArrayOutputStream stream;

        // GIVEN
        stream = new ByteArrayOutputStream();
        stream.writeBytes(new byte[] { 1, 2, 3 });
        given(service.getReport()).willReturn(stream);

        // WHEN
        mockMvc.perform(get("/library/book"))
            // THEN
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When report generation fails, then a servlet exception is raised")
    void testGetBookReport_ServiceFailure() throws Exception {
        final ThrowingCallable action;

        // GIVEN
        given(service.getReport()).willThrow(new RuntimeException("Failed report"));

        // WHEN
        action = () -> mockMvc.perform(get("/library/book"));

        // THEN
        assertThatThrownBy(action).as("Wraps report generation failures in a servlet exception")
            .isInstanceOf(ServletException.class);
    }

}
