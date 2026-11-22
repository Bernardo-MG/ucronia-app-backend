
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.ValidTransactionType;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypes;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionTypeRepository - findOne")
class ITTransactionTypeRepositoryFindOne {

    @Autowired
    private TransactionTypeRepository repository;

    @Test
    @DisplayName("When there is an existing transaction, then it is returned")
    @ValidTransactionType
    void testFindOne() {
        final Optional<TransactionType> read;

        // WHEN
        read = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(read)
            .as("When there is an existing transaction, then it is returned")
            .contains(TransactionTypes.valid());
    }

    @Test
    @DisplayName("When there is no transaction, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<TransactionType> read;

        // WHEN
        read = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(read)
            .as("When there is no transaction, then nothing is returned")
            .isEmpty();
    }

}
