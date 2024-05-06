
package com.bernardomg.association.inventory.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.inventory.adapter.inbound.jpa.model.DonorEntity;
import com.bernardomg.association.inventory.adapter.inbound.jpa.repository.DonorSpringRepository;
import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.inventory.test.config.factory.DonorEntities;
import com.bernardomg.association.inventory.test.config.factory.Donors;
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("DonorRepository - save")
class ITDonorRepositorySave {

    @Autowired
    private DonorRepository       repository;

    @Autowired
    private DonorSpringRepository springRepository;

    @Test
    @DisplayName("Persists a donor with member")
    @ValidMember
    void testSave_WithMember_PersistedData() {
        final Iterable<DonorEntity> donors;
        final Donor                 transaction;

        // GIVEN
        transaction = Donors.withMember();

        // WHEN
        repository.save(transaction);

        // THEN
        donors = springRepository.findAll();

        Assertions.assertThat(donors)
            .as("donors")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "member.id", "member.id", "member.person.id")
            .containsExactly(DonorEntities.withMember());
    }

    @Test
    @DisplayName("Returns the created data after persisting a donor with member")
    @ValidMember
    void testSave_WithMember_ReturnedData() {
        final Donor created;
        final Donor donor;

        // GIVEN
        donor = Donors.withMember();

        // WHEN
        created = repository.save(donor);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .isEqualTo(Donors.withMember());
    }

    @Test
    @DisplayName("Persists a donor without member")
    void testSave_WithoutMember_PersistedData() {
        final Iterable<DonorEntity> donors;
        final Donor                 transaction;

        // GIVEN
        transaction = Donors.withoutMember();

        // WHEN
        repository.save(transaction);

        // THEN
        donors = springRepository.findAll();

        Assertions.assertThat(donors)
            .as("donors")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(DonorEntities.withoutMember());
    }

    @Test
    @DisplayName("Returns the created data after persisting a donor without member")
    void testSave_WithoutMember_ReturnedData() {
        final Donor created;
        final Donor donor;

        // GIVEN
        donor = Donors.withoutMember();

        // WHEN
        created = repository.save(donor);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .isEqualTo(Donors.withoutMember());
    }

}
