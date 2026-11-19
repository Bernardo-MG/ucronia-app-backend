
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeRepository - delete")
class ITFeeRepositoryDelete {

    @Autowired
    private FeeSpringRepository feeSpringRepository;

    @Autowired
    private FeeRepository       repository;

    @Test
    @DisplayName("When there is no data, then nothing is removed")
    void testDelete_NoData() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(feeSpringRepository.count())
            .as("fees")
            .isZero();
    }

    @Test
    @DisplayName("When there is no fee, then nothing is removed")
    @PositiveFeeType
    @ValidProfile
    void testDelete_NoFee() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(feeSpringRepository.count())
            .as("fees")
            .isZero();
    }

    @Test
    @DisplayName("When a not paid entity is deleted, then it is removed")
    @PositiveFeeType
    @ValidProfile
    @NotPaidFee
    void testDelete_NotPaid() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(feeSpringRepository.count())
            .as("fees")
            .isZero();
    }

    @Test
    @DisplayName("When a paid entity is deleted, then it is removed")
    @PositiveFeeType
    @ValidProfile
    @PaidFee
    void testDelete_Paid() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(feeSpringRepository.count())
            .as("fees")
            .isZero();
    }

}
