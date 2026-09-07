/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.image.TestApplication;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.data.annotation.ValidImage;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ImageRepository - find one")
class ITImageRepositoryFindOne {

    @Autowired
    private ImageRepository repository;

    @Test
    @DisplayName("With an image, it is returned")
    @ValidImage
    void testFindOne() {
        final Optional<Image> image;

        // WHEN
        image = repository.findOne(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(image)
            .as("image")
            .contains(Images.valid());
    }

    @Test
    @DisplayName("With no data, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Image> image;

        // WHEN
        image = repository.findOne(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(image)
            .as("image")
            .isEmpty();
    }
}
