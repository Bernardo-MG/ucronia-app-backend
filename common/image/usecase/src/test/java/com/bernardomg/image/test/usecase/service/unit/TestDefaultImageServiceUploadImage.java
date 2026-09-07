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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.image.domain.exception.ImageAlreadyExistsException;
import com.bernardomg.image.domain.model.ImageContent;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.usecase.service.DefaultImageService;
import com.bernardomg.image.usecase.service.ImageService;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service - upload image")
class TestImageServiceUploadImage {

    @Mock
    private S3Client     client;

    private ImageService service;

    @BeforeEach
    public void setupService() {
        service = new DefaultImageService(client, ImageConstants.BUCKET);
    }

    @Test
    @DisplayName("When uploading an image, its data and metadata are sent to storage")
    void testUploadImage() throws IOException {
        final ArgumentCaptor<PutObjectRequest> requestCaptor;
        final ArgumentCaptor<RequestBody>      bodyCaptor;
        final HeadObjectRequest                headRequest;

        // GIVEN
        requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        bodyCaptor = ArgumentCaptor.forClass(RequestBody.class);
        headRequest = HeadObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.NAME)
            .build();

        given(client.headObject(headRequest)).willThrow(S3Exception.builder()
            .statusCode(404)
            .build());

        // WHEN
        service.createImage(ImageConstants.NAME, new ImageContent(ImageConstants.DATA, ImageConstants.MEDIA_TYPE));

        // THEN
        verify(client).headObject(headRequest);
        verify(client).putObject(requestCaptor.capture(), bodyCaptor.capture());

        Assertions.assertThat(requestCaptor.getValue()
            .bucket())
            .isEqualTo(ImageConstants.BUCKET);
        Assertions.assertThat(requestCaptor.getValue()
            .key())
            .isEqualTo(ImageConstants.NAME);
        Assertions.assertThat(requestCaptor.getValue()
            .contentType())
            .isEqualTo(ImageConstants.MEDIA_TYPE);
        Assertions.assertThat(bodyCaptor.getValue()
            .contentStreamProvider()
            .newStream()
            .readAllBytes())
            .containsExactly(ImageConstants.DATA);
    }

    @Test
    @DisplayName("When uploading an existing image, a conflict is raised")
    void testUploadImage_Existing() {
        final HeadObjectRequest headRequest;

        // GIVEN
        headRequest = HeadObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.NAME)
            .build();

        given(client.headObject(headRequest)).willReturn(HeadObjectResponse.builder()
            .build());

        // WHEN + THEN
        Assertions
            .assertThatThrownBy(() -> service.createImage(ImageConstants.NAME,
                new ImageContent(ImageConstants.DATA, ImageConstants.MEDIA_TYPE)))
            .isInstanceOfSatisfying(ImageAlreadyExistsException.class, ex -> Assertions.assertThat(ex.getName())
                .isEqualTo(ImageConstants.NAME));
        verify(client).headObject(headRequest);
        verify(client, never()).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

}
