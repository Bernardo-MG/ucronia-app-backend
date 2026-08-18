
package com.bernardomg.association.transaction.adapter.outbound.rest.controller.test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import com.bernardomg.association.transaction.adapter.outbound.rest.controller.TransactionSummaryController;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionSummaries;
import com.bernardomg.association.transaction.usecase.service.TransactionSummaryService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransactionSummaryController")
class TestTransactionSummaryController {

    private MockMvc                   mockMvc;

    @Mock
    private TransactionSummaryService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new TransactionSummaryController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When requesting the summary, it is returned")
    void testGetTransactionSummary() throws Exception {
        // GIVEN
        given(service.getSummary()).willReturn(TransactionSummaries.amount(TransactionConstants.AMOUNT));

        // WHEN + THEN
        mockMvc.perform(get("/transaction/summary").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.results").exists())
            .andExpect(jsonPath("$.content.total").exists());
    }

}
