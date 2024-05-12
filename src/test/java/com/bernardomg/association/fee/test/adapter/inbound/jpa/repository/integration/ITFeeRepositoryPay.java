
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeePaymentEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeePaymentSpringRepository;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.config.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.config.factory.FeePaymentEntities;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.member.test.config.data.annotation.SingleMember;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.test.config.factory.Persons;
import com.bernardomg.association.transaction.config.data.annotation.PositiveTransaction;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.test.config.factory.Transactions;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - pay")
class ITFeeRepositoryPay {

    @Autowired
    private FeePaymentSpringRepository feePaymentSpringRepository;

    @Autowired
    private FeeRepository              repository;

    @Test
    @DisplayName("When the payment exists it is persisted")
    @SingleMember
    @PaidFee
    void testPay_Existing_PersistedData() {
        final Iterable<FeePaymentEntity> payments;
        final Person                     person;
        final Fee                        fee;
        final Transaction                transaction;

        // GIVEN
        person = Persons.valid();
        fee = Fees.paid();
        transaction = Transactions.valid();

        // WHEN
        repository.pay(person, List.of(fee), transaction);

        // THEN
        payments = feePaymentSpringRepository.findAll();

        Assertions.assertThat(payments)
            .as("payments")
            .containsExactly(FeePaymentEntities.valid());
    }

    @Test
    @DisplayName("Persists the data")
    @SingleMember
    @NotPaidFee
    @PositiveTransaction
    void testPay_PersistedData() {
        final Iterable<FeePaymentEntity> payments;
        final Person                     person;
        final Fee                        fee;
        final Transaction                transaction;

        // GIVEN
        person = Persons.valid();
        fee = Fees.paid();
        transaction = Transactions.valid();

        // WHEN
        repository.pay(person, List.of(fee), transaction);

        // THEN
        payments = feePaymentSpringRepository.findAll();

        Assertions.assertThat(payments)
            .as("payments")
            .hasSize(1);
    }

}
