
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
@DisplayName("ImageFolderRepository - has children")
class ITImageFolderRepositoryHasChildren {

    @Autowired
    private ImageFolderRepository repository;

    @Test
    @DisplayName("With child folders, it has children")
    @ValidImageFolderTree
    void testHasChildren() {
        final boolean hasChildren;

        // WHEN
        hasChildren = repository.hasChildren(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(hasChildren)
            .as("has children")
            .isTrue();
    }

    @Test
    @DisplayName("Without child folders, it doesn't have children")
    @ValidImageFolder
    void testHasChildren_NoChildren() {
        final boolean hasChildren;

        // WHEN
        hasChildren = repository.hasChildren(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(hasChildren)
            .as("has children")
            .isFalse();
    }

}
