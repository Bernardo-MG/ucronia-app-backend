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

import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.model.ImageContent;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.usecase.service.DefaultImageService;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service")
class TestImageServiceGetContent {

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
    @DisplayName("When getting image content, it is loaded from storage")
    void testGetContent() {
        final ImageContent      content;
        final GetObjectRequest  request;
        final GetObjectResponse response;

        // GIVEN
        request = GetObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.KEY)
            .build();
        response = GetObjectResponse.builder()
            .contentType(ImageConstants.MEDIA_TYPE)
            .build();
        given(repository.findOne(ImageConstants.NUMBER)).willReturn(Optional.of(image));
        given(client.getObjectAsBytes(request)).willReturn(ResponseBytes.fromByteArray(response, ImageConstants.DATA));

        // WHEN
        content = service.getContent(ImageConstants.NUMBER);

        // THEN
        Assertions.assertThat(content.data())
            .containsExactly(ImageConstants.DATA);
    }

}
