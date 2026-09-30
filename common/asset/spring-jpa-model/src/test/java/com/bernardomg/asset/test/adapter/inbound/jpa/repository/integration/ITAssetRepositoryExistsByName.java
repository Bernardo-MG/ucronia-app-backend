
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
@DisplayName("AssetRepository - exists by name")
class ITAssetRepositoryExistsByName {

    @Autowired
    private AssetRepository repository;

    @Test
    @DisplayName("With an file with the name, it exists")
    @PublicFile
    void testExistsByName() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndFolder(AssetConstants.NAME, null);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("With no data, it doesn't exist")
    void testExistsByName_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndFolder(AssetConstants.NAME, null);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }
}
