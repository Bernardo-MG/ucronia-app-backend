
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeTypeSpringRepository;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypeConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeTypeRepository - delete")
class ITFeeTypeRepositoryDelete {

    @Autowired
    private FeeTypeSpringRepository feeTypeSpringRepository;

    @Autowired
    private FeeTypeRepository       repository;

    @Test
    @DisplayName("When an entity is deleted, then it is removed")
    @PositiveFeeType
    void testDelete() {
        // WHEN
        repository.delete(FeeTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(feeTypeSpringRepository.count())
            .as("feeTypes")
            .isZero();
    }

    @Test
    @DisplayName("When there is no data, then nothing is removed")
    void testDelete_NoData() {
        // WHEN
        repository.delete(FeeTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(feeTypeSpringRepository.count())
            .as("feeTypes")
            .isZero();
    }

}
