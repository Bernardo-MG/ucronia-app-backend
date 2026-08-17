
package com.bernardomg.association.transaction.adapter.outbound.rest.controller.test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

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

import com.bernardomg.association.transaction.adapter.outbound.rest.controller.TransactionEvolutionController;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEvolutionMonths;
import com.bernardomg.association.transaction.usecase.service.TransactionEvolutionService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransactionEvolutionController")
class TestTransactionEvolutionController {

    private MockMvc                     mockMvc;

    @Mock
    private TransactionEvolutionService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new TransactionEvolutionController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When requesting the monthly evolution, it is returned")
    void testGetMonthlyTransactionEvolution() throws Exception {
        final String from;
        final String to;

        // GIVEN
        from = "2025-01-01T00:00:00Z";
        to = "2025-12-31T00:00:00Z";
        given(service.getEvolution(Instant.parse(from), Instant.parse(to)))
            .willReturn(List.of(TransactionEvolutionMonths.forAmount(10F)));

        // WHEN + THEN
        mockMvc.perform(get("/transaction/balance/monthly").param("from", "2025-01-01T00:00:00Z")
            .param("to", "2025-12-31T00:00:00Z")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

}
