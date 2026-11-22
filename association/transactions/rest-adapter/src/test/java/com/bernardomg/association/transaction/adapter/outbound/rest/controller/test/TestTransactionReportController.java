
package com.bernardomg.association.transaction.adapter.outbound.rest.controller.test;

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

import com.bernardomg.association.transaction.adapter.outbound.rest.controller.TransactionReportController;
import com.bernardomg.association.transaction.usecase.service.TransactionReportService;

import jakarta.servlet.ServletException;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransactionReportController - getTransactionReport")
class TestTransactionReportController {

    @InjectMocks
    private TransactionReportController controller;

    private MockMvc                     mockMvc;

    @Mock
    private TransactionReportService    service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When report generation is successful, then it is returned")
    void testGetTransactionReport() throws Exception {
        final ByteArrayOutputStream stream;

        // GIVEN
        stream = new ByteArrayOutputStream();
        stream.writeBytes(new byte[] { 1, 2, 3 });
        given(service.getReport()).willReturn(stream);

        // WHEN
        mockMvc.perform(get("/transaction/report"))
            // THEN
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When report generation fails, then a servlet exception is raised")
    void testGetTransactionReport_ServiceFailure() throws Exception {
        final ThrowingCallable action;

        // GIVEN
        given(service.getReport()).willThrow(new RuntimeException("Failed report"));

        // WHEN
        action = () -> mockMvc.perform(get("/transaction/report"));

        // THEN
        assertThatThrownBy(action).as("Wraps report generation failures in a servlet exception")
            .isInstanceOf(ServletException.class);
    }

}
