
package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.image.TestApplication;
import com.bernardomg.image.adapter.inbound.jpa.repository.ImageFolderSpringRepository;
import com.bernardomg.image.domain.model.ImageFolder;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.test.configuration.data.annotation.ValidImageFolder;
import com.bernardomg.image.test.configuration.data.annotation.ValidImageFolderTree;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.image.test.configuration.factory.ImageFolderEntities;
import com.bernardomg.image.test.configuration.factory.ImageFolders;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ImageFolderRepository - save")
class ITImageFolderRepositorySave {

    @Autowired
    private ImageFolderRepository       repository;

    @Autowired
    private ImageFolderSpringRepository springRepository;

    @Test
    @DisplayName("When changing the name, it is updated")
    @ValidImageFolder
    void testSave_Existing_ChangeName_Persisted() {
        // WHEN
        repository.save(ImageFolders.nameChange());

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("image folders")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(ImageFolderEntities.nameChange());
    }

    @Test
    @DisplayName("When assigning a parent, it is updated")
    @ValidImageFolderTree
    void testSave_Existing_ChangeParent_Persisted() {
        final ImageFolder saved;

        // WHEN
        saved = repository.save(ImageFolders.withParent());

        // THEN
        Assertions.assertThat(saved.parentNumber())
            .as("parent number")
            .contains(ImageFolderConstants.PARENT_NUMBER);
    }

    @Test
    @DisplayName("When removing a parent, it is updated")
    @ValidImageFolderTree
    void testSave_Existing_RemoveParent_Persisted() {
        final ImageFolder folder;
        final ImageFolder saved;

        // GIVEN
        folder = new ImageFolder(ImageFolderConstants.CHILD_NUMBER, ImageFolderConstants.CHILD_NAME, Optional.empty());

        // WHEN
        saved = repository.save(folder);

        // THEN
        Assertions.assertThat(saved.parentNumber())
            .as("parent number")
            .isEmpty();
    }

    @Test
    @DisplayName("When saving a new image folder, it is persisted")
    void testSave_New_Persisted() {
        // WHEN
        repository.save(ImageFolders.toCreate());

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("image folders")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(ImageFolderEntities.valid());
    }

    @Test
    @DisplayName("When saving a new image folder, it is returned")
    void testSave_New_Returned() {
        final ImageFolder saved;

        // WHEN
        saved = repository.save(ImageFolders.toCreate());

        // THEN
        Assertions.assertThat(saved)
            .as("image folder")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(ImageFolders.valid());
    }

}
