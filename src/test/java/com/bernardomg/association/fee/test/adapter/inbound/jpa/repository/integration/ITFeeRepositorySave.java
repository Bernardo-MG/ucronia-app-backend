
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

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
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActivePerson;
import com.bernardomg.association.transaction.configuration.data.annotation.FeeTransaction;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - save")
class ITFeeRepositorySave {

    @Autowired
    private FeeRepository       repository;

    @Autowired
    private FeeSpringRepository springRepository;

    @Test
    @DisplayName("When saving an existing fee, it is returned")
    @MembershipActivePerson
    @PaidFee
    void testSave_Existing_ReturnedData() {
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "person.id", "personId", "transactionId",
                "transaction.id")
            .containsExactly(FeeEntities.paid());
    }

    @Test
    @DisplayName("Persists the data")
    @MembershipActivePerson
    @FeeTransaction
    void testSave_PersistedData() {
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "person.id", "personId", "transactionId",
                "transaction.id")
            .containsExactly(FeeEntities.paid());
    }

    @Test
    @DisplayName("Returns the created data")
    @MembershipActivePerson
    @FeeTransaction
    void testSave_ReturnedData() {
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
