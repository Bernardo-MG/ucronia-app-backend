/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.usecase.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.image.domain.exception.ImageFolderAlreadyExistsException;
import com.bernardomg.image.domain.exception.ImageFolderCantBeMovedException;
import com.bernardomg.image.domain.exception.ImageFolderNotEmptyException;
import com.bernardomg.image.domain.exception.ImageFolderNotExistingException;
import com.bernardomg.image.domain.exception.ImageNotExistingException;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.model.ImageFolder;
import com.bernardomg.image.domain.repository.ImageFolderRepository;
import com.bernardomg.image.domain.repository.ImageRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

import jakarta.transaction.Transactional;

@Transactional
public final class DefaultImageFolderService implements ImageFolderService {

    /**
     * Logger for the class.
     */
    private static final Logger         log = LoggerFactory.getLogger(DefaultImageFolderService.class);

    private final ImageFolderRepository folderRepository;

    private final ImageRepository       imageRepository;

    public DefaultImageFolderService(final ImageFolderRepository folderRepo, final ImageRepository imageRepo) {
        super();

        folderRepository = Objects.requireNonNull(folderRepo);
        imageRepository = Objects.requireNonNull(imageRepo);
    }

    @Override
    public final ImageFolder create(final ImageFolder folder) {
        final ImageFolder created;

        log.debug("Creating image folder {}", folder);

        validateParent(null, folder.parentNumber()
            .orElse(null));

        if (folderRepository.existsByNameAndParent(folder.name(), folder.parentNumber()
            .orElse(null), null)) {
            log.error("Image folder with name {} already exists below parent {}", folder.name(), folder.parentNumber()
                .orElse(null));
            throw new ImageFolderAlreadyExistsException(folder.name());
        }

        created = folderRepository.save(new ImageFolder(-1L, folder.name(), folder.parentNumber()));

        log.debug("Created image folder {}", created);

        return created;
    }

    @Override
    public final ImageFolder delete(final Long number) {
        final ImageFolder deleted;

        log.debug("Deleting image folder {}", number);

        deleted = folderRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing image folder {}", number);
                return new ImageFolderNotExistingException(number);
            });

        if (folderRepository.hasChildren(number) || imageRepository.hasImagesInFolder(number)) {
            log.error("Image folder {} is not empty", number);
            throw new ImageFolderNotEmptyException(number);
        }

        folderRepository.delete(number);

        log.debug("Deleted image folder {}", deleted);

        return deleted;
    }

    @Override
    public final Collection<ImageFolder> getAll() {
        final Collection<ImageFolder> folders;

        log.debug("Reading all image folders");

        folders = folderRepository.findAll();

        log.debug("Read {} image folders", folders.size());

        return folders;
    }

    @Override
    public final Page<Image> getImages(final Long folderNumber, final Pagination pagination, final Sorting sorting) {
        final Page<Image> images;

        log.debug("Reading images in folder {} with pagination {} and sorting {}", folderNumber, pagination, sorting);

        if (!folderRepository.exists(folderNumber)) {
            log.error("Missing image folder {}", folderNumber);
            throw new ImageFolderNotExistingException(folderNumber);
        }

        images = imageRepository.findAllByFolder(folderNumber, pagination, sorting);

        log.debug("Read images in folder {} with pagination {} and sorting {}", folderNumber, pagination, sorting);

        return images;
    }

    @Override
    public final ImageFolder getOne(final Long number) {
        final ImageFolder folder;

        log.debug("Reading image folder {}", number);

        folder = folderRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing image folder {}", number);
                return new ImageFolderNotExistingException(number);
            });

        log.debug("Read image folder {}", folder);

        return folder;
    }

    @Override
    public final Page<Image> getRootImages(final Pagination pagination, final Sorting sorting) {
        final Page<Image> images;

        log.debug("Reading root images with pagination {} and sorting {}", pagination, sorting);

        images = imageRepository.findAllByFolder(null, pagination, sorting);

        log.debug("Read root images with pagination {} and sorting {}", pagination, sorting);

        return images;
    }

    @Override
    public final Image moveImage(final Long imageNumber, final Long folderNumber) {
        final Image moved;

        log.debug("Moving image {} to folder {}", imageNumber, folderNumber);

        if (!folderRepository.exists(folderNumber)) {
            log.error("Missing image folder {}", folderNumber);
            throw new ImageFolderNotExistingException(folderNumber);
        }

        if (!imageRepository.exists(imageNumber)) {
            log.error("Missing image {}", imageNumber);
            throw new ImageNotExistingException(imageNumber);
        }

        moved = imageRepository.move(imageNumber, folderNumber);

        log.debug("Moved image {} to folder {}", imageNumber, folderNumber);

        return moved;
    }

    @Override
    public final Image moveImageToRoot(final Long imageNumber) {
        final Image moved;

        log.debug("Moving image {} to the root folder", imageNumber);

        if (!imageRepository.exists(imageNumber)) {
            log.error("Missing image {}", imageNumber);
            throw new ImageNotExistingException(imageNumber);
        }

        moved = imageRepository.move(imageNumber, null);

        log.debug("Moved image {} to the root folder", imageNumber);

        return moved;
    }

    @Override
    public final ImageFolder update(final ImageFolder folder) {
        final ImageFolder existing;
        final ImageFolder updated;

        log.debug("Updating image folder {}", folder);

        if (!folderRepository.exists(folder.number())) {
            log.error("Missing image folder {}", folder.number());
            throw new ImageFolderNotExistingException(folder.number());
        }

        existing = folderRepository.findOne(folder.number())
            .orElseThrow(() -> {
                log.error("Missing image folder {}", folder.number());
                return new ImageFolderNotExistingException(folder.number());
            });

        validateParent(folder.number(), folder.parentNumber()
            .orElse(null));

        if (folderRepository.existsByNameAndParent(folder.name(), folder.parentNumber()
            .orElse(null), folder.number())) {
            log.error("Image folder with name {} already exists below parent {}", folder.name(), folder.parentNumber()
                .orElse(null));
            throw new ImageFolderAlreadyExistsException(folder.name());
        }

        updated = folderRepository
            .save(new ImageFolder(folder.number(), folder.name(), folder.parentNumber(), existing.audit()));

        log.debug("Updated image folder {}", updated);

        return updated;
    }

    private final void validateParent(final Long folderNumber, final Long parentNumber) {
        final Set<Long> visited;
        Long            current;

        visited = new HashSet<>();
        current = parentNumber;

        while (current != null) {
            if (Objects.equals(current, folderNumber) || !visited.add(current)) {
                log.error("Image folder {} can't be moved below folder {}", folderNumber, parentNumber);
                throw new ImageFolderCantBeMovedException(folderNumber);
            }

            if (!folderRepository.exists(current)) {
                log.error("Missing image folder {}", current);
                throw new ImageFolderNotExistingException(current);
            }

            current = folderRepository.findOne(current)
                .orElseThrow()
                .parentNumber()
                .orElse(null);
        }
    }

}
