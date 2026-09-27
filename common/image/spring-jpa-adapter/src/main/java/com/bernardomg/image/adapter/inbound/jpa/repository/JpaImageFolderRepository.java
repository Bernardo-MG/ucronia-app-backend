
package com.bernardomg.image.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.image.adapter.inbound.jpa.model.ImageFolderEntity;
import com.bernardomg.image.adapter.inbound.jpa.model.ImageFolderEntityMapper;
import com.bernardomg.image.domain.model.ImageFolder;
import com.bernardomg.image.domain.repository.ImageFolderRepository;

@Transactional
public final class JpaImageFolderRepository implements ImageFolderRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaImageFolderRepository.class);

    private final ImageFolderSpringRepository repository;

    public JpaImageFolderRepository(final ImageFolderSpringRepository imageFolderRepository) {
        super();

        repository = Objects.requireNonNull(imageFolderRepository);
    }

    @Override
    public final void delete(final Long number) {
        log.debug("Deleting image folder {}", number);

        repository.deleteByNumber(number);

        log.debug("Deleted image folder {}", number);
    }

    @Override
    public final boolean exists(final Long number) {
        final boolean exists;

        log.debug("Checking if image folder {} exists", number);

        exists = repository.existsByNumber(number);

        log.debug("Image folder {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameAndParent(final String name, final Long parent, final Long excluded) {
        final boolean exists;

        log.debug("Checking if image folder {} exists below parent {}, excluding folder {}", name, parent, excluded);

        if ((parent == null) && (excluded == null)) {
            exists = repository.existsByNameAndParentIsNull(name);
        } else if (parent == null) {
            exists = repository.existsByNameAndParentIsNullAndNumberNot(name, excluded);
        } else if (excluded == null) {
            exists = repository.existsByNameAndParentNumber(name, parent);
        } else {
            exists = repository.existsByNameAndParentNumberAndNumberNot(name, parent, excluded);
        }

        log.debug("Image folder {} exists below parent {}, excluding folder {}: {}", name, parent, excluded, exists);

        return exists;
    }

    @Override
    public final Collection<ImageFolder> findAll() {
        final Collection<ImageFolder> folders;

        log.debug("Finding all image folders");

        folders = repository.findAll()
            .stream()
            .map(ImageFolderEntityMapper::toDomain)
            .toList();

        log.debug("Found {} image folders", folders.size());

        return folders;
    }

    @Override
    public final Optional<ImageFolder> findOne(final Long number) {
        final Optional<ImageFolder> folder;

        log.debug("Finding image folder with number {}", number);

        folder = repository.findByNumber(number)
            .map(ImageFolderEntityMapper::toDomain);

        log.debug("Found image folder with number {}: {}", number, folder);

        return folder;
    }

    @Override
    public final boolean hasChildren(final Long number) {
        final boolean hasChildren;

        log.debug("Checking if image folder {} has children", number);

        hasChildren = repository.existsByParentNumber(number);

        log.debug("Image folder {} has children: {}", number, hasChildren);

        return hasChildren;
    }

    @Override
    public final ImageFolder save(final ImageFolder folder) {
        final ImageFolderEntity entity;
        final ImageFolderEntity parent;
        final ImageFolderEntity persisted;
        final ImageFolder       saved;

        log.debug("Saving image folder {}", folder);

        entity = repository.findByNumber(folder.number())
            .orElseGet(ImageFolderEntity::new);

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
                    log.error("Missing parent image folder {}", folder.parentNumber()
                        .get());
                    return new IllegalArgumentException("Image folder doesn't exist: " + folder.parentNumber()
                        .get());
                });
            entity.setParent(parent);
        }

        persisted = repository.save(entity);
        saved = ImageFolderEntityMapper.toDomain(persisted);

        log.debug("Saved image folder {}", saved);

        return saved;
    }

}
