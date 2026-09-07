/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.image.domain.exception.ImageAlreadyExistsException;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.model.ImageContent;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.usecase.service.DefaultImageService;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service")
class TestImageServiceCreate {

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
    @DisplayName("When creating an image, metadata and content are persisted")
    void testCreate() {
        final Image created;

        // GIVEN
        given(repository.save(any(Image.class))).willReturn(image);

        // WHEN
        created = service.create(image, new ImageContent(ImageConstants.DATA, ImageConstants.MEDIA_TYPE));

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(image);
        verify(repository).existsByName(ImageConstants.NAME);
        verify(client).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    @DisplayName("When creating an image with an existing name, conflict is raised")
    void testCreateExisting() {
        // GIVEN
        given(repository.existsByName(ImageConstants.NAME)).willReturn(true);

        // WHEN + THEN
        Assertions
            .assertThatThrownBy(
                () -> service.create(image, new ImageContent(ImageConstants.DATA, ImageConstants.MEDIA_TYPE)))
            .isInstanceOf(ImageAlreadyExistsException.class);
        verify(client, never()).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

}
