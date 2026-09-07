/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.image.TestApplication;
import com.bernardomg.image.adapter.inbound.jpa.repository.ImageSpringRepository;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.data.annotation.ValidImage;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ImageRepository - delete")
class ITImageRepositoryDelete {

    @Autowired
    private ImageRepository       repository;

    @Autowired
    private ImageSpringRepository springRepository;

    @Test
    @DisplayName("With an image, it is deleted")
    @ValidImage
    void testDelete() {
        // WHEN
        repository.delete(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("images")
            .isZero();
    }

    @Test
    @DisplayName("With no data, nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("images")
            .isZero();
    }
}
