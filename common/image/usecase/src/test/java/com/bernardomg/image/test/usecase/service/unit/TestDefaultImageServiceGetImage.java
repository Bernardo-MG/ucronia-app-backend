/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.image.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.image.domain.model.ImageContent;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.usecase.service.DefaultImageService;
import com.bernardomg.image.usecase.service.ImageService;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service - get image")
class TestImageServiceGetImage {

    @Mock
    private S3Client     client;

    private ImageService service;

    @BeforeEach
    public void setupService() {
        service = new DefaultImageService(client, ImageConstants.BUCKET);
    }

    @Test
    @DisplayName("When getting an image, its data and media type are returned")
    void testGetImage() {
        final ImageContent                     image;
        final GetObjectRequest                 request;
        final ResponseBytes<GetObjectResponse> response;

        // GIVEN
        request = GetObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.NAME)
            .build();
        response = ResponseBytes.fromByteArray(GetObjectResponse.builder()
            .contentType(ImageConstants.MEDIA_TYPE)
            .build(), ImageConstants.DATA);

        given(client.getObjectAsBytes(request)).willReturn(response);

        // WHEN
        image = service.getImage(ImageConstants.NAME);

        // THEN
        Assertions.assertThat(image.data())
            .containsExactly(ImageConstants.DATA);
        Assertions.assertThat(image.mediaType())
            .isEqualTo(ImageConstants.MEDIA_TYPE);
        verify(client).getObjectAsBytes(request);
    }

}
