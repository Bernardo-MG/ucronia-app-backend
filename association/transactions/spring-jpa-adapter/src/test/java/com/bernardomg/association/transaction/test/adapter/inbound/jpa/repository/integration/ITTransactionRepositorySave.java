
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionTypeEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.PositiveTransaction;
import com.bernardomg.association.transaction.test.configuration.data.annotation.TransactionWithType;
import com.bernardomg.association.transaction.test.configuration.data.annotation.ValidTransactionType;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypeConstants;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.factory.TransactionEntities;
import com.bernardomg.test.annotation.IntegrationTest;
import com.bernardomg.test.configuration.argument.DecimalArgumentsProvider;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionRepository - save")
class ITTransactionRepositorySave {

    @Autowired
    private TransactionRepository       repository;

    @Autowired
    private TransactionSpringRepository springRepository;

    @Test
    @ValidTransactionType
    @DisplayName("When saving a transaction with type, the type is persisted")
    void testSave_AssignsType_Persisted() {
        final List<TransactionEntity> transactions;

        // WHEN
        repository.save(Transactions.withType());

        // THEN
        transactions = springRepository.findAll();

        Assertions.assertThat(transactions)
            .as("transactions")
            .hasSize(1)
            .first()
            .extracting(TransactionEntity::getTransactionType)
            .as("persisted transaction type")
            .extracting(TransactionTypeEntity::getNumber)
            .as("persisted transaction type number")
            .isEqualTo(TransactionTypeConstants.NUMBER);
    }

    @Test
    @ValidTransactionType
    @DisplayName("When saving a transaction with type, the type is returned")
    void testSave_AssignsType_Returned() {
        final Transaction transaction;
        final Transaction created;

        // GIVEN
        transaction = Transactions.withType();

        // WHEN
        created = repository.save(transaction);

        // THEN
        Assertions.assertThat(created.transactionType())
            .as("returned transaction type")
            .contains(TransactionTypeConstants.NUMBER);
    }

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
        Assertions.assertThat(transaction.amount())
            .as("amount")
            .isEqualTo(amount);
    }

    @Test
    @DisplayName("When the transaction value is changed, it is updated")
    @PositiveTransaction
    void testSave_Existing_ChangeValue_PersistedData() {
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(TransactionEntities.decimal());
    }

    @Test
    @DisplayName("When the transaction value is changed, it is returned")
    @PositiveTransaction
    void testSave_Existing_ChangeValue_ReturnedData() {
        final Transaction created;
        final Transaction transaction;

        // GIVEN
        transaction = Transactions.positive();

        // WHEN
        created = repository.save(transaction);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .isEqualTo(Transactions.positive());
    }

    @Test
    @ValidTransactionType
    @TransactionWithType
    @DisplayName("When saving a transaction removing the type, it is removed")
    void testSave_Existing_ClearsType() {
        final List<TransactionEntity> transactions;

        // WHEN
        repository.save(Transactions.positive());

        // THEN
        transactions = springRepository.findAll();

        Assertions.assertThat(transactions)
            .as("transactions")
            .hasSize(1)
            .first()
            .extracting(TransactionEntity::getTransactionType)
            .as("persisted transaction type")
            .extracting(TransactionTypeEntity::getNumber)
            .as("persisted transaction type number")
            .isNull();
    }

    @Test
    @DisplayName("Persists the data")
    void testSave_PersistedData() {
        final Iterable<TransactionEntity> transactions;
        final Transaction                 transaction;

        // GIVEN
        transaction = Transactions.positive();

        // WHEN
        repository.save(transaction);

        // THEN
        transactions = springRepository.findAll();

        Assertions.assertThat(transactions)
            .as("transactions")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "index", "audit")
            .containsExactly(TransactionEntities.valid());
    }

    @Test
    @DisplayName("Returns the created data")
    void testSave_ReturnedData() {
        final Transaction created;
        final Transaction transaction;

        // GIVEN
        transaction = Transactions.positive();

        // WHEN
        created = repository.save(transaction);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .usingRecursiveComparison()
            .ignoringFields("index")
            .isEqualTo(Transactions.positive());
    }

}
