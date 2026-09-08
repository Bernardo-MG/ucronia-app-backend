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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.bernardomg.image.domain.exception.ImageAlreadyExistsException;
import com.bernardomg.image.domain.exception.ImageNotExistingException;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.model.ImageContent;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

import jakarta.transaction.Transactional;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/**
 * Loads images from an S3-compatible object store.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Transactional
public final class DefaultImageService implements ImageService {

    /**
     * Logger for the class.
     */
    private static final Logger   log = LoggerFactory.getLogger(DefaultImageService.class);

    private final String          bucket;

    private final S3Client        client;

    private final ImageRepository repository;

    public DefaultImageService(final ImageRepository imageRepository, final S3Client s3Client, final String s3Bucket) {
        super();

        client = Objects.requireNonNull(s3Client);
        repository = Objects.requireNonNull(imageRepository);
        bucket = Objects.requireNonNull(s3Bucket);
    }

    @Override
    public final Image create(final Image image, final ImageContent content) {
        final Image toCreate;
        final Image created;

        if (repository.existsByName(image.name())) {
            log.error("Image {} already exists", image.name());
            throw new ImageAlreadyExistsException(image.name());
        }

        toCreate = new Image(image.number(), image.name(), image.description(), image.key(), content.mediaType(),
            content.data().length);
        created = repository.save(toCreate);
        storeImage(created.key(), content);

        return created;
    }

    @Override
    public final Image delete(final Long number) {
        final DeleteObjectRequest request;
        final Image               image;

        image = getOne(number);

        request = DeleteObjectRequest.builder()
            .bucket(bucket)
            .key(image.key())
            .build();
        repository.delete(number);
        client.deleteObject(request);

        return image;
    }

    @Override
    public final Page<Image> getAll(final Pagination pagination, final Sorting sorting) {
        return repository.findAll(pagination, sorting);
    }

    @Override
    public final ImageContent getContent(final Long number) {
        final GetObjectRequest                 request;
        final ResponseBytes<GetObjectResponse> response;
        final String                           mediaType;
        final Image                            image;

        image = repository.findOne(number)
            .orElseThrow(() -> {
                log.error("Image {} doesn't exist", number);
                return new ImageNotExistingException(number);
            });
        request = GetObjectRequest.builder()
            .bucket(bucket)
            .key(image.key())
            .build();
        try {
            response = client.getObjectAsBytes(request);
        } catch (final NoSuchKeyException ex) {
            log.error("Image {} doesn't exist", number);
            throw new ImageNotExistingException(number);
        }

        mediaType = response.response()
            .contentType();
        return new ImageContent(response.asByteArray(),
            mediaType != null ? mediaType : MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    @Override
    public final Image getOne(final Long number) {
        return repository.findOne(number)
            .orElseThrow(() -> {
                log.error("Image {} doesn't exist", number);
                return new ImageNotExistingException(number);
            });
    }

    @Override
    public final Image update(final Image image, final ImageContent content) {
        final Image existing;
        final Image updated;

        existing = repository.findOne(image.number())
            .orElseThrow(() -> {
                log.error("Image {} doesn't exist", image.number());
                return new ImageNotExistingException(image.number());
            });
        if (repository.existsByNameForAnother(image.name(), image.number())) {
            log.error("Image {} already exists", image.name());
            throw new ImageAlreadyExistsException(image.name());
        }
        updated = repository.save(new Image(image.number(), image.name(), image.description(), existing.key(),
            content.mediaType(), content.data().length, existing.audit()));
        storeImage(updated.key(), content);

        return updated;
    }

    private final void storeImage(final String name, final ImageContent content) {
        final PutObjectRequest request;

        request = PutObjectRequest.builder()
            .bucket(bucket)
            .key(name)
            .contentType(content.mediaType())
            .build();
        client.putObject(request, RequestBody.fromBytes(content.data()));
    }

}
