/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.image.TestApplication;
import com.bernardomg.image.adapter.inbound.jpa.repository.ImageSpringRepository;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.data.annotation.ValidImage;
import com.bernardomg.image.test.configuration.factory.ImageEntities;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ImageRepository - save")
class ITImageRepositorySave {

    @Autowired
    private ImageRepository       repository;

    @Autowired
    private ImageSpringRepository springRepository;

    @Test
    @DisplayName("When the image name is changed, it is updated")
    @ValidImage
    void testSave_Existing_ChangeName_Persisted() {
        final Image image;

        // GIVEN
        image = Images.nameChange();

        // WHEN
        repository.save(image);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("images")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(ImageEntities.nameChange());
    }

    @Test
    @DisplayName("When the image name is changed, it is returned")
    @ValidImage
    void testSave_Existing_ChangeName_Returned() {
        final Image image;
        final Image saved;

        // GIVEN
        image = Images.nameChange();

        // WHEN
        saved = repository.save(image);

        // THEN
        Assertions.assertThat(saved)
            .as("image")
            .isEqualTo(Images.nameChange());
    }

    @Test
    @DisplayName("When saving, an image is persisted")
    void testSave_Persisted() {
        // WHEN
        repository.save(Images.valid());

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("images")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(ImageEntities.valid());
    }

    @Test
    @DisplayName("When saving, the persisted image is returned")
    void testSave_Returned() {
        final Image saved;

        // WHEN
        saved = repository.save(Images.valid());

        // THEN
        Assertions.assertThat(saved)
            .as("image")
            .isEqualTo(Images.valid());
    }
}
