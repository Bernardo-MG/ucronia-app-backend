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

import com.bernardomg.content.domain.key.ContentKeyGenerator;
import com.bernardomg.content.domain.model.Content;
import com.bernardomg.content.domain.policy.ContentPolicy;
import com.bernardomg.content.domain.repository.ContentRepository;
import com.bernardomg.image.domain.exception.ImageAlreadyExistsException;
import com.bernardomg.image.domain.exception.ImageNotExistingException;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

import jakarta.transaction.Transactional;

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
    private static final Logger       log       = LoggerFactory.getLogger(DefaultImageService.class);

    private final ContentKeyGenerator contentKeyGenerator;

    private final ContentRepository   contentRepository;

    private final ContentPolicy       imageContentPolicy;

    private final ImageRepository     imageRepository;

    private final String              namespace = "images";

    public DefaultImageService(final ImageRepository imageRepo, final ContentRepository contentRepo,
            final ContentPolicy contentPolicy, final ContentKeyGenerator keyGenerator) {
        super();

        imageRepository = Objects.requireNonNull(imageRepo);
        contentRepository = Objects.requireNonNull(contentRepo);
        imageContentPolicy = Objects.requireNonNull(contentPolicy);
        contentKeyGenerator = Objects.requireNonNull(keyGenerator);
    }

    @Override
    public final Image create(final Image image, final Content content) {
        final Image toCreate;
        final Image created;

        log.debug("Creating image {}", image);

        if (imageRepository.existsByName(image.name())) {
            log.error("Image {} already exists", image.name());
            throw new ImageAlreadyExistsException(image.name());
        }

        imageContentPolicy.validate(content.size(), content.mediaType());

        toCreate = new Image(image.number(), image.name(), image.description(), contentKeyGenerator.generate(namespace),
            content.mediaType(), content.size(), image.folderNumber());
        contentRepository.save(toCreate.key(), content);
        try {
            created = imageRepository.save(toCreate);
        } catch (final RuntimeException ex) {
            deleteContent(toCreate.key());
            throw ex;
        }

        log.debug("Created image {}", created);

        return created;
    }

    @Override
    public final Image delete(final Long number) {
        final Image deleted;

        log.debug("Deleting image {}", number);

        deleted = getOne(number);

        try {
            imageRepository.delete(number);
        } catch (final RuntimeException ex) {
            deleteContent(deleted.key());
            throw ex;
        }
        deleteContent(deleted.key());

        log.debug("Deleted image {}", deleted);

        return deleted;
    }

    @Override
    public final Page<Image> getAll(final Pagination pagination, final Sorting sorting) {
        final Page<Image> page;

        log.debug("Reading all images with pagination {} and sorting {}", pagination, sorting);

        page = imageRepository.findAll(pagination, sorting);

        log.debug("Read all images with pagination {} and sorting {}: {}", pagination, sorting, page);

        return page;
    }

    @Override
    public final Content getContent(final Long number) {
        final Image   image;
        final Content imageContent;

        log.debug("Reading image content for {}", number);

        image = imageRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Image {} doesn't exist", number);
                return new ImageNotExistingException(number);
            });

        imageContent = contentRepository.getOne(image.key());

        log.debug("Read image content for {}", number);

        return imageContent;
    }

    @Override
    public final Image getOne(final Long number) {
        final Image image;

        log.debug("Reading image {}", number);

        image = imageRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Image {} doesn't exist", number);
                return new ImageNotExistingException(number);
            });

        log.debug("Read image {}", image);

        return image;
    }

    @Override
    public final Image update(final Image image, final Content content) {
        final Image  existing;
        final String key;
        final Image  updated;

        log.debug("Updating image {}", image);

        existing = imageRepository.findOne(image.number())
            .orElseThrow(() -> {
                log.error("Image {} doesn't exist", image.number());
                return new ImageNotExistingException(image.number());
            });
        if (imageRepository.existsByNameForAnother(image.name(), image.number())) {
            log.error("Image {} already exists", image.name());
            throw new ImageAlreadyExistsException(image.name());
        }

        imageContentPolicy.validate(content.size(), content.mediaType());

        key = contentKeyGenerator.generate(namespace);
        contentRepository.save(key, content);
        try {
            updated = imageRepository.save(new Image(image.number(), image.name(), image.description(), key,
                content.mediaType(), content.size(), existing.folderNumber(), existing.audit()));
        } catch (final RuntimeException ex) {
            deleteContent(key);
            throw ex;
        }
        deleteContent(existing.key());

        log.debug("Updated image {}", updated);

        return updated;
    }

    @Override
    public final Image updateMetadata(final Image image) {
        final Image existing;
        final Image updated;

        log.debug("Updating metadata for image {}", image);

        existing = imageRepository.findOne(image.number())
            .orElseThrow(() -> {
                log.error("Image {} doesn't exist", image.number());
                return new ImageNotExistingException(image.number());
            });
        if (imageRepository.existsByNameForAnother(image.name(), image.number())) {
            log.error("Image {} already exists", image.name());
            throw new ImageAlreadyExistsException(image.name());
        }
        updated = imageRepository.save(new Image(existing.number(), image.name(), image.description(), existing.key(),
            existing.mediaType(), existing.size(), existing.folderNumber(), existing.audit()));

        log.debug("Updated metadata for image {}", updated);

        return updated;
    }

    private final void deleteContent(final String key) {
        try {
            contentRepository.delete(key);
        } catch (final RuntimeException ex) {
            log.warn("Failed to delete content {}", key, ex);
        }
    }

}
