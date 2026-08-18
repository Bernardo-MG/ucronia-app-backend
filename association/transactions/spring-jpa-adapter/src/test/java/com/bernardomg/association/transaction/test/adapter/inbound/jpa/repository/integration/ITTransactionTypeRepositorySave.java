
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionTypeEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionTypeSpringRepository;
import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.ValidTransactionType;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypes;
import com.bernardomg.association.transaction.test.factory.TransactionTypeEntities;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionTypeRepository - save")
class ITTransactionTypeRepositorySave {

    @Autowired
    private TransactionTypeRepository       repository;

    @Autowired
    private TransactionTypeSpringRepository springRepository;

    @Test
    @DisplayName("When the transaction value is changed, it is updated")
    @ValidTransactionType
    void testSave_Existing_ChangeValue_PersistedData() {
        final Iterable<TransactionTypeEntity> transactionTypes;
        final TransactionType                 transactionType;

        // GIVEN
        transactionType = TransactionTypes.valid();

        // WHEN
        repository.save(transactionType);

        // THEN
        transactionTypes = springRepository.findAll();

        Assertions.assertThat(transactionTypes)
            .as("transactions")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(TransactionTypeEntities.valid());
    }

    @Test
    @DisplayName("When the transaction value is changed, it is returned")
    @ValidTransactionType
    void testSave_Existing_ChangeValue_ReturnedData() {
        final TransactionType created;
        final TransactionType transactionType;

        // GIVEN
        transactionType = TransactionTypes.valid();

        // WHEN
        created = repository.save(transactionType);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .isEqualTo(TransactionTypes.valid());
    }

    @Test
    @DisplayName("Persists the data")
    void testSave_PersistedData() {
        final Iterable<TransactionTypeEntity> transactionTypes;
        final TransactionType                 transactionType;

        // GIVEN
        transactionType = TransactionTypes.valid();

        // WHEN
        repository.save(transactionType);

        // THEN
        transactionTypes = springRepository.findAll();

        Assertions.assertThat(transactionTypes)
            .as("transactions")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(TransactionTypeEntities.valid());
    }

    @Test
    @DisplayName("Returns the created data")
    void testSave_ReturnedData() {
        final TransactionType created;
        final TransactionType transactionType;

        // GIVEN
        transactionType = TransactionTypes.valid();

        // WHEN
        created = repository.save(transactionType);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(TransactionTypes.valid());
    }

}
