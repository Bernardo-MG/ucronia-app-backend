/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.image.adapter.inbound.jpa.model.ImageEntity;
import com.bernardomg.image.adapter.inbound.jpa.model.ImageEntityMapper;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringPagination;

@Transactional
public final class JpaImageRepository implements ImageRepository {

    /**
     * Logger for the class.
     */
    private static final Logger         log = LoggerFactory.getLogger(JpaImageRepository.class);

    private final ImageSpringRepository repository;

    public JpaImageRepository(final ImageSpringRepository imageRepository) {
        repository = Objects.requireNonNull(imageRepository);
    }

    @Override
    public final void delete(final Long number) {
        log.debug("Deleting image {}", number);

        repository.deleteByNumber(number);

        log.debug("Deleted image {}", number);
    }

    @Override
    public final boolean existsByName(final String name) {
        final boolean exists;

        log.debug("Checking if image {} exists", name);

        exists = repository.existsByName(name);

        log.debug("Image {} exists: {}", name, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameForAnother(final String name, final Long number) {
        final boolean exists;

        log.debug("Checking if image {} exists for another distinct from {}", name, number);

        exists = repository.existsByNotNumberAndName(number, name);

        log.debug("Image {} exists: {}", name, exists);

        return exists;
    }

    @Override
    public final Page<Image> findAll(final Pagination pagination, final Sorting sorting) {
        final Pageable                                    pageable;
        final org.springframework.data.domain.Page<Image> read;

        log.debug("Finding images with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = repository.findAll(pageable)
            .map(ImageEntityMapper::toDomain);

        log.debug("Found images {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<Image> findOne(final Long number) {
        final Optional<Image> image;

        log.debug("Finding author with number {}", number);

        image = repository.findByNumber(number)
            .map(ImageEntityMapper::toDomain);

        log.debug("Found image with number {}: {}", number, image);

        return image;
    }

    @Override
    public final Image save(final Image image) {
        final Optional<ImageEntity> existing;
        final ImageEntity           entity;
        final Long                  number;
        final Image                 toCreate;
        final Image                 saved;

        log.debug("Saving image {}", image);

        existing = repository.findByNumber(image.number());
        if (existing.isPresent()) {
            entity = ImageEntityMapper.toEntity(image);
            entity.setId(existing.get()
                .getId());
        } else {
            number = repository.findNextNumber();
            toCreate = new Image(number, image.name(), image.description(), "images/" + number, image.mediaType(),
                image.size());
            entity = ImageEntityMapper.toEntity(toCreate);
        }

        saved = ImageEntityMapper.toDomain(repository.save(entity));

        log.debug("Saved image {}", saved);

        return saved;
    }
}
