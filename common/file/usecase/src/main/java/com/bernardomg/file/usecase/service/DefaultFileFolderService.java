/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.file.usecase.service;

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
import com.bernardomg.file.domain.exception.FileAlreadyExistsException;
import com.bernardomg.file.domain.exception.FileFolderAlreadyExistsException;
import com.bernardomg.file.domain.exception.FileFolderCantBeMovedException;
import com.bernardomg.file.domain.exception.FileFolderNotEmptyException;
import com.bernardomg.file.domain.exception.FileFolderNotExistingException;
import com.bernardomg.file.domain.exception.FileNotExistingException;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

import jakarta.transaction.Transactional;

@Transactional
public final class DefaultFileFolderService implements FileFolderService {

    /**
     * Logger for the class.
     */
    private static final Logger         log = LoggerFactory.getLogger(DefaultFileFolderService.class);

    private final AssetRepository       fileRepository;

    private final AssetFolderRepository folderRepository;

    public DefaultFileFolderService(final AssetFolderRepository folderRepo, final AssetRepository fileRepo) {
        super();

        folderRepository = Objects.requireNonNull(folderRepo);
        fileRepository = Objects.requireNonNull(fileRepo);
    }

    @Override
    public final AssetFolder create(final AssetFolder folder) {
        final AssetFolder created;

        log.debug("Creating file folder {}", folder);

        validateParent(null, folder.parentNumber()
            .orElse(null));

        if (folderRepository.existsByNameAndParent(folder.name(), folder.parentNumber()
            .orElse(null), null)) {
            log.error("Asset folder with name {} already exists below parent {}", folder.name(), folder.parentNumber()
                .orElse(null));
            throw new FileFolderAlreadyExistsException(folder.name());
        }

        created = folderRepository.save(new AssetFolder(-1L, folder.name(), folder.parentNumber()));

        log.debug("Created file folder {}", created);

        return created;
    }

    @Override
    public final AssetFolder delete(final Long number) {
        final AssetFolder deleted;

        log.debug("Deleting file folder {}", number);

        deleted = folderRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing file folder {}", number);
                return new FileFolderNotExistingException(number);
            });

        if (folderRepository.hasChildren(number) || fileRepository.hasAssetsInFolder(number)) {
            log.error("Asset folder {} is not empty", number);
            throw new FileFolderNotEmptyException(number);
        }

        folderRepository.delete(number);

        log.debug("Deleted file folder {}", deleted);

        return deleted;
    }

    @Override
    public final Collection<AssetFolder> getAll() {
        final Collection<AssetFolder> folders;

        log.debug("Reading all file folders");

        folders = folderRepository.findAll();

        log.debug("Read {} file folders", folders.size());

        return folders;
    }

    @Override
    public final Page<Asset> getFiles(final Long folderNumber, final Pagination pagination, final Sorting sorting) {
        final Page<Asset> files;

        log.debug("Reading files in folder {} with pagination {} and sorting {}", folderNumber, pagination, sorting);

        if (!folderRepository.exists(folderNumber)) {
            log.error("Missing file folder {}", folderNumber);
            throw new FileFolderNotExistingException(folderNumber);
        }

        files = fileRepository.findAllByFolder(folderNumber, pagination, sorting);

        log.debug("Read files in folder {} with pagination {} and sorting {}", folderNumber, pagination, sorting);

        return files;
    }

    @Override
    public final AssetFolder getOne(final Long number) {
        final AssetFolder folder;

        log.debug("Reading file folder {}", number);

        folder = folderRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing file folder {}", number);
                return new FileFolderNotExistingException(number);
            });

        log.debug("Read file folder {}", folder);

        return folder;
    }

    @Override
    public final Page<Asset> getPublicFiles(final Long folderNumber, final Pagination pagination,
            final Sorting sorting) {
        if (!folderRepository.exists(folderNumber)) {
            throw new FileFolderNotExistingException(folderNumber);
        }
        return fileRepository.findAllPublicByFolder(folderNumber, pagination, sorting);
    }

    @Override
    public final Page<Asset> getPublicRootFiles(final Pagination pagination, final Sorting sorting) {
        return fileRepository.findAllPublicByFolder(null, pagination, sorting);
    }

    @Override
    public final Page<Asset> getRootFiles(final Pagination pagination, final Sorting sorting) {
        final Page<Asset> files;

        log.debug("Reading root files with pagination {} and sorting {}", pagination, sorting);

        files = fileRepository.findAllByFolder(null, pagination, sorting);

        log.debug("Read root files with pagination {} and sorting {}", pagination, sorting);

        return files;
    }

    @Override
    public final Asset moveFile(final Long fileNumber, final Long folderNumber) {
        final Asset file;
        final Asset moved;

        log.debug("Moving file {} to folder {}", fileNumber, folderNumber);

        if (!folderRepository.exists(folderNumber)) {
            log.error("Missing file folder {}", folderNumber);
            throw new FileFolderNotExistingException(folderNumber);
        }

        file = fileRepository.findOne(fileNumber)
            .orElseThrow(() -> {
                log.error("Missing file {}", fileNumber);
                return new FileNotExistingException(fileNumber);
            });
        validateFileName(file, folderNumber);

        moved = fileRepository.move(fileNumber, folderNumber);

        log.debug("Moved file {} to folder {}", fileNumber, folderNumber);

        return moved;
    }

    @Override
    public final Asset moveFileToRoot(final Long fileNumber) {
        final Asset file;
        final Asset moved;

        log.debug("Moving file {} to the root folder", fileNumber);

        file = fileRepository.findOne(fileNumber)
            .orElseThrow(() -> {
                log.error("Missing file {}", fileNumber);
                return new FileNotExistingException(fileNumber);
            });
        validateFileName(file, null);

        moved = fileRepository.move(fileNumber, null);

        log.debug("Moved file {} to the root folder", fileNumber);

        return moved;
    }

    @Override
    public final AssetFolder update(final AssetFolder folder) {
        final AssetFolder existing;
        final AssetFolder updated;

        log.debug("Updating file folder {}", folder);

        if (!folderRepository.exists(folder.number())) {
            log.error("Missing file folder {}", folder.number());
            throw new FileFolderNotExistingException(folder.number());
        }

        existing = folderRepository.findOne(folder.number())
            .orElseThrow(() -> {
                log.error("Missing file folder {}", folder.number());
                return new FileFolderNotExistingException(folder.number());
            });

        validateParent(folder.number(), folder.parentNumber()
            .orElse(null));

        if (folderRepository.existsByNameAndParent(folder.name(), folder.parentNumber()
            .orElse(null), folder.number())) {
            log.error("Asset folder with name {} already exists below parent {}", folder.name(), folder.parentNumber()
                .orElse(null));
            throw new FileFolderAlreadyExistsException(folder.name());
        }

        updated = folderRepository
            .save(new AssetFolder(folder.number(), folder.name(), folder.parentNumber(), existing.audit()));

        log.debug("Updated file folder {}", updated);

        return updated;
    }

    private final void validateFileName(final Asset file, final Long folderNumber) {
        if (fileRepository.existsByNameAndFolder(file.name(), folderNumber, file.number())) {
            log.error("Asset {} already exists in folder {}", file.name(), folderNumber);
            throw new FileAlreadyExistsException(file.name());
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
                throw new FileFolderCantBeMovedException(folderNumber);
            }

            if (!folderRepository.exists(current)) {
                log.error("Missing file folder {}", current);
                throw new FileFolderNotExistingException(current);
            }

            current = folderRepository.findOne(current)
                .orElseThrow()
                .parentNumber()
                .orElse(null);
        }
    }

}
