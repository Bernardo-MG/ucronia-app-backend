
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
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
import com.bernardomg.association.fee.test.configuration.factory.FeeTransactionEntities;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.transaction.test.configuration.data.annotation.FeeTransaction;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeRepository - save")
class ITFeeRepositorySave {

    @Autowired
    private FeeRepository                  repository;

    @Autowired
    private FeeSpringRepository            springRepository;

    @Autowired
    private FeeTransactionSpringRepository transactionSpringRepository;

    @Test
    @DisplayName("When valid data is saved, then the data is persisted")
    @PositiveFeeType
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "member.id", "member.feeType.id",
                "member.profile.id", "member.profile.contactChannels.id", "memberId", "feeType.id", "transaction.id",
                "audit", "member.audit", "transaction.audit")
            .containsExactly(FeeEntities.notPaid());
    }

    @Test
    @DisplayName("When the component is queried, then the created data is returned")
    @PositiveFeeType
    @ValidProfile
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
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Fees.notPaid());
    }

    @Test
    @DisplayName("When changing a fee date, then it is persisted")
    @PositiveFeeType
    @ActiveMember
    @PaidFee
    void testSave_Paid_ChangeDate_PersistedData() {
        final Iterable<FeeEntity> fees;
        final Fee                 fee;
        final Instant             date;

        // GIVEN
        date = ZonedDateTime.now()
            .plus(1, ChronoUnit.MONTHS)
            .toInstant();
        fee = Fees.paidAtDate(date);

        // WHEN
        repository.save(fee);

        // THEN
        fees = springRepository.findAll();

        // TODO: check the transaction date changes
        Assertions.assertThat(fees)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "member.id", "member.feeType.id",
                "member.profile.id", "member.profile.contactChannels.id", "memberId", "feeType.id", "transaction.id",
                "audit", "member.audit", "transaction.audit")
            .containsExactly(FeeEntities.paidAtDate(date));
    }

    @Test
    @DisplayName("When changing a fee date, then it is persisted in the transaction")
    @PositiveFeeType
    @ValidProfile
    @PaidFee
    void testSave_Paid_ChangeDate_PersistedTransaction() {
        final Fee                            fee;
        final Instant                        date;
        final Iterable<FeeTransactionEntity> transactions;

        // GIVEN
        date = ZonedDateTime.now()
            .plus(1, ChronoUnit.MONTHS)
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
            .extracting(FeeTransactionEntity::getDate)
            .isEqualTo(date);
    }

    @Test
    @DisplayName("When changing a fee date, then it is returned")
    @PositiveFeeType
    @ValidProfile
    @PaidFee
    void testSave_Paid_ChangeDate_ReturnedData() {
        final Fee     created;
        final Fee     fee;
        final Instant date;

        // GIVEN
        date = ZonedDateTime.now()
            .plus(1, ChronoUnit.MONTHS)
            .toInstant();
        fee = Fees.paidAtDate(date);

        // WHEN
        created = repository.save(fee);

        // THEN

        Assertions.assertThat(created)
            .as("fee")
            .usingRecursiveComparison()
            .ignoringFields("id", "member.id", "memberId", "transaction.id", "audit")
            .isEqualTo(Fees.paidAtDate(date));
    }

    @Test
    @DisplayName("When the fee type doesn't exist, then an exception is thrown")
    @PositiveFeeType
    @ValidProfile
    @FeeTransaction
    void testSave_Paid_MissingFeeType() {
        final Fee              fee;
        final ThrowingCallable action;

        // GIVEN
        fee = Fees.alternativeFeeType();

        // WHEN
        action = () -> repository.save(fee);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the fee type doesn't exist, then an exception is thrown")
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("When the member doesn't exist, then an exception is thrown")
    @PositiveFeeType
    @FeeTransaction
    void testSave_Paid_MissingMember() {
        final Fee              fee;
        final ThrowingCallable action;

        // GIVEN
        fee = Fees.paid();

        // WHEN
        action = () -> repository.save(fee);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the member doesn't exist, then an exception is thrown")
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("When the transaction doesn't exist, then the transaction is ignored")
    @PositiveFeeType
    @ActiveMember
    void testSave_Paid_MissingTransaction() {
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "member.id", "member.feeType.id",
                "member.profile.id", "member.profile.contactChannels.id", "memberId", "feeType.id", "transaction.id",
                "audit", "member.audit", "transaction.audit")
            .containsExactly(FeeEntities.paidNoTransaction());
    }

    @Test
    @DisplayName("When valid data is saved, then the data is persisted")
    @PositiveFeeType
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "member.id", "member.feeType.id",
                "member.profile.id", "member.profile.contactChannels.id", "memberId", "feeType.id", "transaction.id",
                "audit", "member.audit", "transaction.audit")
            .containsExactly(FeeEntities.paid());
    }

    @Test
    @DisplayName("When a fee with a transaction is saved, then the fee-to-transaction relationship is persisted")
    @PositiveFeeType
    @ValidProfile
    @FeeTransaction
    void testSave_Paid_PersistedRelationship() {
        final Fee                  fee;
        final FeeEntity            feeEntity;
        final FeeTransactionEntity transactionEntity;

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
            .extracting(FeeTransactionEntity::getId)
            .isEqualTo(transactionEntity.getId());
    }

    @Test
    @DisplayName("When a fee is saved, then its transaction is persisted")
    @PositiveFeeType
    @ValidProfile
    @FeeTransaction
    void testSave_Paid_PersistedTransaction() {
        final Iterable<FeeTransactionEntity> transactions;
        final Fee                            fee;

        // GIVEN
        fee = Fees.paid();

        // WHEN
        repository.save(fee);

        // THEN
        transactions = transactionSpringRepository.findAll();

        Assertions.assertThat(transactions)
            .as("transactions")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(FeeTransactionEntities.februaryFee());
    }

    @Test
    @DisplayName("When the component is queried, then the created data is returned")
    @PositiveFeeType
    @ValidProfile
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

    @Test
    @DisplayName("When the fee is paid without a transaction, then it is persisted")
    @PositiveFeeType
    @ActiveMember
    @FeeTransaction
    void testSave_PaidNoTransaction_PersistedData() {
        final Iterable<FeeEntity> fees;
        final Fee                 fee;

        // GIVEN
        fee = Fees.paidNoTransaction();

        // WHEN
        repository.save(fee);

        // THEN
        fees = springRepository.findAll();

        Assertions.assertThat(fees)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "member.id", "member.feeType.id",
                "member.profile.id", "member.profile.contactChannels.id", "memberId", "feeType.id", "transaction.id",
                "audit", "member.audit", "transaction.audit")
            .containsExactly(FeeEntities.paidNoTransaction());
    }

    @Test
    @DisplayName("When updating from unpaid to paid, then it is persisted")
    @PositiveFeeType
    @ActiveMember
    @NotPaidFee
    @FeeTransaction
    void testSave_UnpaidToPaid_ChangeDate_PersistedData() {
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "member.id", "member.feeType.id",
                "member.profile.id", "member.profile.contactChannels.id", "memberId", "feeType.id", "transaction.id",
                "audit", "member.audit", "transaction.audit")
            .containsExactly(FeeEntities.paid());
    }

}
