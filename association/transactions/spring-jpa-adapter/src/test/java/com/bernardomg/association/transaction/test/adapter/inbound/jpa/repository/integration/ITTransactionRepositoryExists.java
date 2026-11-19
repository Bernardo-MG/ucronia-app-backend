
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.PositiveTransaction;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionRepository - exists")
class ITTransactionRepositoryExists {

    @Autowired
    private TransactionRepository repository;

    @Test
    @DisplayName("When there is an existing transaction, then it exists")
    @PositiveTransaction
    void testExists() {
        final boolean exists;

        // WHEN
        exists = repository.exists(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is no transaction, then nothing exists")
    void testExists_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.exists(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
