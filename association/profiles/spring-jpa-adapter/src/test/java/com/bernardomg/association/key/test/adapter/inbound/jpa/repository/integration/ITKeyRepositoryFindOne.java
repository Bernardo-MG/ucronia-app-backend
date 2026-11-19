
package com.bernardomg.association.key.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.key.domain.model.Key;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.key.test.configuration.factory.Keys;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithKey;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyRepository - findOne")
class ITKeyRepositoryFindOne {

    @Autowired
    private KeyRepository repository;

    @Test
    @DisplayName("When there is a key, then it is returned")
    @PositiveFeeType
    @ActiveMemberWithKey
    void testFindOne() {
        final Optional<Key> key;

        // WHEN
        key = repository.findOne(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(key)
            .as("When there is a key, then it is returned")
            .contains(Keys.available());
    }

    @Test
    @DisplayName("When there is no key, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<Key> key;

        // WHEN
        key = repository.findOne(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(key)
            .as("When there is no key, then nothing is returned")
            .isEmpty();
    }

}
