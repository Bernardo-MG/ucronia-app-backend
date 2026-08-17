
package com.bernardomg.association.transaction.adapter.outbound.rest.controller.test;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.bernardomg.association.transaction.adapter.outbound.rest.controller.TransactionController;
import com.bernardomg.association.transaction.domain.model.TransactionMonthsRange;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionCalendarMonthsRanges;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.usecase.service.TransactionService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransactionController")
class TestTransactionController {

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
    @DisplayName("When creating a transaction with valid data, it is accepted")
    void testCreateTransaction_ValidData() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(Transactions.positive());

        requestBody = String.format("""
                {
                    "date": "%s",
                    "amount": %s,
                    "description": "%s"
                }
                """, TransactionConstants.DATE, TransactionConstants.AMOUNT, TransactionConstants.DESCRIPTION);

        // WHEN + THEN
        mockMvc.perform(post("/transaction").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.date").exists())
            .andExpect(jsonPath("$.content.amount").exists())
            .andExpect(jsonPath("$.content.description", equalTo(TransactionConstants.DESCRIPTION)));
    }

    @Test
    @DisplayName("When the transaction is deleted, it is accepted")
    void testDeleteTransaction() throws Exception {
        // GIVEN
        given(service.delete(anyLong())).willReturn(Transactions.positive());

        // WHEN + THEN
        mockMvc
            .perform(delete("/transaction/{index}", TransactionConstants.INDEX).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.index").exists());
    }

    @Test
    @DisplayName("When the transaction exists, it is returned")
    void testGetOneTransaction() throws Exception {
        // GIVEN
        given(service.getOne(TransactionConstants.INDEX)).willReturn(Optional.of(Transactions.positive()));

        // WHEN + THEN
        mockMvc.perform(get("/transaction/{index}", TransactionConstants.INDEX).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.index").exists());
    }

    @Test
    @DisplayName("When requesting the transaction range, it is returned")
    void testGetTransactionRange() throws Exception {
        // GIVEN
        given(service.getRange())
            .willReturn(new TransactionMonthsRange(TransactionCalendarMonthsRanges.FULL_YEAR_MONTHS));

        // WHEN + THEN
        mockMvc.perform(get("/transaction/range").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.months").isArray());
    }

    @Test
    @DisplayName("When updating a valid transaction, it is accepted")
    void testUpdateTransaction_ValidData() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(any())).willReturn(Transactions.positive());

        requestBody = String.format("""
                {
                    "date": "%s",
                    "amount": %s,
                    "description": "%s"
                }
                """, TransactionConstants.DATE, TransactionConstants.AMOUNT, TransactionConstants.DESCRIPTION);

        // WHEN + THEN
        mockMvc.perform(put("/transaction/{index}", TransactionConstants.INDEX).contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.amount", equalTo((double) TransactionConstants.AMOUNT)));
    }

}
