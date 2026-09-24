
package com.bernardomg.image.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.image.adapter.inbound.jpa.model.ImageEntity;
import com.bernardomg.image.adapter.inbound.jpa.model.ImageEntityMapper;
import com.bernardomg.image.adapter.inbound.jpa.model.ImageFolderEntity;
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
    private static final Logger               log = LoggerFactory.getLogger(JpaImageRepository.class);

    private final ImageFolderSpringRepository folderRepository;

    private final ImageSpringRepository       repository;

    public JpaImageRepository(final ImageSpringRepository imageRepository,
            final ImageFolderSpringRepository imageFolderRepository) {
        repository = Objects.requireNonNull(imageRepository);
        folderRepository = Objects.requireNonNull(imageFolderRepository);
    }

    @Override
    public final void delete(final Long number) {
        log.debug("Deleting image {}", number);

        repository.deleteByNumber(number);

        log.debug("Deleted image {}", number);
    }

    @Override
    public final boolean exists(final Long number) {
        final boolean exists;

        log.debug("Checking if image {} exists", number);

        exists = repository.existsByNumber(number);

        log.debug("Image {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameAndFolder(final String name, final Long folderNumber) {
        final boolean exists;

        log.debug("Checking if image {} exists in folder {}", name, folderNumber);

        if (folderNumber == null) {
            exists = repository.existsByNameAndFolderIsNull(name);
        } else {
            exists = repository.existsByNameAndFolderNumber(name, folderNumber);
        }

        log.debug("Image {} exists in folder {}: {}", name, folderNumber, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameAndFolder(final String name, final Long folderNumber, final long excludedNumber) {
        final boolean exists;

        log.debug("Checking if image {} exists in folder {}, excluding {}", name, folderNumber, excludedNumber);

        if (folderNumber == null) {
            exists = repository.existsByNameAndNumberNotAndFolderIsNull(name, excludedNumber);
        } else {
            exists = repository.existsByNameAndFolderNumberAndNumberNot(name, folderNumber, excludedNumber);
        }

        log.debug("Image {} exists in folder {}: {}", name, folderNumber, exists);

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
    public final Page<Image> findAllByFolder(final Long folderNumber, final Pagination pagination,
            final Sorting sorting) {
        final Pageable                                    pageable = SpringPagination.toPageable(pagination, sorting);
        final org.springframework.data.domain.Page<Image> read;
        if (folderNumber == null) {
            read = repository.findAllByFolderIsNull(pageable)
                .map(ImageEntityMapper::toDomain);
        } else {
            read = repository.findAllByFolderNumber(folderNumber, pageable)
                .map(ImageEntityMapper::toDomain);
        }
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
    public final boolean hasImagesInFolder(final Long folderNumber) {
        return repository.existsByFolderNumber(folderNumber);
    }

    @Override
    public final Image move(final Long number, final Long folderNumber) {
        final ImageEntity entity = repository.findByNumber(number)
            .orElseThrow();
        entity.setFolder(folderNumber == null ? null
                : folderRepository.findByNumber(folderNumber)
                    .orElseThrow());
        return ImageEntityMapper.toDomain(repository.save(entity));
    }

    @Override
    public final Image save(final Image image) {
        final Optional<ImageEntity> existing;
        final ImageEntity           entity;
        final Long                  number;
        final Image                 toCreate;
        final Image                 saved;
        final ImageFolderEntity     folder;

        log.debug("Saving image {}", image);

        existing = repository.findByNumber(image.number());
        if (existing.isPresent()) {
            entity = ImageEntityMapper.toEntity(image);
            entity.setId(existing.get()
                .getId());
        } else {
            number = repository.findNextNumber();
            toCreate = new Image(number, image.name(), image.description(), image.key(), image.mediaType(),
                image.size(), image.folderNumber(), image.audit());
            entity = ImageEntityMapper.toEntity(toCreate);
        }

        if (image.folderNumber()
            .isEmpty()) {
            entity.setFolder(null);
        } else {
            folder = folderRepository.findByNumber(image.folderNumber()
                .get())
                .orElseThrow();
            entity.setFolder(folder);
        }

        saved = ImageEntityMapper.toDomain(repository.save(entity));

        log.debug("Saved image {}", saved);

        return saved;
    }
}
