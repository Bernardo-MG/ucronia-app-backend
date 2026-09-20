/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.image.TestApplication;
import com.bernardomg.image.domain.model.ImageFolder;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.test.configuration.data.annotation.ValidImageFolder;
import com.bernardomg.image.test.configuration.factory.ImageFolders;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ImageFolderRepository - find all")
class ITImageFolderRepositoryFindAll {

    @Autowired
    private ImageFolderRepository repository;

    @Test
    @DisplayName("When there are image folders, they are returned")
    @ValidImageFolder
    void testFindAll() {
        final Collection<ImageFolder> folders;

        // WHEN
        folders = repository.findAll();

        // THEN
        Assertions.assertThat(folders)
            .as("image folders")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(ImageFolders.valid());
    }

    @Test
    @DisplayName("When there are no image folders, nothing is returned")
    void testFindAll_NoData() {
        final Collection<ImageFolder> folders;

        // WHEN
        folders = repository.findAll();

        // THEN
        Assertions.assertThat(folders)
            .as("image folders")
            .isEmpty();
    }

}
