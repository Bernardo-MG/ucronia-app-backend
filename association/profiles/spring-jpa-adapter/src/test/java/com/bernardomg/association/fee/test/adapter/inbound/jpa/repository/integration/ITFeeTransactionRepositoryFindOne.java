
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeTransactionSpringRepository;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.fee.domain.repository.FeeTransactionRepository;
import com.bernardomg.association.fee.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.fee.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.configuration.data.annotation.NegativeTransaction;
import com.bernardomg.association.transaction.test.configuration.data.annotation.PositiveTransaction;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEntities;
import com.bernardomg.test.annotation.IntegrationTest;
import com.bernardomg.test.configuration.argument.AroundZeroArgumentsProvider;
import com.bernardomg.test.configuration.argument.DecimalArgumentsProvider;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeTransactionRepository - findOne")
class ITFeeTransactionRepositoryFindOne {

    @Autowired
    private FeeTransactionRepository       repository;

    @Autowired
    private FeeTransactionSpringRepository springRepository;

    @Test
    @DisplayName("When there is an existing transaction, then it is returned")
    @PositiveTransaction
    void testFindOne() {
        final Optional<FeeTransaction> transactionOptional;

        // WHEN
        transactionOptional = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .as("When there is an existing transaction, then it is returned")
            .contains(Transactions.positive());
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("When a related entity has a value around zero, then it is returned")
    void testFindOne_AroundZero(final Float amount) {
        final Optional<FeeTransaction> transactionOptional;

        // GIVEN
        springRepository.save(TransactionEntities.forAmount(amount));

        // WHEN
        transactionOptional = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .as("With a value around zero, the related entity is returned")
            .contains(Transactions.forAmount(amount));
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("When a related entity has a decimal value, then it is returned")
    void testFindOne_Decimal(final Float amount) {
        final Optional<FeeTransaction> transactionOptional;

        // GIVEN
        springRepository.save(TransactionEntities.forAmount(amount));

        // WHEN
        transactionOptional = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .as("With a decimal value, the related entity is returned")
            .contains(Transactions.forAmount(amount));
    }

    @Test
    @DisplayName("When a negative value is read, then the correct data is returned")
    @NegativeTransaction
    void testFindOne_Negative() {
        final Optional<FeeTransaction> transactionOptional;

        // WHEN
        transactionOptional = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .as("Returns the correct data when reading a negative value")
            .contains(Transactions.forAmount(-1F));
    }

    @Test
    @DisplayName("When there is no transaction, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<FeeTransaction> transactionOptional;

        // WHEN
        transactionOptional = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .as("When there is no transaction, then nothing is returned")
            .isEmpty();
    }

}
