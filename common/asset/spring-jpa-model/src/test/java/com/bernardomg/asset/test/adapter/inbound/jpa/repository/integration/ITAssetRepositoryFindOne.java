
package com.bernardomg.asset.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.test.configuration.TestApplication;
import com.bernardomg.asset.test.configuration.data.annotation.PublicFile;
import com.bernardomg.asset.test.configuration.factory.AssetConstants;
import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetRepository - findOne")
class ITAssetRepositoryFindOne {

    @Autowired
    private AssetRepository repository;

    @Test
    @DisplayName("When there is an file, then it is returned")
    @PublicFile
    void testFindOne() {
        final Optional<Asset> file;

        // WHEN
        file = repository.findOne(AssetConstants.NUMBER);

        // THEN
        Assertions.assertThat(file)
            .as("file")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is an file, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Assets.publicAccess()));
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<Asset> file;

        // WHEN
        file = repository.findOne(AssetConstants.NUMBER);

        // THEN
        Assertions.assertThat(file)
            .as("file")
            .isEmpty();
    }
}
