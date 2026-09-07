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

import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.image.domain.model.ImageContent;
import com.bernardomg.image.usecase.service.DefaultImageService;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@ExtendWith(MockitoExtension.class)
@DisplayName("Image service - upload image")
class TestImageServiceUploadImage {

    private static final String BUCKET = "images";

    private static final byte[] DATA = { 1, 2, 3 };

    private static final String MEDIA_TYPE = "image/png";

    private static final String NAME = "image.png";

    @Mock
    private S3Client client;

    @Test
    @DisplayName(
        "When uploading an image, its data and metadata are sent to storage"
    )
    void testUploadImage() throws IOException {
        final ArgumentCaptor<PutObjectRequest> requestCaptor;
        final ArgumentCaptor<RequestBody> bodyCaptor;
        final DefaultImageService service;

        // GIVEN
        requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        bodyCaptor = ArgumentCaptor.forClass(RequestBody.class);
        service = new DefaultImageService(client, BUCKET);

        // WHEN
        service.uploadImage(NAME, new ImageContent(DATA, MEDIA_TYPE));

        // THEN
        verify(client).putObject(
            requestCaptor.capture(),
            bodyCaptor.capture()
        );

        Assertions.assertThat(requestCaptor.getValue().bucket())
            .isEqualTo(BUCKET);
        Assertions.assertThat(requestCaptor.getValue().key())
            .isEqualTo(NAME);
        Assertions.assertThat(requestCaptor.getValue().contentType())
            .isEqualTo(MEDIA_TYPE);
        Assertions.assertThat(
            bodyCaptor.getValue()
                .contentStreamProvider()
                .newStream()
                .readAllBytes()
        ).containsExactly(DATA);
    }
    
}
