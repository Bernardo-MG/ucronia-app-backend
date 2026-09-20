/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.image.TestApplication;
import com.bernardomg.image.domain.model.ImageFolder;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.test.configuration.data.annotation.ValidImageFolder;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.image.test.configuration.factory.ImageFolders;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ImageFolderRepository - find one")
class ITImageFolderRepositoryFindOne {

    @Autowired
    private ImageFolderRepository repository;

    @Test
    @DisplayName("With an image folder, it is returned")
    @ValidImageFolder
    void testFindOne() {
        final Optional<ImageFolder> folder;

        // WHEN
        folder = repository.findOne(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(folder)
            .as("image folder")
            .get()
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(ImageFolders.valid());
    }

    @Test
    @DisplayName("With no data, nothing is returned")
    void testFindOne_NoData() {
        final Optional<ImageFolder> folder;

        // WHEN
        folder = repository.findOne(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(folder)
            .as("image folder")
            .isEmpty();
    }

}
