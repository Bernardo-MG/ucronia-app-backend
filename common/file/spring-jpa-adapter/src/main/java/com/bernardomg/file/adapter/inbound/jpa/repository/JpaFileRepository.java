
package com.bernardomg.file.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.file.adapter.inbound.jpa.model.FileEntity;
import com.bernardomg.file.adapter.inbound.jpa.model.FileEntityMapper;
import com.bernardomg.file.adapter.inbound.jpa.model.FileFolderEntity;
import com.bernardomg.file.domain.model.File;
import com.bernardomg.file.domain.repository.FileRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringPagination;

@Transactional
public final class JpaFileRepository implements FileRepository {

    /**
     * Logger for the class.
     */
    private static final Logger              log = LoggerFactory.getLogger(JpaFileRepository.class);

    private final FileFolderSpringRepository folderRepository;

    private final FileSpringRepository       repository;

    public JpaFileRepository(final FileSpringRepository fileRepository,
            final FileFolderSpringRepository fileFolderRepository) {
        repository = Objects.requireNonNull(fileRepository);
        folderRepository = Objects.requireNonNull(fileFolderRepository);
    }

    @Override
    public final void delete(final Long number) {
        log.debug("Deleting file {}", number);

        repository.deleteByNumber(number);

        log.debug("Deleted file {}", number);
    }

    @Override
    public final boolean exists(final Long number) {
        final boolean exists;

        log.debug("Checking if file {} exists", number);

        exists = repository.existsByNumber(number);

        log.debug("File {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameAndFolder(final String name, final Long folderNumber) {
        final boolean exists;

        log.debug("Checking if file {} exists in folder {}", name, folderNumber);

        if (folderNumber == null) {
            exists = repository.existsByNameAndFolderIsNull(name);
        } else {
            exists = repository.existsByNameAndFolderNumber(name, folderNumber);
        }

        log.debug("File {} exists in folder {}: {}", name, folderNumber, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameAndFolder(final String name, final Long folderNumber, final long excludedNumber) {
        final boolean exists;

        log.debug("Checking if file {} exists in folder {}, excluding {}", name, folderNumber, excludedNumber);

        if (folderNumber == null) {
            exists = repository.existsByNameAndNumberNotAndFolderIsNull(name, excludedNumber);
        } else {
            exists = repository.existsByNameAndFolderNumberAndNumberNot(name, folderNumber, excludedNumber);
        }

        log.debug("File {} exists in folder {}: {}", name, folderNumber, exists);

        return exists;
    }

    @Override
    public final Page<File> findAll(final Pagination pagination, final Sorting sorting) {
        final Pageable                                   pageable;
        final org.springframework.data.domain.Page<File> read;

        log.debug("Finding files with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = repository.findAll(pageable)
            .map(FileEntityMapper::toDomain);

        log.debug("Found files {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Page<File> findAllByFolder(final Long folderNumber, final Pagination pagination,
            final Sorting sorting) {
        final Pageable                                   pageable;
        final org.springframework.data.domain.Page<File> read;

        pageable = SpringPagination.toPageable(pagination, sorting);
        if (folderNumber == null) {
            read = repository.findAllByFolderIsNull(pageable)
                .map(FileEntityMapper::toDomain);
        } else {
            read = repository.findAllByFolderNumber(folderNumber, pageable)
                .map(FileEntityMapper::toDomain);
        }
        return SpringPagination.toPage(read);
    }

    @Override
    public final Page<File> findAllPublic(final Pagination pagination, final Sorting sorting) {
        final Pageable pageable;

        pageable = SpringPagination.toPageable(pagination, sorting);
        return SpringPagination.toPage(repository.findAllByPublicAccessTrue(pageable)
            .map(FileEntityMapper::toDomain));
    }

    @Override
    public final Page<File> findAllPublicByFolder(final Long folderNumber, final Pagination pagination,
            final Sorting sorting) {
        final Pageable                                   pageable;
        final org.springframework.data.domain.Page<File> read;

        pageable = SpringPagination.toPageable(pagination, sorting);
        if (folderNumber == null) {
            read = repository.findAllByFolderIsNullAndPublicAccessTrue(pageable)
                .map(FileEntityMapper::toDomain);
        } else {
            read = repository.findAllByFolderNumberAndPublicAccessTrue(folderNumber, pageable)
                .map(FileEntityMapper::toDomain);
        }

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<File> findOne(final Long number) {
        final Optional<File> file;

        log.debug("Finding author with number {}", number);

        file = repository.findByNumber(number)
            .map(FileEntityMapper::toDomain);

        log.debug("Found file with number {}: {}", number, file);

        return file;
    }

    @Override
    public final boolean hasFilesInFolder(final Long folderNumber) {
        return repository.existsByFolderNumber(folderNumber);
    }

    @Override
    public final File move(final Long number, final Long folderNumber) {
        final FileEntity       entity;
        final FileFolderEntity folderEntity;

        entity = repository.findByNumber(number)
            .orElseThrow();
        if (folderNumber == null) {
            folderEntity = null;
        } else {
            folderEntity = folderRepository.findByNumber(folderNumber)
                .orElseThrow();
        }
        entity.setFolder(folderEntity);
        return FileEntityMapper.toDomain(repository.save(entity));
    }

    @Override
    public final File save(final File file) {
        final Optional<FileEntity> existing;
        final FileEntity           entity;
        final Long                 number;
        final File                 toCreate;
        final File                 saved;
        final FileFolderEntity     folder;

        log.debug("Saving file {}", file);

        existing = repository.findByNumber(file.number());
        if (existing.isPresent()) {
            entity = FileEntityMapper.toEntity(file);
            entity.setId(existing.get()
                .getId());
        } else {
            number = repository.findNextNumber();
            toCreate = new File(number, file.name(), file.description(), file.key(), file.mediaType(), file.size(),
                file.publicAccess(), file.folderNumber(), file.audit());
            entity = FileEntityMapper.toEntity(toCreate);
        }

        if (file.folderNumber()
            .isEmpty()) {
            entity.setFolder(null);
        } else {
            folder = folderRepository.findByNumber(file.folderNumber()
                .get())
                .orElseThrow();
            entity.setFolder(folder);
        }

        saved = FileEntityMapper.toDomain(repository.save(entity));

        log.debug("Saved file {}", saved);

        return saved;
    }
}
