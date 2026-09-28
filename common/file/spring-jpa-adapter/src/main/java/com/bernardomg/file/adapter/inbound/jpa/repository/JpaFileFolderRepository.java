
package com.bernardomg.file.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetFolderEntity;
import com.bernardomg.file.adapter.inbound.jpa.model.FileFolderEntityMapper;
import com.bernardomg.file.domain.model.FileFolder;
import com.bernardomg.file.domain.repository.FileFolderRepository;

@Transactional
public final class JpaFileFolderRepository implements FileFolderRepository {

    /**
     * Logger for the class.
     */
    private static final Logger              log = LoggerFactory.getLogger(JpaFileFolderRepository.class);

    private final FileFolderSpringRepository repository;

    public JpaFileFolderRepository(final FileFolderSpringRepository fileFolderRepository) {
        super();

        repository = Objects.requireNonNull(fileFolderRepository);
    }

    @Override
    public final void delete(final Long number) {
        log.debug("Deleting file folder {}", number);

        repository.deleteByNumber(number);

        log.debug("Deleted file folder {}", number);
    }

    @Override
    public final boolean exists(final Long number) {
        final boolean exists;

        log.debug("Checking if file folder {} exists", number);

        exists = repository.existsByNumber(number);

        log.debug("File folder {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameAndParent(final String name, final Long parent, final Long excluded) {
        final boolean exists;

        log.debug("Checking if file folder {} exists below parent {}, excluding folder {}", name, parent, excluded);

        if ((parent == null) && (excluded == null)) {
            exists = repository.existsByNameAndParentIsNull(name);
        } else if (parent == null) {
            exists = repository.existsByNameAndParentIsNullAndNumberNot(name, excluded);
        } else if (excluded == null) {
            exists = repository.existsByNameAndParentNumber(name, parent);
        } else {
            exists = repository.existsByNameAndParentNumberAndNumberNot(name, parent, excluded);
        }

        log.debug("File folder {} exists below parent {}, excluding folder {}: {}", name, parent, excluded, exists);

        return exists;
    }

    @Override
    public final Collection<FileFolder> findAll() {
        final Collection<FileFolder> folders;

        log.debug("Finding all file folders");

        folders = repository.findAll()
            .stream()
            .map(FileFolderEntityMapper::toDomain)
            .toList();

        log.debug("Found {} file folders", folders.size());

        return folders;
    }

    @Override
    public final Optional<FileFolder> findOne(final Long number) {
        final Optional<FileFolder> folder;

        log.debug("Finding file folder with number {}", number);

        folder = repository.findByNumber(number)
            .map(FileFolderEntityMapper::toDomain);

        log.debug("Found file folder with number {}: {}", number, folder);

        return folder;
    }

    @Override
    public final boolean hasChildren(final Long number) {
        final boolean hasChildren;

        log.debug("Checking if file folder {} has children", number);

        hasChildren = repository.existsByParentNumber(number);

        log.debug("File folder {} has children: {}", number, hasChildren);

        return hasChildren;
    }

    @Override
    public final FileFolder save(final FileFolder folder) {
        final AssetFolderEntity entity;
        final AssetFolderEntity parent;
        final AssetFolderEntity persisted;
        final FileFolder        saved;

        log.debug("Saving file folder {}", folder);

        entity = repository.findByNumber(folder.number())
            .orElseGet(AssetFolderEntity::new);

        if (entity.getNumber() == null) {
            entity.setNumber(repository.findNextNumber());
        }

        entity.setName(folder.name());

        if (folder.parentNumber()
            .isEmpty()) {
            entity.setParent(null);
        } else {
            parent = repository.findByNumber(folder.parentNumber()
                .get())
                .orElseThrow(() -> {
                    log.error("Missing parent file folder {}", folder.parentNumber()
                        .get());
                    return new IllegalArgumentException("File folder doesn't exist: " + folder.parentNumber()
                        .get());
                });
            entity.setParent(parent);
        }

        persisted = repository.save(entity);
        saved = FileFolderEntityMapper.toDomain(persisted);

        log.debug("Saved file folder {}", saved);

        return saved;
    }

}
