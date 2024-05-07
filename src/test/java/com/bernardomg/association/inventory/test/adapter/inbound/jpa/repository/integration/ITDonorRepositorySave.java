
package com.bernardomg.association.inventory.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.inventory.test.config.factory.Donors;
import com.bernardomg.association.member.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.member.test.config.data.annotation.ValidPerson;
import com.bernardomg.association.member.test.config.factory.PersonEntities;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("DonorRepository - save")
class ITDonorRepositorySave {

    @Autowired
    private DonorRepository        repository;

    @Autowired
    private PersonSpringRepository springRepository;

    @Test
    @DisplayName("Persists a donor")
    @ValidPerson
    void testSave_PersistedData() {
        final Iterable<PersonEntity> donors;
        final Donor                  transaction;

        // GIVEN
        transaction = Donors.valid();

        // WHEN
        repository.save(transaction);

        // THEN
        donors = springRepository.findAll();

        Assertions.assertThat(donors)
            .as("donors")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "member.id", "member.id",
                "member.person.id")
            .containsExactly(PersonEntities.minimal());
    }

    @Test
    @DisplayName("Returns the created data after persisting a donor")
    @ValidPerson
    void testSave_ReturnedData() {
        final Donor created;
        final Donor donor;

        // GIVEN
        donor = Donors.valid();

        // WHEN
        created = repository.save(donor);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .isEqualTo(Donors.valid());
    }

}
