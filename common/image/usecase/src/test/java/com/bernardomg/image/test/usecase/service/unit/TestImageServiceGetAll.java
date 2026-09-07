/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.usecase.service.DefaultImageService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

import software.amazon.awssdk.services.s3.S3Client;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service")
class TestImageServiceGetAll {

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
    @DisplayName("When getting all images, the requested page is returned")
    void testGetAll() {
        final Page<Image> existing;
        final Page<Image> result;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(image), 10, 1, 1, 1, 1, true, true, sorting);
        given(repository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        result = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(result)
            .isEqualTo(existing);
        verify(repository).findAll(pagination, sorting);
    }

}
