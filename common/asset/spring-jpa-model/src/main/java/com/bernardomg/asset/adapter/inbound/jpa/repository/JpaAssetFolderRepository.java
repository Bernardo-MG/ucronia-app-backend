
package com.bernardomg.asset.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetFolderEntity;
import com.bernardomg.asset.adapter.inbound.jpa.model.AssetFolderEntityMapper;
import com.bernardomg.asset.adapter.inbound.jpa.model.AssetType;
import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;

@Transactional
public final class JpaAssetFolderRepository implements AssetFolderRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaAssetFolderRepository.class);

    private final AssetFolderSpringRepository repository;

    private final AssetType                   type;

    public JpaAssetFolderRepository(final AssetType assetType,
            final AssetFolderSpringRepository assetFolderRepository) {
        super();

        type = Objects.requireNonNull(assetType);
        repository = Objects.requireNonNull(assetFolderRepository);
    }

    @Override
    public final void delete(final Long number) {
        log.debug("Deleting {} asset folder {}", type, number);

        repository.deleteByTypeAndNumber(type, number);

        log.debug("Deleted {} asset folder {}", type, number);
    }

    @Override
    public final boolean exists(final Long number) {
        final boolean exists;

        log.debug("Checking if image folder {} exists", number);

        exists = repository.existsByTypeAndNumber(type, number);

        log.debug("Asset folder {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameAndParent(final String name, final Long parent, final Long excluded) {
        final boolean exists;

        log.debug("Checking if image folder {} exists below parent {}, excluding folder {}", name, parent, excluded);

        if ((parent == null) && (excluded == null)) {
            exists = repository.existsByTypeAndNameAndParentIsNull(type, name);
        } else if (parent == null) {
            exists = repository.existsByTypeAndNameAndParentIsNullAndNumberNot(type, name, excluded);
        } else if (excluded == null) {
            exists = repository.existsByTypeAndNameAndParentNumber(type, name, parent);
        } else {
            exists = repository.existsByTypeAndNameAndParentNumberAndNumberNot(type, name, parent, excluded);
        }

        log.debug("Asset folder {} exists below parent {}, excluding folder {}: {}", name, parent, excluded, exists);

        return exists;
    }

    @Override
    public final Collection<AssetFolder> findAll() {
        final Collection<AssetFolder> folders;

        log.debug("Finding all image folders");

        folders = repository.findAllByType(type)
            .stream()
            .map(AssetFolderEntityMapper::toDomain)
            .toList();

        log.debug("Found {} image folders", folders.size());

        return folders;
    }

    @Override
    public final Optional<AssetFolder> findOne(final Long number) {
        final Optional<AssetFolder> folder;

        log.debug("Finding image folder with number {}", number);

        folder = repository.findByTypeAndNumber(type, number)
            .map(AssetFolderEntityMapper::toDomain);

        log.debug("Found image folder with number {}: {}", number, folder);

        return folder;
    }

    @Override
    public final boolean hasChildren(final Long number) {
        final boolean hasChildren;

        log.debug("Checking if image folder {} has children", number);

        hasChildren = repository.existsByTypeAndParentNumber(type, number);

        log.debug("Asset folder {} has children: {}", number, hasChildren);

        return hasChildren;
    }

    @Override
    public final AssetFolder save(final AssetFolder folder) {
        final AssetFolderEntity entity;
        final AssetFolderEntity parent;
        final AssetFolderEntity persisted;
        final AssetFolder       saved;

        log.debug("Saving image folder {}", folder);

        entity = repository.findByTypeAndNumber(type, folder.number())
            .orElseGet(AssetFolderEntity::new);

        if (entity.getNumber() == null) {
            entity.setNumber(repository.findNextNumber(type));
        }

        entity.setType(type);
        entity.setName(folder.name());

        if (folder.parentNumber()
            .isEmpty()) {
            entity.setParent(null);
        } else {
            parent = repository.findByTypeAndNumber(type, folder.parentNumber()
                .get())
                .orElseThrow(() -> {
                    log.error("Missing parent image folder {}", folder.parentNumber()
                        .get());
                    return new IllegalArgumentException("Asset folder doesn't exist: " + folder.parentNumber()
                        .get());
                });
            entity.setParent(parent);
        }

        persisted = repository.save(entity);
        saved = AssetFolderEntityMapper.toDomain(persisted);

        log.debug("Saved image folder {}", saved);

        return saved;
    }

}
