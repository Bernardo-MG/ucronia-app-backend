
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.config.factory.FeeEntities;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.member.test.config.data.annotation.SingleMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - save")
class ITFeeRepositorySave {

    @Autowired
    private FeeRepository       repository;

    @Autowired
    private FeeSpringRepository springRepository;

    @Test
    @DisplayName("When a not paid fee exists it can be persisted")
    @SingleMember
    @PaidFee
    void testSave_Existing() {
        final Iterable<FeeEntity> fees;
        final Fee                 fee;

        // GIVEN
        fee = Fees.paid();

        // WHEN
        repository.save(List.of(fee));

        // THEN
        fees = springRepository.findAll();

        Assertions.assertThat(fees)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "person.id", "personId")
            .containsExactly(FeeEntities.atDate());
    }

    @Test
    @DisplayName("Persists the data")
    @SingleMember
    void testSave_PersistedData() {
        final Iterable<FeeEntity> fees;
        final Fee                 fee;

        // GIVEN
        fee = Fees.paid();

        // WHEN
        repository.save(List.of(fee));

        // THEN
        fees = springRepository.findAll();

        Assertions.assertThat(fees)
            .as("fees")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "person.id", "personId")
            .containsExactly(FeeEntities.atDate());
    }

    @Test
    @DisplayName("Returns the created data")
    @SingleMember
    void testSave_ReturnedData() {
        final Collection<Fee> fees;
        final Fee             fee;

        // GIVEN
        fee = Fees.paid();

        // WHEN
        fees = repository.save(List.of(fee));

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.newlyCreated());
    }

}
