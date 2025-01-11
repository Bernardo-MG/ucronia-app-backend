
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.util.List;
import java.util.Objects;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActivePerson;
import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.association.transaction.configuration.data.annotation.PositiveTransaction;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - pay")
class ITFeeRepositoryPay {

    @Autowired
    private FeeSpringRepository feeSpringRepository;

    @Autowired
    private FeeRepository       repository;

    @Test
    @DisplayName("When the fee is already paid, the transactions are persisted")
    @MembershipActivePerson
    @PaidFee
    void testPay_Existing_PersistedTransaction() {
        final Person              person;
        final Fee                 fee;
        final Iterable<FeeEntity> fees;
        final Transaction         transaction;

        // GIVEN
        person = Persons.noMembership();
        fee = Fees.paid();
        transaction = Transactions.positive();

        // WHEN
        repository.pay(person, List.of(fee), transaction);

        // THEN
        fees = feeSpringRepository.findAll();

        Assertions.assertThat(fees)
            .as("fees")
            .extracting(FeeEntity::getTransactionId)
            .allMatch(Objects::nonNull);
    }

    @Test
    @DisplayName("When the fee is not paid, no transaction is persisted")
    @MembershipActivePerson
    @NotPaidFee
    @PositiveTransaction
    void testPay_NoPersistedTransaction() {
        final Person              person;
        final Fee                 fee;
        final Iterable<FeeEntity> fees;
        final Transaction         transaction;

        // GIVEN
        person = Persons.noMembership();
        fee = Fees.paid();
        transaction = Transactions.positive();

        // WHEN
        repository.pay(person, List.of(fee), transaction);

        // THEN
        fees = feeSpringRepository.findAll();

        Assertions.assertThat(fees)
            .as("fees")
            .extracting(FeeEntity::getTransactionId)
            .allMatch(Objects::nonNull);
    }

    @Test
    @DisplayName("When the fee is not paid, it is returned")
    @MembershipActivePerson
    @NotPaidFee
    @PositiveTransaction
    void testPay_ReturnedData() {
        final Person        person;
        final Fee           fee;
        final Iterable<Fee> fees;
        final Transaction   transaction;

        // GIVEN
        person = Persons.noMembership();
        fee = Fees.paid();
        transaction = Transactions.positive();

        // WHEN
        fees = repository.pay(person, List.of(fee), transaction);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

}
