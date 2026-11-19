
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.ValidTransactionType;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypeConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionTypeRepository - delete")
class ITTransactionTypeRepositoryDelete {

    @Autowired
    private TransactionTypeRepository   repository;

    @Autowired
    private TransactionSpringRepository springRepository;

    @Test
    @DisplayName("When there is no data, then nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(TransactionTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("transactions")
            .isZero();
    }

    @Test
    @DisplayName("When the transaction type exists, then it is removed")
    @ValidTransactionType
    void testDelete_RemovesEntity() {
        // WHEN
        repository.delete(TransactionTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("transactions")
            .isZero();
    }

}
