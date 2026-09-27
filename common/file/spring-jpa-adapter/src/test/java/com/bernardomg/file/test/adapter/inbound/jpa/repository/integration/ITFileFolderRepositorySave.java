
package com.bernardomg.file.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.file.TestApplication;
import com.bernardomg.file.adapter.inbound.jpa.repository.FileFolderSpringRepository;
import com.bernardomg.file.domain.model.FileFolder;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.test.configuration.data.annotation.ValidFileFolder;
import com.bernardomg.file.test.configuration.data.annotation.ValidFileFolderTree;
import com.bernardomg.file.test.configuration.factory.FileFolderConstants;
import com.bernardomg.file.test.configuration.factory.FileFolderEntities;
import com.bernardomg.file.test.configuration.factory.FileFolders;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FileFolderRepository - save")
class ITFileFolderRepositorySave {

    @Autowired
    private FileFolderRepository       repository;

    @Autowired
    private FileFolderSpringRepository springRepository;

    @Test
    @DisplayName("When changing the name, it is updated")
    @ValidFileFolder
    void testSave_Existing_ChangeName_Persisted() {
        // WHEN
        repository.save(FileFolders.nameChange());

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("file folders")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(FileFolderEntities.nameChange());
    }

    @Test
    @DisplayName("When assigning a parent, it is updated")
    @ValidFileFolderTree
    void testSave_Existing_ChangeParent_Persisted() {
        final FileFolder saved;

        // WHEN
        saved = repository.save(FileFolders.withParent());

        // THEN
        Assertions.assertThat(saved.parentNumber())
            .as("parent number")
            .contains(FileFolderConstants.PARENT_NUMBER);
    }

    @Test
    @DisplayName("When removing a parent, it is updated")
    @ValidFileFolderTree
    void testSave_Existing_RemoveParent_Persisted() {
        final FileFolder folder;
        final FileFolder saved;

        // GIVEN
        folder = new FileFolder(FileFolderConstants.CHILD_NUMBER, FileFolderConstants.CHILD_NAME, Optional.empty());

        // WHEN
        saved = repository.save(folder);

        // THEN
        Assertions.assertThat(saved.parentNumber())
            .as("parent number")
            .isEmpty();
    }

    @Test
    @DisplayName("When saving a new file folder, it is persisted")
    void testSave_New_Persisted() {
        // WHEN
        repository.save(FileFolders.toCreate());

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("file folders")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(FileFolderEntities.valid());
    }

    @Test
    @DisplayName("When saving a new file folder, it is returned")
    void testSave_New_Returned() {
        final FileFolder saved;

        // WHEN
        saved = repository.save(FileFolders.toCreate());

        // THEN
        Assertions.assertThat(saved)
            .as("file folder")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(FileFolders.valid());
    }

}
