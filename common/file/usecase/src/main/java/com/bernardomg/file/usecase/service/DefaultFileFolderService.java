/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.file.usecase.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.file.domain.exception.FileAlreadyExistsException;
import com.bernardomg.file.domain.exception.FileFolderAlreadyExistsException;
import com.bernardomg.file.domain.exception.FileFolderCantBeMovedException;
import com.bernardomg.file.domain.exception.FileFolderNotEmptyException;
import com.bernardomg.file.domain.exception.FileFolderNotExistingException;
import com.bernardomg.file.domain.exception.FileNotExistingException;
import com.bernardomg.file.domain.model.File;
import com.bernardomg.file.domain.model.FileFolder;
import com.bernardomg.file.domain.repository.FileFolderRepository;
import com.bernardomg.file.domain.repository.FileRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

import jakarta.transaction.Transactional;

@Transactional
public final class DefaultFileFolderService implements FileFolderService {

    /**
     * Logger for the class.
     */
    private static final Logger        log = LoggerFactory.getLogger(DefaultFileFolderService.class);

    private final FileRepository       fileRepository;

    private final FileFolderRepository folderRepository;

    public DefaultFileFolderService(final FileFolderRepository folderRepo, final FileRepository fileRepo) {
        super();

        folderRepository = Objects.requireNonNull(folderRepo);
        fileRepository = Objects.requireNonNull(fileRepo);
    }

    @Override
    public final FileFolder create(final FileFolder folder) {
        final FileFolder created;

        log.debug("Creating file folder {}", folder);

        validateParent(null, folder.parentNumber()
            .orElse(null));

        if (folderRepository.existsByNameAndParent(folder.name(), folder.parentNumber()
            .orElse(null), null)) {
            log.error("File folder with name {} already exists below parent {}", folder.name(), folder.parentNumber()
                .orElse(null));
            throw new FileFolderAlreadyExistsException(folder.name());
        }

        created = folderRepository.save(new FileFolder(-1L, folder.name(), folder.parentNumber()));

        log.debug("Created file folder {}", created);

        return created;
    }

    @Override
    public final FileFolder delete(final Long number) {
        final FileFolder deleted;

        log.debug("Deleting file folder {}", number);

        deleted = folderRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing file folder {}", number);
                return new FileFolderNotExistingException(number);
            });

        if (folderRepository.hasChildren(number) || fileRepository.hasFilesInFolder(number)) {
            log.error("File folder {} is not empty", number);
            throw new FileFolderNotEmptyException(number);
        }

        folderRepository.delete(number);

        log.debug("Deleted file folder {}", deleted);

        return deleted;
    }

    @Override
    public final Collection<FileFolder> getAll() {
        final Collection<FileFolder> folders;

        log.debug("Reading all file folders");

        folders = folderRepository.findAll();

        log.debug("Read {} file folders", folders.size());

        return folders;
    }

    @Override
    public final Page<File> getFiles(final Long folderNumber, final Pagination pagination, final Sorting sorting) {
        final Page<File> files;

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
    public final FileFolder getOne(final Long number) {
        final FileFolder folder;

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
    public final Page<File> getPublicFiles(final Long folderNumber, final Pagination pagination,
            final Sorting sorting) {
        if (!folderRepository.exists(folderNumber)) {
            throw new FileFolderNotExistingException(folderNumber);
        }
        return fileRepository.findAllPublicByFolder(folderNumber, pagination, sorting);
    }

    @Override
    public final Page<File> getPublicRootFiles(final Pagination pagination, final Sorting sorting) {
        return fileRepository.findAllPublicByFolder(null, pagination, sorting);
    }

    @Override
    public final Page<File> getRootFiles(final Pagination pagination, final Sorting sorting) {
        final Page<File> files;

        log.debug("Reading root files with pagination {} and sorting {}", pagination, sorting);

        files = fileRepository.findAllByFolder(null, pagination, sorting);

        log.debug("Read root files with pagination {} and sorting {}", pagination, sorting);

        return files;
    }

    @Override
    public final File moveFile(final Long fileNumber, final Long folderNumber) {
        final File file;
        final File moved;

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
    public final File moveFileToRoot(final Long fileNumber) {
        final File file;
        final File moved;

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
    public final FileFolder update(final FileFolder folder) {
        final FileFolder existing;
        final FileFolder updated;

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
            log.error("File folder with name {} already exists below parent {}", folder.name(), folder.parentNumber()
                .orElse(null));
            throw new FileFolderAlreadyExistsException(folder.name());
        }

        updated = folderRepository
            .save(new FileFolder(folder.number(), folder.name(), folder.parentNumber(), existing.audit()));

        log.debug("Updated file folder {}", updated);

        return updated;
    }

    private final void validateFileName(final File file, final Long folderNumber) {
        if (fileRepository.existsByNameAndFolder(file.name(), folderNumber, file.number())) {
            log.error("File {} already exists in folder {}", file.name(), folderNumber);
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
                log.error("File folder {} can't be moved below folder {}", folderNumber, parentNumber);
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
