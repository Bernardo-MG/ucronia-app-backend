
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.member.adapter.inbound.jpa.model.KeyEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.KeySpringRepository;
import com.bernardomg.association.member.domain.model.Key;
import com.bernardomg.association.member.domain.repository.KeyRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.AvailableKey;
import com.bernardomg.association.member.test.configuration.factory.KeyEntities;
import com.bernardomg.association.member.test.configuration.factory.Keys;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyRepository - save")
class ITKeyRepositorySave {

    @Autowired
    private KeyRepository       repository;

    @Autowired
    private KeySpringRepository springRepository;

    public ITKeyRepositorySave() {
        super();
    }

    @Test
    @DisplayName("When changing a key, the key is persisted")
    @AvailableKey
    void testSave_Change_PersistedData() {
        final Key                 key;
        final Iterable<KeyEntity> entities;

        // GIVEN
        key = Keys.descriptionChange();

        // WHEN
        repository.save(key);

        // THEN
        entities = springRepository.findAll();

        assertThat(entities).as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(KeyEntities.descriptionChange());
    }

    @Test
    @DisplayName("With a valid key, the key is persisted")
    void testSave_PersistedData() {
        final Key                 key;
        final Iterable<KeyEntity> entities;

        // GIVEN
        key = Keys.available();

        // WHEN
        repository.save(key);

        // THEN
        entities = springRepository.findAll();

        assertThat(entities).as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(KeyEntities.available());
    }

    @Test
    @DisplayName("With a valid key, the created key is returned")
    void testSave_ReturnedData() {
        final Key key;
        final Key saved;

        // GIVEN
        key = Keys.available();

        // WHEN
        saved = repository.save(key);

        // THEN
        assertThat(saved).as("key")
            .isEqualTo(Keys.available());
    }

}
