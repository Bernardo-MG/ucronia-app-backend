
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.time.Instant;
import java.time.ZonedDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.factory.FeeEntities;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.configuration.data.annotation.FeeTransaction;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEntities;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - save")
class ITFeeRepositorySave {

    @Autowired
    private FeeRepository               repository;

    @Autowired
    private FeeSpringRepository         springRepository;

    @Autowired
    private TransactionSpringRepository transactionSpringRepository;

    @Test
    @DisplayName("Persists the data")
    @ActiveMember
    void testSave_NotPaid_PersistedData() {
        final Iterable<FeeEntity> fees;
        final Fee                 fee;

        // GIVEN
        fee = Fees.notPaid();

        // WHEN
        repository.save(fee);

        // THEN
        fees = springRepository.findAll();

        Assertions.assertThat(fees)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "contact.id", "contactId",
                "transaction.id")
            .containsExactly(FeeEntities.notPaid());
    }

    @Test
    @DisplayName("Returns the created data")
    @ActiveMember
    void testSave_NotPaid_ReturnedData() {
        final Fee created;
        final Fee fee;

        // GIVEN
        fee = Fees.notPaid();

        // WHEN
        created = repository.save(fee);

        // THEN
        Assertions.assertThat(created)
            .as("fees")
            .isEqualTo(Fees.notPaid());
    }

    @Test
    @DisplayName("When changing a fee date, it is persisted")
    @ActiveMember
    @PaidFee
    void testSave_Paid_ChangeDate_PersistedData() {
        final Iterable<FeeEntity> fees;
        final Fee                 fee;
        final Instant             date;

        // GIVEN
        date = ZonedDateTime.now()
            .plusMonths(1)
            .toInstant();
        fee = Fees.paidAtDate(date);

        // WHEN
        repository.save(fee);

        // THEN
        fees = springRepository.findAll();

        // TODO: check the transaction date changes
        Assertions.assertThat(fees)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "contact.id", "contactId",
                "transaction.id")
            .containsExactly(FeeEntities.paidAtDate(date));
    }

    @Test
    @DisplayName("When changing a fee date, it is persisted in the transaction")
    @ActiveMember
    @PaidFee
    void testSave_Paid_ChangeDate_PersistedTransaction() {
        final Fee                         fee;
        final Instant                     date;
        final Iterable<TransactionEntity> transactions;

        // GIVEN
        date = ZonedDateTime.now()
            .plusMonths(1)
            .toInstant();
        fee = Fees.paidAtDate(date);

        // WHEN
        repository.save(fee);

        // THEN
        transactions = transactionSpringRepository.findAll();

        Assertions.assertThat(transactions)
            .as("transactions")
            .hasSize(1)
            .first()
            .extracting(TransactionEntity::getDate)
            .isEqualTo(date);
    }

    @Test
    @DisplayName("When changing a fee date, it is returned")
    @ActiveMember
    @PaidFee
    void testSave_Paid_ChangeDate_ReturnedData() {
        final Fee     created;
        final Fee     fee;
        final Instant date;

        // GIVEN
        date = ZonedDateTime.now()
            .plusMonths(1)
            .toInstant();
        fee = Fees.paidAtDate(date);

        // WHEN
        created = repository.save(fee);

        // THEN

        Assertions.assertThat(created)
            .as("fee")
            .usingRecursiveComparison()
            .ignoringFields("id", "contact.id", "contactId", "transaction.id")
            .isEqualTo(Fees.paidAtDate(date));
    }

    @Test
    @DisplayName("Persists the data")
    @ActiveMember
    @FeeTransaction
    void testSave_Paid_PersistedData() {
        final Iterable<FeeEntity> fees;
        final Fee                 fee;

        // GIVEN
        fee = Fees.paid();

        // WHEN
        repository.save(fee);

        // THEN
        fees = springRepository.findAll();

        Assertions.assertThat(fees)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "contact.id", "contactId",
                "transaction.id")
            .containsExactly(FeeEntities.paid());
    }

    @Test
    @DisplayName("Persists the fee to transaction relationship")
    @ActiveMember
    @FeeTransaction
    void testSave_Paid_PersistedRelationship() {
        final Fee               fee;
        final FeeEntity         feeEntity;
        final TransactionEntity transactionEntity;

        // GIVEN
        fee = Fees.paid();

        // WHEN
        repository.save(fee);

        // THEN
        feeEntity = springRepository.findAll()
            .iterator()
            .next();
        transactionEntity = transactionSpringRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(feeEntity)
            .as("fee entity")
            .extracting(FeeEntity::getTransaction)
            .extracting(TransactionEntity::getId)
            .isEqualTo(transactionEntity.getId());
    }

    @Test
    @DisplayName("Persists the transaction")
    @ActiveMember
    @FeeTransaction
    void testSave_Paid_PersistedTransaction() {
        final Iterable<TransactionEntity> transactions;
        final Fee                         fee;

        // GIVEN
        fee = Fees.paid();

        // WHEN
        repository.save(fee);

        // THEN
        transactions = transactionSpringRepository.findAll();

        Assertions.assertThat(transactions)
            .as("transactions")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(TransactionEntities.februaryFee());
    }

    @Test
    @DisplayName("Returns the created data")
    @ActiveMember
    @FeeTransaction
    void testSave_Paid_ReturnedData() {
        final Fee created;
        final Fee fee;

        // GIVEN
        fee = Fees.paid();

        // WHEN
        created = repository.save(fee);

        // THEN
        Assertions.assertThat(created)
            .as("fees")
            .isEqualTo(Fees.paid());
    }

}
