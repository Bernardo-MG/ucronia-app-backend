
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeTypeSpringRepository;
import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypeEntities;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypes;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.transaction.configuration.data.annotation.FeeTransaction;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeTypeRepository - save")
class ITFeeTypeRepositorySave {

    @Autowired
    private FeeTypeRepository       repository;

    @Autowired
    private FeeTypeSpringRepository springRepository;

    @Test
    @DisplayName("When changing a fee type name, it is persisted")
    @PositiveFeeType
    void testSave_ChangeName_PersistedData() {
        final Iterable<FeeTypeEntity> feeTypes;
        final FeeType                 feeType;

        // GIVEN
        feeType = FeeTypes.nameChange();

        // WHEN
        repository.save(feeType);

        // THEN
        feeTypes = springRepository.findAll();

        // TODO: check the transaction date changes
        Assertions.assertThat(feeTypes)
            .as("feeTypes")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(FeeTypeEntities.nameChange());
    }

    @Test
    @DisplayName("When changing a fee type name, it is returned")
    @PositiveFeeType
    void testSave_ChangeName_ReturnedData() {
        final FeeType created;
        final FeeType feeType;

        // GIVEN
        feeType = FeeTypes.nameChange();

        // WHEN
        created = repository.save(feeType);

        // THEN

        Assertions.assertThat(created)
            .as("fee")
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(FeeTypes.nameChange());
    }

    @Test
    @DisplayName("Persists the data")
    void testSave_PersistedData() {
        final Iterable<FeeTypeEntity> feeTypes;
        final FeeType                 feeType;

        // GIVEN
        feeType = FeeTypes.positive();

        // WHEN
        repository.save(feeType);

        // THEN
        feeTypes = springRepository.findAll();

        Assertions.assertThat(feeTypes)
            .as("feeTypes")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(FeeTypeEntities.positive());
    }

    @Test
    @DisplayName("Returns the created data")
    @PositiveFeeType
    @ActiveMember
    @FeeTransaction
    void testSave_ReturnedData() {
        final FeeType created;
        final FeeType feeType;

        // GIVEN
        feeType = FeeTypes.positive();

        // WHEN
        created = repository.save(feeType);

        // THEN
        Assertions.assertThat(created)
            .as("feeTypes")
            .isEqualTo(FeeTypes.positive());
    }

}
