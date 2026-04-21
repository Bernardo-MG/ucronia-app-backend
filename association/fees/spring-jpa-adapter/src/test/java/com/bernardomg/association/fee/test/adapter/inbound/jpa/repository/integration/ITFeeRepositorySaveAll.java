
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTransactionEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeTransactionSpringRepository;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeEntities;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.transaction.test.configuration.data.annotation.FeeTransaction;
import com.bernardomg.association.transaction.test.factory.TransactionEntities;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeRepository - save all")
class ITFeeRepositorySaveAll {

    @Autowired
    private FeeRepository                  repository;

    @Autowired
    private FeeSpringRepository            springRepository;

    @Autowired
    private FeeTransactionSpringRepository transactionSpringRepository;

    @Test
    @DisplayName("When there is no data, nothing is saved")
    @PositiveFeeType
    @ValidProfile
    @FeeTransaction
    void testSaveAll_Empty() {
        final Collection<Fee> fees;

        // WHEN
        fees = repository.saveAll(List.of());

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("When a not paid fee, it is persisted")
    @PositiveFeeType
    @ValidProfile
    void testSaveAll_NotPaid_PersistedData() {
        final Iterable<FeeEntity> fees;
        final Fee                 fee;

        // GIVEN
        fee = Fees.notPaid();

        // WHEN
        repository.saveAll(List.of(fee));

        // THEN
        fees = springRepository.findAll();

        Assertions.assertThat(fees)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "member.id", "member.feeType.id",
                "member.profile.id", "member.profile.contactChannels.id", "memberId", "feeType.id", "transaction.id")
            .containsExactly(FeeEntities.notPaid());
    }

    @Test
    @DisplayName("When a not paid fee, it is returned")
    @PositiveFeeType
    @ValidProfile
    void testSaveAll_NotPaid_ReturnedData() {
        final Collection<Fee> created;
        final Fee             fee;

        // GIVEN
        fee = Fees.notPaid();

        // WHEN
        created = repository.saveAll(List.of(fee));

        // THEN
        Assertions.assertThat(created)
            .as("fees")
            .containsExactly(Fees.notPaid());
    }

    @Test
    @DisplayName("When changing a fee date, it is persisted")
    @PositiveFeeType
    @ValidProfile
    @FeeTransaction
    @NotPaidFee
    void testSaveAll_Paid_ChangeDate_PersistedData() {
        final Iterable<FeeEntity> fees;
        final Fee                 fee;
        final Instant             date;

        // GIVEN
        date = ZonedDateTime.now()
            .plusMonths(1)
            .toInstant();
        fee = Fees.paidAtDate(date);

        // WHEN
        repository.saveAll(List.of(fee));

        // THEN
        fees = springRepository.findAll();

        Assertions.assertThat(fees)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "member.id", "member.feeType.id",
                "member.profile.id", "member.profile.contactChannels.id", "memberId", "feeType.id", "transaction.id")
            .containsExactly(FeeEntities.paidAtDate(date));
    }

    @Test
    @DisplayName("When changing a fee date, it is persisted in the transaction")
    @PositiveFeeType
    @ValidProfile
    @PaidFee
    void testSaveAll_Paid_ChangeDate_PersistedTransaction() {
        final Fee                            fee;
        final Instant                        date;
        final Iterable<FeeTransactionEntity> transactions;

        // GIVEN
        date = ZonedDateTime.now()
            .plusMonths(1)
            .toInstant();
        fee = Fees.paidAtDate(date);

        // WHEN
        repository.saveAll(List.of(fee));

        // THEN
        transactions = transactionSpringRepository.findAll();

        Assertions.assertThat(transactions)
            .as("transactions")
            .hasSize(1)
            .first()
            .extracting(FeeTransactionEntity::getDate)
            .isEqualTo(date);
    }

    @Test
    @DisplayName("When changing a fee date, it is returned")
    @PositiveFeeType
    @ValidProfile
    @FeeTransaction
    @NotPaidFee
    void testSaveAll_Paid_ChangeDate_ReturnedData() {
        final Iterable<Fee> created;
        final Fee           fee;
        final Instant       date;

        // GIVEN
        date = ZonedDateTime.now()
            .plusMonths(1)
            .toInstant();
        fee = Fees.paidAtDate(date);

        // WHEN
        created = repository.saveAll(List.of(fee));

        // THEN

        Assertions.assertThat(created)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "member.id", "memberId", "transaction.id")
            .containsExactly(Fees.paidAtDate(date));
    }

    @Test
    @DisplayName("When a paid fee, it is persisted")
    @PositiveFeeType
    @ValidProfile
    @FeeTransaction
    void testSaveAll_Paid_PersistedData() {
        final Iterable<FeeEntity> fees;
        final Fee                 fee;

        // GIVEN
        fee = Fees.paid();

        // WHEN
        repository.saveAll(List.of(fee));

        // THEN
        fees = springRepository.findAll();

        Assertions.assertThat(fees)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "member.id", "member.feeType.id",
                "member.profile.id", "member.profile.contactChannels.id", "memberId", "feeType.id", "transaction.id")
            .containsExactly(FeeEntities.paid());
    }

    @Test
    @DisplayName("Persists the fee to transaction relationship")
    @PositiveFeeType
    @ValidProfile
    @FeeTransaction
    void testSaveAll_Paid_PersistedRelationship() {
        final Fee                  fee;
        final FeeEntity            feeEntity;
        final FeeTransactionEntity transactionEntity;

        // GIVEN
        fee = Fees.paid();

        // WHEN
        repository.saveAll(List.of(fee));

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
            .extracting(FeeTransactionEntity::getId)
            .isEqualTo(transactionEntity.getId());
    }

    @Test
    @DisplayName("Persists the transaction")
    @PositiveFeeType
    @ValidProfile
    @FeeTransaction
    void testSaveAll_Paid_PersistedTransaction() {
        final Iterable<FeeTransactionEntity> transactions;
        final Fee                            fee;

        // GIVEN
        fee = Fees.paid();

        // WHEN
        repository.saveAll(List.of(fee));

        // THEN
        transactions = transactionSpringRepository.findAll();

        Assertions.assertThat(transactions)
            .as("transactions")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(TransactionEntities.februaryFee());
    }

    @Test
    @DisplayName("When a paid fee, it is returned")
    @PositiveFeeType
    @ValidProfile
    @FeeTransaction
    void testSaveAll_Paid_ReturnedData() {
        final Collection<Fee> fees;
        final Fee             fee;

        // GIVEN
        fee = Fees.paid();

        // WHEN
        fees = repository.saveAll(List.of(fee));

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

}
