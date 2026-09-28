
package com.bernardomg.file.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.file.TestApplication;
import com.bernardomg.file.test.configuration.data.annotation.PublicFile;
import com.bernardomg.file.test.configuration.factory.FileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetRepository - exists by name for another")
class ITFileRepositoryExistsByNameForAnother {

    @Autowired
    private AssetRepository repository;

    @Test
    @DisplayName("With another file with the same name, it exists")
    @PublicFile
    void testExistsByNameForAnother_Another() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndFolder(FileConstants.NAME, null, -1L);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("With only the edited file, nothing exists")
    @PublicFile
    void testExistsByNameForAnother_Itself() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndFolder(FileConstants.NAME, null, FileConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("With no data, nothing exists")
    void testExistsByNameForAnother_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndFolder(FileConstants.NAME, null, FileConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }
}
