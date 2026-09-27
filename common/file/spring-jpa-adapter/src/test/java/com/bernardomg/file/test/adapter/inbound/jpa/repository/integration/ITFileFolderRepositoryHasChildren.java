
package com.bernardomg.file.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.file.TestApplication;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.test.configuration.data.annotation.ValidFileFolder;
import com.bernardomg.file.test.configuration.data.annotation.ValidFileFolderTree;
import com.bernardomg.file.test.configuration.factory.FileFolderConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FileFolderRepository - has children")
class ITFileFolderRepositoryHasChildren {

    @Autowired
    private FileFolderRepository repository;

    @Test
    @DisplayName("With child folders, it has children")
    @ValidFileFolderTree
    void testHasChildren() {
        final boolean hasChildren;

        // WHEN
        hasChildren = repository.hasChildren(FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(hasChildren)
            .as("has children")
            .isTrue();
    }

    @Test
    @DisplayName("Without child folders, it doesn't have children")
    @ValidFileFolder
    void testHasChildren_NoChildren() {
        final boolean hasChildren;

        // WHEN
        hasChildren = repository.hasChildren(FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(hasChildren)
            .as("has children")
            .isFalse();
    }

}
