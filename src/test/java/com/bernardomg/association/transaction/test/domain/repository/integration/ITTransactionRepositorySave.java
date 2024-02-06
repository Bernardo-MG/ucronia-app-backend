
package com.bernardomg.association.transaction.test.domain.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.config.data.annotation.PositiveTransaction;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.config.factory.TransactionEntities;
import com.bernardomg.association.transaction.test.config.factory.Transactions;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("TransactionRepository - save")
class ITTransactionRepositorySave {

    @Autowired
    private TransactionRepository       repository;

    @Autowired
    private TransactionSpringRepository springRepository;

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal value, the transaction is persisted")
    void testSave_Decimal_PersistedData(final Float amount) {
        final Transaction       transaction;
        final TransactionEntity entity;

        // GIVEN
        transaction = Transactions.amount(amount);

        // WHEN
        repository.save(transaction);

        // THEN
        entity = springRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getAmount())
            .as("amount")
            .isEqualTo(amount);
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal value, the persisted transaction is returned")
    void testSave_Decimal_ReturnedData(final Float amount) {
        final Transaction transactionRequest;
        final Transaction transaction;

        // GIVEN
        transactionRequest = Transactions.amount(amount);

        // WHEN
        transaction = repository.save(transactionRequest);

        // THEN
        Assertions.assertThat(transaction.getAmount())
            .as("amount")
            .isEqualTo(amount);
    }

    @Test
    @DisplayName("When the transaction exists it is updated")
    @PositiveTransaction
    void testSave_Existing_PersistedData() {
        final Iterable<TransactionEntity> transactions;
        final Transaction                 transaction;

        // GIVEN
        transaction = Transactions.decimal();

        // WHEN
        repository.save(transaction);

        // THEN
        transactions = springRepository.findAll();

        Assertions.assertThat(transactions)
            .as("transactions")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(TransactionEntities.decimal());
    }

    @Test
    @DisplayName("When the transaction exists it is returned")
    @PositiveTransaction
    void testSave_Existing_ReturnedData() {
        final Transaction created;
        final Transaction transaction;

        // GIVEN
        transaction = Transactions.valid();

        // WHEN
        created = repository.save(transaction);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .isEqualTo(Transactions.valid());
    }

    @Test
    @DisplayName("Persists the data")
    void testSave_PersistedData() {
        final Iterable<TransactionEntity> transactions;
        final Transaction                 transaction;

        // GIVEN
        transaction = Transactions.valid();

        // WHEN
        repository.save(transaction);

        // THEN
        transactions = springRepository.findAll();

        Assertions.assertThat(transactions)
            .as("transactions")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(TransactionEntities.valid());
    }

    @Test
    @DisplayName("Returns the created data")
    void testSave_ReturnedData() {
        final Transaction created;
        final Transaction transaction;

        // GIVEN
        transaction = Transactions.valid();

        // WHEN
        created = repository.save(transaction);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .isEqualTo(Transactions.valid());
    }

}
