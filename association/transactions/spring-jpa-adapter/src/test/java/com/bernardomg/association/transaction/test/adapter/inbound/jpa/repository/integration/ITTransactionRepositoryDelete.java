
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.PositiveTransaction;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionRepository - delete")
class ITTransactionRepositoryDelete {

    @Autowired
    private TransactionRepository       repository;

    @Autowired
    private TransactionSpringRepository springRepository;

    @Test
    @DisplayName("When there is no data, then nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("transactions")
            .isZero();
    }

    @Test
    @DisplayName("When the transaction exists, then it is removed")
    @PositiveTransaction
    void testDelete_RemovesEntity() {
        // WHEN
        repository.delete(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("transactions")
            .isZero();
    }

}
