
package com.bernardomg.file.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.file.TestApplication;
import com.bernardomg.file.test.configuration.data.annotation.ValidFileFolder;
import com.bernardomg.file.test.configuration.factory.FileFolderConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AssetFolderRepository - exists")
class ITFileFolderRepositoryExists {

    @Autowired
    private AssetFolderRepository repository;

    @Test
    @DisplayName("With an file folder, it exists")
    @ValidFileFolder
    void testExists() {
        final boolean exists;

        // WHEN
        exists = repository.exists(FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("With no data, it doesn't exist")
    void testExists_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.exists(FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
