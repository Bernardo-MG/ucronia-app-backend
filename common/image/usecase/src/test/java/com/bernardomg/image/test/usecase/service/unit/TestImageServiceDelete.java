/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service")
class TestImageServiceDelete {

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
    @DisplayName("When deleting an image, metadata and content are deleted")
    void testDelete() {
        final Image deleted;

        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(image));

        // WHEN
        deleted = service.delete(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(deleted)
            .isEqualTo(image);
        verify(client).deleteObject(DeleteObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.KEY)
            .build());
        verify(repository).delete(ImageConstants.NUMBER);
    }

    @Test
    @DisplayName("When deleting a missing image, not found is raised")
    void testDeleteMissing() {
        // GIVEN
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN + THEN
        Assertions.assertThatThrownBy(() -> service.delete(ImageConstants.NUMBER))
            .isInstanceOf(ImageNotExistingException.class);
        verify(client, never()).deleteObject(any(DeleteObjectRequest.class));
    }

}
