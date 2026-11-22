
package com.bernardomg.association.key.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithKey;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyRepository - delete")
class ITKeyRepositoryDelete {

    @Autowired
    private KeyRepository repository;

    @Test
    @DisplayName("When deleting a key, then it is removed")
    @PositiveFeeType
    @ActiveMemberWithKey
    void testDelete() {
        // WHEN
        repository.delete(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(repository.findOne(KeyConstants.NUMBER))
            .as("When deleting a key, then it is removed")
            .isEmpty();
    }

    @Test
    @DisplayName("When deleting a missing key, then nothing happens")
    void testDelete_NoData() {
        // WHEN
        repository.delete(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(repository.findOne(KeyConstants.NUMBER))
            .as("When deleting a missing key, then nothing happens")
            .isEmpty();
    }

}
