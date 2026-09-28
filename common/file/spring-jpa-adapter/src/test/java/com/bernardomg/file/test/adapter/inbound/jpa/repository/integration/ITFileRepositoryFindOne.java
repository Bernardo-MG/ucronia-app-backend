
package com.bernardomg.file.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.file.TestApplication;
import com.bernardomg.file.test.configuration.data.annotation.PublicFile;
import com.bernardomg.file.test.configuration.factory.FileConstants;
import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetRepository - find one")
class ITFileRepositoryFindOne {

    @Autowired
    private AssetRepository repository;

    @Test
    @DisplayName("With an file, it is returned")
    @PublicFile
    void testFindOne() {
        final Optional<Asset> file;

        // WHEN
        file = repository.findOne(FileConstants.NUMBER);

        // THEN
        Assertions.assertThat(file)
            .as("file")
            .contains(Files.publicAccess());
    }

    @Test
    @DisplayName("With no data, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Asset> file;

        // WHEN
        file = repository.findOne(FileConstants.NUMBER);

        // THEN
        Assertions.assertThat(file)
            .as("file")
            .isEmpty();
    }
}
