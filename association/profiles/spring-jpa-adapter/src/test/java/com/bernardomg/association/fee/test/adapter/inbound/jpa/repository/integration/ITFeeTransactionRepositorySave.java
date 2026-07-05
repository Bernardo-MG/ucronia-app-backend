
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTransactionEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeTransactionSpringRepository;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.fee.domain.repository.FeeTransactionRepository;
import com.bernardomg.association.fee.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.configuration.data.annotation.PositiveTransaction;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEntities;
import com.bernardomg.test.annotation.IntegrationTest;
import com.bernardomg.test.configuration.argument.DecimalArgumentsProvider;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeTransactionRepository - save")
class ITFeeTransactionRepositorySave {

    @Autowired
    private FeeTransactionRepository       repository;

    @Autowired
    private FeeTransactionSpringRepository springRepository;

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal value, the transaction is persisted")
    void testSave_Decimal_PersistedData(final Float amount) {
        final FeeTransaction       transaction;
        final FeeTransactionEntity entity;

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
        final FeeTransaction transactionRequest;
        final FeeTransaction transaction;

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
        final Iterable<FeeTransactionEntity> transactions;
        final FeeTransaction                 transaction;

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
    @DisplayName("When the transaction value is changed, it is returned")
    @PositiveTransaction
    void testSave_Existing_ChangeValue_ReturnedData() {
        final FeeTransaction created;
        final FeeTransaction transaction;

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
    @DisplayName("Persists the data")
    void testSave_PersistedData() {
        final Iterable<FeeTransactionEntity> transactions;
        final FeeTransaction                 transaction;

        // GIVEN
        transaction = Transactions.positive();

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
        final FeeTransaction created;
        final FeeTransaction transaction;

        // GIVEN
        transaction = Transactions.positive();

        // WHEN
        created = repository.save(transaction);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .isEqualTo(Transactions.positive());
    }

}
