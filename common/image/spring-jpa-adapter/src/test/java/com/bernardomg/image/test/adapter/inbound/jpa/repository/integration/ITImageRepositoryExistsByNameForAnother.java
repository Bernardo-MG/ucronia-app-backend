/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.image.TestApplication;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.data.annotation.ValidImage;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ImageRepository - exists by name for another")
class ITImageRepositoryExistsByNameForAnother {

    @Autowired
    private ImageRepository repository;

    @Test
    @DisplayName("With another image with the same name, it exists")
    @ValidImage
    void testExistsByNameForAnother_Another() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameForAnother(ImageConstants.NAME, -1L);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("With only the edited image, nothing exists")
    @ValidImage
    void testExistsByNameForAnother_Itself() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameForAnother(ImageConstants.NAME, ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("With no data, nothing exists")
    void testExistsByNameForAnother_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameForAnother(ImageConstants.NAME, ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }
}
