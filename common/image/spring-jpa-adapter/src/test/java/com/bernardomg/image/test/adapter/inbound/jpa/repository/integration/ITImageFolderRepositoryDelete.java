/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.image.TestApplication;
import com.bernardomg.image.adapter.inbound.jpa.repository.ImageFolderSpringRepository;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.test.configuration.data.annotation.ValidImageFolder;
import com.bernardomg.image.test.configuration.factory.ImageFolderConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ImageFolderRepository - delete")
class ITImageFolderRepositoryDelete {

    @Autowired
    private ImageFolderRepository       repository;

    @Autowired
    private ImageFolderSpringRepository springRepository;

    @Test
    @DisplayName("With an image folder, it is deleted")
    @ValidImageFolder
    void testDelete() {
        // WHEN
        repository.delete(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("image folders")
            .isZero();
    }

    @Test
    @DisplayName("With no data, nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(ImageFolderConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("image folders")
            .isZero();
    }

}
