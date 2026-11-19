
package com.bernardomg.transaction.test.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.transaction.domain.model.TransactionEvolutionMonth;
import com.bernardomg.association.transaction.domain.repository.TransactionEvolutionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEvolutionMonths;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionEvolutionService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultTransactionEvolutionService - getEvolution")
class TestTransactionEvolutionServiceGetEvolution {

    @InjectMocks
    private DefaultTransactionEvolutionService service;

    @Mock
    private TransactionEvolutionRepository     transactionEvolutionRepository;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetEvolution() {
        final Collection<TransactionEvolutionMonth> evolutions;

        // GIVEN
        given(transactionEvolutionRepository.findEvolution(eq(null), eq(null), any()))
            .willReturn(List.of(TransactionEvolutionMonths.currentMonth(1)));

        // WHEN
        evolutions = service.getEvolution(null, null);

        // THEN
        Assertions.assertThat(evolutions)
            .as("When there is data, then it is returned")
            .containsExactly(TransactionEvolutionMonths.currentMonth(1));
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testGetEvolution_NoData() {
        final Collection<TransactionEvolutionMonth> evolutions;

        // GIVEN
        given(transactionEvolutionRepository.findEvolution(eq(null), eq(null), any())).willReturn(List.of());

        // WHEN
        evolutions = service.getEvolution(null, null);

        // THEN
        Assertions.assertThat(evolutions)
            .as("When there is no data, then nothing is returned")
            .isEmpty();
    }

}
