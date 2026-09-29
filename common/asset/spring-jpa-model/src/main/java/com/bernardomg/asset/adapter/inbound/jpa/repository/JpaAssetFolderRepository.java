
package com.bernardomg.asset.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetFolderEntity;
import com.bernardomg.asset.adapter.inbound.jpa.model.AssetFolderEntityMapper;
import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;

@Transactional
public final class JpaAssetFolderRepository implements AssetFolderRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaAssetFolderRepository.class);

    private final AssetFolderSpringRepository repository;

    public JpaAssetFolderRepository(final AssetFolderSpringRepository assetFolderRepository) {
        super();

        repository = Objects.requireNonNull(assetFolderRepository);
    }

    @Override
    public final void delete(final Long number) {
        log.debug("Deleting asset folder {}", number);

        repository.deleteByNumber(number);

        log.debug("Deleted asset folder {}", number);
    }

    @Override
    public final boolean exists(final Long number) {
        final boolean exists;

        log.debug("Checking if asset folder {} exists", number);

        exists = repository.existsByNumber(number);

        log.debug("Asset folder {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameAndParent(final String name, final Long parent, final Long excluded) {
        final boolean exists;

        log.debug("Checking if asset folder {} exists below parent {}, excluding folder {}", name, parent, excluded);

        if ((parent == null) && (excluded == null)) {
            exists = repository.existsByNameAndParentIsNull(name);
        } else if (parent == null) {
            exists = repository.existsByNameAndParentIsNullAndNumberNot(name, excluded);
        } else if (excluded == null) {
            exists = repository.existsByNameAndParentNumber(name, parent);
        } else {
            exists = repository.existsByNameAndParentNumberAndNumberNot(name, parent, excluded);
        }

        log.debug("Asset folder {} exists below parent {}, excluding folder {}: {}", name, parent, excluded, exists);

        return exists;
    }

    @Override
    public final Collection<AssetFolder> findAll() {
        final Collection<AssetFolder> folders;

        log.debug("Finding all asset folders");

        folders = repository.findAll()
            .stream()
            .map(AssetFolderEntityMapper::toDomain)
            .toList();

        log.debug("Found {} asset folders", folders.size());

        return folders;
    }

    @Override
    public final Optional<AssetFolder> findOne(final Long number) {
        final Optional<AssetFolder> folder;

        log.debug("Finding asset folder with number {}", number);

        folder = repository.findByNumber(number)
            .map(AssetFolderEntityMapper::toDomain);

        log.debug("Found asset folder with number {}: {}", number, folder);

        return folder;
    }

    @Override
    public final boolean hasChildren(final Long number) {
        final boolean hasChildren;

        log.debug("Checking if asset folder {} has children", number);

        hasChildren = repository.existsByParentNumber(number);

        log.debug("Asset folder {} has children: {}", number, hasChildren);

        return hasChildren;
    }

    @Override
    public final AssetFolder save(final AssetFolder folder) {
        final AssetFolderEntity entity;
        final AssetFolderEntity parent;
        final AssetFolderEntity persisted;
        final AssetFolder       saved;

        log.debug("Saving asset folder {}", folder);

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
                    log.error("Missing parent asset folder {}", folder.parentNumber()
                        .get());
                    return new IllegalArgumentException("Asset folder doesn't exist: " + folder.parentNumber()
                        .get());
                });
            entity.setParent(parent);
        }

        persisted = repository.save(entity);
        saved = AssetFolderEntityMapper.toDomain(persisted);

        log.debug("Saved asset folder {}", saved);

        return saved;
    }

}
