
package com.bernardomg.association.transaction.adapter.outbound.rest.controller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.bernardomg.association.transaction.adapter.outbound.rest.controller.TransactionController;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.usecase.service.TransactionService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransactionController - Validation")
class TestTransactionControllerValidation {

    private MockMvc            mockMvc;

    @Mock
    private TransactionService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new TransactionController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating a transaction with an oversized description, it is rejected")
    void testCreateTransaction_OversizedDescription() throws Exception {
        final String longDescription;
        final String requestBody;

        // GIVEN
        longDescription = "x".repeat(201);
        requestBody = String.format("""
                {
                    "date": "%s",
                    "amount": %s,
                    "description": "%s"
                }
                """, TransactionConstants.DATE, TransactionConstants.AMOUNT, longDescription);

        // WHEN + THEN
        mockMvc.perform(post("/transaction").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a transaction without amount, it is rejected")
    void testCreateTransaction_WithoutAmount() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = String.format("""
                {
                    "date": "%s",
                    "description": "%s"
                }
                """, TransactionConstants.DATE, TransactionConstants.DESCRIPTION);

        // WHEN + THEN
        mockMvc.perform(post("/transaction").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a transaction without date, it is rejected")
    void testCreateTransaction_WithoutDate() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = String.format("""
                {
                    "amount": %s,
                    "description": "%s"
                }
                """, TransactionConstants.AMOUNT, TransactionConstants.DESCRIPTION);

        // WHEN + THEN
        mockMvc.perform(post("/transaction").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a transaction without description, it is rejected")
    void testCreateTransaction_WithoutDescription() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = String.format("""
                {
                    "date": "%s",
                    "amount": %s
                }
                """, TransactionConstants.DATE, TransactionConstants.AMOUNT);

        // WHEN + THEN
        mockMvc.perform(post("/transaction").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

}
