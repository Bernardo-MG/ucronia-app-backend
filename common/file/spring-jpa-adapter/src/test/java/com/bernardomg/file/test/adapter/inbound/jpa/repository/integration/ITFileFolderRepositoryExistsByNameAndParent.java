
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
@DisplayName("FileFolderRepository - exists by name and parent")
class ITFileFolderRepositoryExistsByNameAndParent {

    @Autowired
    private FileFolderRepository repository;

    @Test
    @DisplayName("With a child folder with the name and parent, it exists")
    @ValidFileFolderTree
    void testExistsByNameAndParent_Child() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(FileFolderConstants.CHILD_NAME, FileFolderConstants.NUMBER, null);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When the matching folder is excluded, it doesn't exist")
    @ValidFileFolderTree
    void testExistsByNameAndParent_Excluded() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(FileFolderConstants.CHILD_NAME, FileFolderConstants.NUMBER,
            FileFolderConstants.CHILD_NUMBER);

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
        exists = repository.existsByNameAndParent(FileFolderConstants.NAME, null, null);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("With a root folder with the name, it exists")
    @ValidFileFolder
    void testExistsByNameAndParent_Root() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(FileFolderConstants.NAME, null, null);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When the matching root folder is excluded, it doesn't exist")
    @ValidFileFolder
    void testExistsByNameAndParent_Root_Excluded() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameAndParent(FileFolderConstants.NAME, null, FileFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
