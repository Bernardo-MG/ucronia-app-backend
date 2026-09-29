
package com.bernardomg.asset.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.adapter.inbound.jpa.repository.AssetSpringRepository;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.test.configuration.TestApplication;
import com.bernardomg.asset.test.configuration.data.annotation.PublicFile;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.asset.test.configuration.factory.AssetEntities;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetRepository - save")
class ITAssetRepositorySave {

    @Autowired
    private AssetRepository       repository;

    @Autowired
    private AssetSpringRepository springRepository;

    @Test
    @DisplayName("When the file name is changed, it is updated")
    @PublicFile
    void testSave_Existing_ChangeName_Persisted() {
        final Asset file;

        // GIVEN
        file = Assets.nameChange();

        // WHEN
        repository.save(file);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("files")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(AssetEntities.nameChange());
    }

    @Test
    @DisplayName("When the file name is changed, it is returned")
    @PublicFile
    void testSave_Existing_ChangeName_Returned() {
        final Asset file;
        final Asset saved;

        // GIVEN
        file = Assets.nameChange();

        // WHEN
        saved = repository.save(file);

        // THEN
        Assertions.assertThat(saved)
            .as("file")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Assets.nameChange());
    }

    @Test
    @DisplayName("When saving, an file is persisted")
    void testSave_Persisted() {
        // WHEN
        repository.save(Assets.publicAccess());

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("files")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(AssetEntities.publicAccess());
    }

    @Test
    @DisplayName("When saving, the persisted file is returned")
    void testSave_Returned() {
        final Asset saved;

        // WHEN
        saved = repository.save(Assets.publicAccess());

        // THEN
        Assertions.assertThat(saved)
            .as("file")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Assets.publicAccess());
    }
}
