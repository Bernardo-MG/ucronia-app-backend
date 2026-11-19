
package com.bernardomg.transaction.test.service.unit;

import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.transaction.domain.model.TransactionMonthsRange;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionCalendarMonthsRanges;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultTransactionService - getRange")
class TestTransactionServiceGetRange {

    @InjectMocks
    private DefaultTransactionService service;

    @Mock
    private TransactionRepository     transactionRepository;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetRange() {
        final TransactionMonthsRange range;

        // GIVEN
        given(transactionRepository.findRange()).willReturn(TransactionCalendarMonthsRanges.fullYear());

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range)
            .as("range")
            .isEqualTo(TransactionCalendarMonthsRanges.fullYear());
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testGetRange_NoData() {
        final TransactionMonthsRange range;

        // GIVEN
        given(transactionRepository.findRange()).willReturn(TransactionCalendarMonthsRanges.empty());

        // WHEN
        range = service.getRange();

        // THEN
        Assertions.assertThat(range)
            .as("range")
            .isEqualTo(TransactionCalendarMonthsRanges.empty());
    }

}
