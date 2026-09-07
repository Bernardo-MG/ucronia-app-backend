/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.image.domain.exception.ImageNotExistingException;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.usecase.service.DefaultImageService;

import software.amazon.awssdk.services.s3.S3Client;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service")
class TestImageServiceGetOne {

    @Mock
    private S3Client            client;

    private Image               image;

    @Mock
    private ImageRepository     repository;

    private DefaultImageService service;

    @BeforeEach
    void setUp() {
        service = new DefaultImageService(repository, client, ImageConstants.BUCKET);
        image = new Image(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION, ImageConstants.KEY,
            ImageConstants.MEDIA_TYPE, ImageConstants.DATA.length);
    }

    @Test
    @DisplayName("When getting an image, its metadata is returned")
    void testGetOne() {
        final Image result;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(image));

        // WHEN
        result = service.getOne(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(result)
            .isEqualTo(image);
    }

    @Test
    @DisplayName("When getting a missing image, not found is raised")
    void testGetOneMissing() {
        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN + THEN
        Assertions.assertThatThrownBy(() -> service.getOne(ImageConstants.NUMBER))
            .isInstanceOf(ImageNotExistingException.class);
    }

}
