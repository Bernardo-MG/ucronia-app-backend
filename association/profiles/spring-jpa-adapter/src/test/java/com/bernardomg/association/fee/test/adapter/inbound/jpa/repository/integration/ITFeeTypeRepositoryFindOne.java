
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypeConstants;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypes;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeTypeRepository - findOne")
class ITFeeTypeRepositoryFindOne {

    @Autowired
    private FeeTypeRepository repository;

    @Test
    @DisplayName("When there is an existing fee type, then the related entity is returned")
    @PositiveFeeType
    void testFindOne() {
        final Optional<FeeType> feeType;

        // WHEN
        feeType = repository.findOne(FeeTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(feeType)
            .as("When there is an existing fee type, then the related entity is returned")
            .contains(FeeTypes.positive());
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<FeeType> feeType;

        // WHEN
        feeType = repository.findOne(FeeTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(feeType)
            .as("When there is no data, then nothing is returned")
            .isEmpty();
    }

}
