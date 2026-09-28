/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.usecase.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.image.domain.exception.ImageAlreadyExistsException;
import com.bernardomg.image.domain.exception.ImageFolderAlreadyExistsException;
import com.bernardomg.image.domain.exception.ImageFolderCantBeMovedException;
import com.bernardomg.image.domain.exception.ImageFolderNotEmptyException;
import com.bernardomg.image.domain.exception.ImageFolderNotExistingException;
import com.bernardomg.image.domain.exception.ImageNotExistingException;
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

    private final AssetFolderRepository folderRepository;

    private final AssetRepository       imageRepository;

    public DefaultImageFolderService(final AssetFolderRepository folderRepo, final AssetRepository imageRepo) {
        super();

        folderRepository = Objects.requireNonNull(folderRepo);
        imageRepository = Objects.requireNonNull(imageRepo);
    }

    @Override
    public final AssetFolder create(final AssetFolder folder) {
        final AssetFolder created;

        log.debug("Creating image folder {}", folder);

        validateParent(null, folder.parentNumber()
            .orElse(null));

        if (folderRepository.existsByNameAndParent(folder.name(), folder.parentNumber()
            .orElse(null), null)) {
            log.error("Asset folder with name {} already exists below parent {}", folder.name(), folder.parentNumber()
                .orElse(null));
            throw new ImageFolderAlreadyExistsException(folder.name());
        }

        created = folderRepository.save(new AssetFolder(-1L, folder.name(), folder.parentNumber()));

        log.debug("Created image folder {}", created);

        return created;
    }

    @Override
    public final AssetFolder delete(final Long number) {
        final AssetFolder deleted;

        log.debug("Deleting image folder {}", number);

        deleted = folderRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing image folder {}", number);
                return new ImageFolderNotExistingException(number);
            });

        if (folderRepository.hasChildren(number) || imageRepository.hasAssetsInFolder(number)) {
            log.error("Asset folder {} is not empty", number);
            throw new ImageFolderNotEmptyException(number);
        }

        folderRepository.delete(number);

        log.debug("Deleted image folder {}", deleted);

        return deleted;
    }

    @Override
    public final Collection<AssetFolder> getAll() {
        final Collection<AssetFolder> folders;

        log.debug("Reading all image folders");

        folders = folderRepository.findAll();

        log.debug("Read {} image folders", folders.size());

        return folders;
    }

    @Override
    public final Page<Asset> getImages(final Long folderNumber, final Pagination pagination, final Sorting sorting) {
        final Page<Asset> images;

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
    public final AssetFolder getOne(final Long number) {
        final AssetFolder folder;

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
    public final Page<Asset> getPublicImages(final Long folderNumber, final Pagination pagination,
            final Sorting sorting) {
        if (!folderRepository.exists(folderNumber)) {
            throw new ImageFolderNotExistingException(folderNumber);
        }
        return imageRepository.findAllPublicByFolder(folderNumber, pagination, sorting);
    }

    @Override
    public final Page<Asset> getPublicRootImages(final Pagination pagination, final Sorting sorting) {
        return imageRepository.findAllPublicByFolder(null, pagination, sorting);
    }

    @Override
    public final Page<Asset> getRootImages(final Pagination pagination, final Sorting sorting) {
        final Page<Asset> images;

        log.debug("Reading root images with pagination {} and sorting {}", pagination, sorting);

        images = imageRepository.findAllByFolder(null, pagination, sorting);

        log.debug("Read root images with pagination {} and sorting {}", pagination, sorting);

        return images;
    }

    @Override
    public final Asset moveImage(final Long imageNumber, final Long folderNumber) {
        final Asset image;
        final Asset moved;

        log.debug("Moving image {} to folder {}", imageNumber, folderNumber);

        if (!folderRepository.exists(folderNumber)) {
            log.error("Missing image folder {}", folderNumber);
            throw new ImageFolderNotExistingException(folderNumber);
        }

        image = imageRepository.findOne(imageNumber)
            .orElseThrow(() -> {
                log.error("Missing image {}", imageNumber);
                return new ImageNotExistingException(imageNumber);
            });
        validateImageName(image, folderNumber);

        moved = imageRepository.move(imageNumber, folderNumber);

        log.debug("Moved image {} to folder {}", imageNumber, folderNumber);

        return moved;
    }

    @Override
    public final Asset moveImageToRoot(final Long imageNumber) {
        final Asset image;
        final Asset moved;

        log.debug("Moving image {} to the root folder", imageNumber);

        image = imageRepository.findOne(imageNumber)
            .orElseThrow(() -> {
                log.error("Missing image {}", imageNumber);
                return new ImageNotExistingException(imageNumber);
            });
        validateImageName(image, null);

        moved = imageRepository.move(imageNumber, null);

        log.debug("Moved image {} to the root folder", imageNumber);

        return moved;
    }

    @Override
    public final AssetFolder update(final AssetFolder folder) {
        final AssetFolder existing;
        final AssetFolder updated;

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
            log.error("Asset folder with name {} already exists below parent {}", folder.name(), folder.parentNumber()
                .orElse(null));
            throw new ImageFolderAlreadyExistsException(folder.name());
        }

        updated = folderRepository
            .save(new AssetFolder(folder.number(), folder.name(), folder.parentNumber(), existing.audit()));

        log.debug("Updated image folder {}", updated);

        return updated;
    }

    private final void validateImageName(final Asset image, final Long folderNumber) {
        if (imageRepository.existsByNameAndFolder(image.name(), folderNumber, image.number())) {
            log.error("Asset {} already exists in folder {}", image.name(), folderNumber);
            throw new ImageAlreadyExistsException(image.name());
        }
    }

    private final void validateParent(final Long folderNumber, final Long parentNumber) {
        final Set<Long> visited;
        Long            current;

        visited = new HashSet<>();
        current = parentNumber;

        while (current != null) {
            if (Objects.equals(current, folderNumber) || !visited.add(current)) {
                log.error("Asset folder {} can't be moved below folder {}", folderNumber, parentNumber);
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
