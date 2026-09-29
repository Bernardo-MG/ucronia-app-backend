
package com.bernardomg.asset.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetEntity;
import com.bernardomg.asset.adapter.inbound.jpa.model.AssetEntityMapper;
import com.bernardomg.asset.adapter.inbound.jpa.model.AssetFolderEntity;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.AssetType;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringPagination;

@Transactional
public final class JpaAssetRepository implements AssetRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaAssetRepository.class);

    private final AssetFolderSpringRepository folderRepository;

    private final AssetSpringRepository       repository;

    public JpaAssetRepository(final AssetSpringRepository assetRepository,
            final AssetFolderSpringRepository assetFolderRepository) {
        repository = Objects.requireNonNull(assetRepository);
        folderRepository = Objects.requireNonNull(assetFolderRepository);
    }

    @Override
    public final void delete(final AssetType type, final Long number) {
        log.debug("Deleting {} asset {}", type, number);

        repository.deleteByTypeAndNumber(type, number);

        log.debug("Deleted {} asset {}", type, number);
    }

    @Override
    public final void delete(final Long number) {
        log.debug("Deleting asset {}", number);

        repository.deleteByNumber(number);

        log.debug("Deleted asset {}", number);
    }

    @Override
    public final boolean exists(final Long number) {
        final boolean exists;

        log.debug("Checking if asset {} exists", number);

        exists = repository.existsByNumber(number);

        log.debug("Asset {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameAndFolder(final AssetType type, final String name, final Long folderNumber) {
        final boolean exists;

        if (folderNumber == null) {
            exists = repository.existsByTypeAndNameAndFolderIsNull(type, name);
        } else {
            exists = repository.existsByTypeAndNameAndFolderNumber(type, name, folderNumber);
        }
        return exists;
    }

    @Override
    public final boolean existsByNameAndFolder(final AssetType type, final String name, final Long folderNumber,
            final long excludedNumber) {
        final boolean exists;

        if (folderNumber == null) {
            exists = repository.existsByTypeAndNameAndNumberNotAndFolderIsNull(type, name, excludedNumber);
        } else {
            exists = repository.existsByTypeAndNameAndFolderNumberAndNumberNot(type, name, folderNumber,
                excludedNumber);
        }
        return exists;
    }

    @Override
    public final boolean existsByNameAndFolder(final String name, final Long folderNumber) {
        final boolean exists;

        log.debug("Checking if asset {} exists in folder {}", name, folderNumber);

        if (folderNumber == null) {
            exists = repository.existsByNameAndFolderIsNull(name);
        } else {
            exists = repository.existsByNameAndFolderNumber(name, folderNumber);
        }

        log.debug("Asset {} exists in folder {}: {}", name, folderNumber, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameAndFolder(final String name, final Long folderNumber, final long excludedNumber) {
        final boolean exists;

        log.debug("Checking if asset {} exists in folder {}, excluding {}", name, folderNumber, excludedNumber);

        if (folderNumber == null) {
            exists = repository.existsByNameAndNumberNotAndFolderIsNull(name, excludedNumber);
        } else {
            exists = repository.existsByNameAndFolderNumberAndNumberNot(name, folderNumber, excludedNumber);
        }

        log.debug("Asset {} exists in folder {}: {}", name, folderNumber, exists);

        return exists;
    }

    @Override
    public final Page<Asset> findAll(final AssetType type, final Pagination pagination, final Sorting sorting) {
        final Pageable pageable;

        pageable = SpringPagination.toPageable(pagination, sorting);
        return SpringPagination.toPage(repository.findAllByType(type, pageable)
            .map(AssetEntityMapper::toDomain));
    }

    @Override
    public final Page<Asset> findAll(final Pagination pagination, final Sorting sorting) {
        final Pageable                                    pageable;
        final org.springframework.data.domain.Page<Asset> read;

        log.debug("Finding assets with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = repository.findAll(pageable)
            .map(AssetEntityMapper::toDomain);

        log.debug("Found assets {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Page<Asset> findAllByFolder(final Long folderNumber, final Pagination pagination,
            final Sorting sorting) {
        final Pageable                                    pageable;
        final org.springframework.data.domain.Page<Asset> read;

        pageable = SpringPagination.toPageable(pagination, sorting);
        if (folderNumber == null) {
            read = repository.findAllByFolderIsNull(pageable)
                .map(AssetEntityMapper::toDomain);
        } else {
            read = repository.findAllByFolderNumber(folderNumber, pageable)
                .map(AssetEntityMapper::toDomain);
        }
        return SpringPagination.toPage(read);
    }

    @Override
    public final Page<Asset> findAllPublic(final AssetType type, final Pagination pagination, final Sorting sorting) {
        final Pageable pageable;

        pageable = SpringPagination.toPageable(pagination, sorting);
        return SpringPagination.toPage(repository.findAllByTypeAndPublicAccessTrue(type, pageable)
            .map(AssetEntityMapper::toDomain));
    }

    @Override
    public final Page<Asset> findAllPublic(final Pagination pagination, final Sorting sorting) {
        final Pageable pageable;

        pageable = SpringPagination.toPageable(pagination, sorting);
        return SpringPagination.toPage(repository.findAllByPublicAccessTrue(pageable)
            .map(AssetEntityMapper::toDomain));
    }

    @Override
    public final Page<Asset> findAllPublicByFolder(final Long folderNumber, final Pagination pagination,
            final Sorting sorting) {
        final Pageable                                    pageable;
        final org.springframework.data.domain.Page<Asset> read;

        pageable = SpringPagination.toPageable(pagination, sorting);
        if (folderNumber == null) {
            read = repository.findAllByFolderIsNullAndPublicAccessTrue(pageable)
                .map(AssetEntityMapper::toDomain);
        } else {
            read = repository.findAllByFolderNumberAndPublicAccessTrue(folderNumber, pageable)
                .map(AssetEntityMapper::toDomain);
        }

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<Asset> findOne(final AssetType type, final Long number) {
        return repository.findByTypeAndNumber(type, number)
            .map(AssetEntityMapper::toDomain);
    }

    @Override
    public final Optional<Asset> findOne(final Long number) {
        final Optional<Asset> asset;

        log.debug("Finding asset with number {}", number);

        asset = repository.findByNumber(number)
            .map(AssetEntityMapper::toDomain);

        log.debug("Found asset with number {}: {}", number, asset);

        return asset;
    }

    @Override
    public final boolean hasAssetsInFolder(final Long folderNumber) {
        return repository.existsByFolderNumber(folderNumber);
    }

    @Override
    public final Asset move(final Long number, final Long folderNumber) {
        final AssetEntity       entity;
        final AssetFolderEntity folderEntity;

        entity = repository.findByNumber(number)
            .orElseThrow();
        if (folderNumber == null) {
            folderEntity = null;
        } else {
            folderEntity = folderRepository.findByNumber(folderNumber)
                .orElseThrow();
        }
        entity.setFolder(folderEntity);
        return AssetEntityMapper.toDomain(repository.save(entity));
    }

    @Override
    public final Asset save(final AssetType type, final Asset asset) {
        final Optional<AssetEntity> existing;
        final AssetEntity           entity;
        final Long                  number;
        final Asset                 toCreate;
        final Asset                 saved;
        final AssetFolderEntity     folder;

        log.debug("Saving asset {}", asset);

        existing = repository.findByTypeAndNumber(type, asset.number());
        if (existing.isPresent()) {
            entity = AssetEntityMapper.toEntity(asset);
            entity.setId(existing.get()
                .getId());
        } else {
            number = repository.findNextNumber();
            toCreate = new Asset(number, asset.name(), asset.description(), asset.key(), asset.mediaType(),
                asset.size(), asset.publicAccess(), asset.folderNumber(), asset.audit());
            entity = AssetEntityMapper.toEntity(toCreate);
        }
        entity.setType(type);

        if (asset.folderNumber()
            .isEmpty()) {
            entity.setFolder(null);
        } else {
            folder = folderRepository.findByNumber(asset.folderNumber()
                .get())
                .orElseThrow();
            entity.setFolder(folder);
        }

        saved = AssetEntityMapper.toDomain(repository.save(entity));

        log.debug("Saved asset {}", saved);

        return saved;
    }
}
