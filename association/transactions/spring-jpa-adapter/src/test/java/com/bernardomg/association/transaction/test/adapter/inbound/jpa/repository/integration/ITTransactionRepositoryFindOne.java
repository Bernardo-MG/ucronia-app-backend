
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.NegativeTransaction;
import com.bernardomg.association.transaction.test.configuration.data.annotation.PositiveTransaction;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.factory.TransactionEntities;
import com.bernardomg.test.annotation.IntegrationTest;
import com.bernardomg.test.configuration.argument.AroundZeroArgumentsProvider;
import com.bernardomg.test.configuration.argument.DecimalArgumentsProvider;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionRepository - findOne")
class ITTransactionRepositoryFindOne {

    @Autowired
    private TransactionRepository       repository;

    @Autowired
    private TransactionSpringRepository springRepository;

    @Test
    @DisplayName("When there is an existing transaction, then it is returned")
    @PositiveTransaction
    void testFindOne() {
        final Optional<Transaction> transaction;

        // WHEN
        transaction = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transaction)
            .as("transaction")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is an existing transaction, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Transactions.positive()));
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("When a related entity has a value around zero, then it is returned")
    void testFindOne_AroundZero(final Float amount) {
        final Optional<Transaction> transaction;

        // GIVEN
        springRepository.save(TransactionEntities.forAmount(amount));

        // WHEN
        transaction = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transaction)
            .as("transaction")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("With a value around zero, the related entity is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Transactions.forAmount(amount)));
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("When a related entity has a decimal value, then it is returned")
    void testFindOne_Decimal(final Float amount) {
        final Optional<Transaction> transaction;

        // GIVEN
        springRepository.save(TransactionEntities.forAmount(amount));

        // WHEN
        transaction = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transaction)
            .as("transaction")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("With a decimal value, the related entity is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Transactions.forAmount(amount)));
    }

    @Test
    @DisplayName("When a negative value is read, then the correct data is returned")
    @NegativeTransaction
    void testFindOne_Negative() {
        final Optional<Transaction> transaction;

        // WHEN
        transaction = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transaction)
            .as("transaction")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("Returns the correct data when reading a negative value")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Transactions.forAmount(-1F)));
    }

    @Test
    @DisplayName("When there is no transaction, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<Transaction> transaction;

        // WHEN
        transaction = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transaction)
            .as("When there is no transaction, then nothing is returned")
            .isEmpty();
    }

}
