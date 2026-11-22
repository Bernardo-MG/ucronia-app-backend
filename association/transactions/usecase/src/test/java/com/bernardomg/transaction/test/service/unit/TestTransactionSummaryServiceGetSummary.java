
package com.bernardomg.transaction.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.transaction.domain.model.TransactionSummary;
import com.bernardomg.association.transaction.domain.repository.TransactionSummaryRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionSummaries;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionSummaryService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultTransactionSummaryService - getSummary")
class TestTransactionSummaryServiceGetSummary {

    @InjectMocks
    private DefaultTransactionSummaryService service;

    @Mock
    private TransactionSummaryRepository     transactionSummaryRepository;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetSummary() {
        final TransactionSummary summary;

        // GIVEN
        given(transactionSummaryRepository.findSummary()).willReturn(Optional.of(TransactionSummaries.amount(1)));

        // WHEN
        summary = service.getSummary();

        // THEN
        Assertions.assertThat(summary)
            .as("When there is data, then it is returned")
            .isEqualTo(TransactionSummaries.amount(1));
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testGetSummary_NoData() {
        final TransactionSummary summary;

        // GIVEN
        given(transactionSummaryRepository.findSummary()).willReturn(Optional.empty());

        // WHEN
        summary = service.getSummary();

        // THEN
        Assertions.assertThat(summary)
            .as("When there is no data, then nothing is returned")
            .isEqualTo(TransactionSummaries.amount(0));
    }

}
