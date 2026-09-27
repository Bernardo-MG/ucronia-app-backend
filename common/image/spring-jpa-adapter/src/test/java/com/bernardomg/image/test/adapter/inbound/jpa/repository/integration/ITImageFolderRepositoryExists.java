
package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.image.TestApplication;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.test.configuration.data.annotation.ValidImageFolder;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ImageFolderRepository - exists")
class ITImageFolderRepositoryExists {

    @Autowired
    private ImageFolderRepository repository;

    @Test
    @DisplayName("With an image folder, it exists")
    @ValidImageFolder
    void testExists() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ImageFolderConstants.NUMBER);

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
        exists = repository.exists(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
