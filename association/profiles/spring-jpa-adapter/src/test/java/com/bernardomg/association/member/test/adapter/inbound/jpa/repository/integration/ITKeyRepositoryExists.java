
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.member.test.configuration.data.annotation.AvailableKey;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyRepository - exists")
class ITKeyRepositoryExists {

    @Autowired
    private KeyRepository repository;

    @Test
    @DisplayName("With a key, it exists")
    @AvailableKey
    void testExists_Key() {
        final boolean exists;

        // WHEN
        exists = repository.exists(KeyConstants.NUMBER);

        // THEN
        assertThat(exists).as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("With no key, nothing exists")
    void testExists_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.exists(KeyConstants.NUMBER);

        // THEN
        assertThat(exists).as("exists")
            .isFalse();
    }

}
