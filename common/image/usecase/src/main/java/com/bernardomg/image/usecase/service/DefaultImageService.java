/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo MartÃ­nez Garrido
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

package com.bernardomg.image.usecase.service;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;

import com.bernardomg.image.domain.model.ImageContent;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

/**
 * Loads images from an S3-compatible object store.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class DefaultImageService implements ImageService {

    private final String   bucket;

    private final S3Client client;

    public DefaultImageService(final S3Client s3Client, final String s3Bucket) {
        super();
        
        client = Objects.requireNonNull(s3Client);
        bucket = Objects.requireNonNull(s3Bucket);
    }

    @Override
    public ImageContent getImage(final String name) {
        final GetObjectRequest                 request;
        final ResponseBytes<GetObjectResponse> response;
        final String                           mediaType;

        request = GetObjectRequest.builder()
            .bucket(bucket)
            .key(name)
            .build();
        try {
            response = client.getObjectAsBytes(request);
        } catch (final NoSuchKeyException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found", ex);
        }

        mediaType = response.response()
            .contentType();
        return new ImageContent(response.asByteArray(), mediaType != null ? mediaType
                : MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

}
