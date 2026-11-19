
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.domain.repository.FeeTransactionRepository;
import com.bernardomg.association.fee.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.data.annotation.PositiveTransaction;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeTransactionRepository - findNextIndex")
class ITFeeTransactionRepositoryFindNextIndex {

    @Autowired
    private FeeTransactionRepository repository;

    @Test
    @DisplayName("When a transaction exists, then the next index is correct")
    @PositiveTransaction
    void testFindNextIndex() {
        final long index;

        // WHEN
        index = repository.findNextIndex();

        // THEN
        Assertions.assertThat(index)
            .as("index")
            .isEqualTo(TransactionConstants.NEXT_INDEX);
    }

    @Test
    @DisplayName("When no transaction exists, then the next index is correct")
    void testFindNextIndex_NoData() {
        final long index;

        // WHEN
        index = repository.findNextIndex();

        // THEN
        Assertions.assertThat(index)
            .as("index")
            .isEqualTo(1);
    }

}
