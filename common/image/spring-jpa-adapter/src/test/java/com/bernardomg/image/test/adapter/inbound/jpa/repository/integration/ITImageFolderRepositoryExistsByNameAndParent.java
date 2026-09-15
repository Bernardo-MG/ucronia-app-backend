/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.image.TestApplication;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.test.configuration.data.annotation.ValidImageFolder;
import com.bernardomg.image.test.configuration.data.annotation.ValidImageFolderTree;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ImageFolderRepository - exists by name and parent")
class ITImageFolderRepositoryExistsByNameAndParent {

    @Autowired
    private ImageFolderRepository repository;

    @Test
    @DisplayName("With a child folder with the name and parent, it exists")
    @ValidImageFolderTree
    void testExistsByNameAndParent_Child() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(ImageFolderConstants.CHILD_NAME, ImageFolderConstants.NUMBER, null);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When the matching folder is excluded, it doesn't exist")
    @ValidImageFolderTree
    void testExistsByNameAndParent_Excluded() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(ImageFolderConstants.CHILD_NAME, ImageFolderConstants.NUMBER,
            ImageFolderConstants.CHILD_NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("With no matching folder, it doesn't exist")
    void testExistsByNameAndParent_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(ImageFolderConstants.NAME, null, null);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("With a root folder with the name, it exists")
    @ValidImageFolder
    void testExistsByNameAndParent_Root() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(ImageFolderConstants.NAME, null, null);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When the matching root folder is excluded, it doesn't exist")
    @ValidImageFolder
    void testExistsByNameAndParent_Root_Excluded() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(ImageFolderConstants.NAME, null, ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
