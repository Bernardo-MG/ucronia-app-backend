
package com.bernardomg.association.transaction.adapter.outbound.rest.controller.test;

import static org.hamcrest.Matchers.equalTo;
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

import com.bernardomg.association.transaction.adapter.outbound.rest.controller.TransactionTypeController;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypeConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypes;
import com.bernardomg.association.transaction.usecase.service.TransactionTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransactionTypeController - getOneTransactionType")
class TestTransactionTypeControllerGetOneTransactionType {

    @InjectMocks
    private TransactionTypeController controller;

    private MockMvc                   mockMvc;

    @Mock
    private TransactionTypeService    service;

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
    @DisplayName("When the transaction exists, then it is returned")
    void testGetOneTransaction() throws Exception {
        // GIVEN
        given(service.getOne(TransactionTypeConstants.NUMBER)).willReturn(Optional.of(TransactionTypes.valid()));

        // WHEN
        mockMvc
            .perform(get("/transaction/type/{number}", TransactionTypeConstants.NUMBER)
                .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.description", equalTo(TransactionTypeConstants.DESCRIPTION)))
            .andExpect(jsonPath("$.content.color", equalTo(TransactionTypeConstants.COLOR)));
    }

}
