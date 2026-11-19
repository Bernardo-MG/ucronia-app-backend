
package com.bernardomg.asset.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.test.configuration.TestApplication;
import com.bernardomg.asset.test.configuration.data.annotation.PublicFile;
import com.bernardomg.asset.test.configuration.factory.AssetConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetRepository - existsByNameAndFolder")
class ITAssetRepositoryExistsByNameForAnother {

    @Autowired
    private AssetRepository repository;

    @Test
    @DisplayName("When another file with the same name is provided, then it exists")
    @PublicFile
    void testExistsByNameForAnother_Another() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndFolder(AssetConstants.NAME, null, -1L);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When only the edited file is provided, then nothing exists")
    @PublicFile
    void testExistsByNameForAnother_Itself() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndFolder(AssetConstants.NAME, null, AssetConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is no data, then nothing exists")
    void testExistsByNameForAnother_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndFolder(AssetConstants.NAME, null, AssetConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }
}
