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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.image.domain.exception.ImageNotExistingException;
import com.bernardomg.image.test.configuration.factory.ImageConstants;
import com.bernardomg.image.usecase.service.DefaultImageService;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service - delete image")
class TestImageServiceDeleteImage {

    @Mock
    private S3Client client;

    @Test
    @DisplayName("When deleting an image, it is removed from storage")
    void testDeleteImage() {
        final DeleteObjectRequest deleteRequest;
        final HeadObjectRequest   headRequest;
        final DefaultImageService service;

        // GIVEN
        service = new DefaultImageService(client, ImageConstants.BUCKET);
        headRequest = HeadObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.NAME)
            .build();
        deleteRequest = DeleteObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.NAME)
            .build();

        given(client.headObject(headRequest)).willReturn(HeadObjectResponse.builder()
            .build());

        // WHEN
        service.deleteImage(ImageConstants.NAME);

        // THEN
        verify(client).headObject(headRequest);
        verify(client).deleteObject(deleteRequest);
    }

    @Test
    @DisplayName("When deleting a missing image, not found is raised")
    void testDeleteImage_Missing() {
        final HeadObjectRequest   headRequest;
        final DefaultImageService service;

        // GIVEN
        service = new DefaultImageService(client, ImageConstants.BUCKET);
        headRequest = HeadObjectRequest.builder()
            .bucket(ImageConstants.BUCKET)
            .key(ImageConstants.NAME)
            .build();

        given(client.headObject(headRequest)).willThrow(S3Exception.builder()
            .statusCode(404)
            .build());

        // WHEN + THEN
        Assertions.assertThatThrownBy(() -> service.deleteImage(ImageConstants.NAME))
            .isInstanceOfSatisfying(ImageNotExistingException.class, ex -> Assertions.assertThat(ex.getName())
                .isEqualTo(ImageConstants.NAME));
        verify(client).headObject(headRequest);
        verify(client, never()).deleteObject(any(DeleteObjectRequest.class));
    }

}
