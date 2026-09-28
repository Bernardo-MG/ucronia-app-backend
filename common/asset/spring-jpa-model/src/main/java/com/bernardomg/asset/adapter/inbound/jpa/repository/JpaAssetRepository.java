
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
import com.bernardomg.asset.adapter.inbound.jpa.model.AssetType;
import com.bernardomg.asset.domain.model.Asset;
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

    private final AssetType                   type;

    public JpaAssetRepository(final AssetType assetType, final AssetSpringRepository assetRepository,
            final AssetFolderSpringRepository assetFolderRepository) {
        type = Objects.requireNonNull(assetType);
        repository = Objects.requireNonNull(assetRepository);
        folderRepository = Objects.requireNonNull(assetFolderRepository);
    }

    @Override
    public final void delete(final Long number) {
        log.debug("Deleting {} asset {}", type, number);

        repository.deleteByTypeAndNumber(type, number);

        log.debug("Deleted {} asset {}", type, number);
    }

    @Override
    public final boolean exists(final Long number) {
        final boolean exists;

        log.debug("Checking if {} asset {} exists", type, number);

        exists = repository.existsByTypeAndNumber(type, number);

        log.debug("Asset {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameAndFolder(final String name, final Long folderNumber) {
        final boolean exists;

        log.debug("Checking if image {} exists in folder {}", name, folderNumber);

        if (folderNumber == null) {
            exists = repository.existsByTypeAndNameAndFolderIsNull(type, name);
        } else {
            exists = repository.existsByTypeAndNameAndFolderNumber(type, name, folderNumber);
        }

        log.debug("Asset {} exists in folder {}: {}", name, folderNumber, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameAndFolder(final String name, final Long folderNumber, final long excludedNumber) {
        final boolean exists;

        log.debug("Checking if image {} exists in folder {}, excluding {}", name, folderNumber, excludedNumber);

        if (folderNumber == null) {
            exists = repository.existsByTypeAndNameAndNumberNotAndFolderIsNull(type, name, excludedNumber);
        } else {
            exists = repository.existsByTypeAndNameAndFolderNumberAndNumberNot(type, name, folderNumber,
                excludedNumber);
        }

        log.debug("Asset {} exists in folder {}: {}", name, folderNumber, exists);

        return exists;
    }

    @Override
    public final Page<Asset> findAll(final Pagination pagination, final Sorting sorting) {
        final Pageable                                    pageable;
        final org.springframework.data.domain.Page<Asset> read;

        log.debug("Finding {} assets with pagination {} and sorting {}", type, pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = repository.findAllByType(type, pageable)
            .map(AssetEntityMapper::toDomain);

        log.debug("Found {} assets {}", type, read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Page<Asset> findAllByFolder(final Long folderNumber, final Pagination pagination,
            final Sorting sorting) {
        final Pageable                                    pageable;
        final org.springframework.data.domain.Page<Asset> read;

        pageable = SpringPagination.toPageable(pagination, sorting);
        if (folderNumber == null) {
            read = repository.findAllByTypeAndFolderIsNull(type, pageable)
                .map(AssetEntityMapper::toDomain);
        } else {
            read = repository.findAllByTypeAndFolderNumber(type, folderNumber, pageable)
                .map(AssetEntityMapper::toDomain);
        }
        return SpringPagination.toPage(read);
    }

    @Override
    public final Page<Asset> findAllPublic(final Pagination pagination, final Sorting sorting) {
        final Pageable pageable;

        pageable = SpringPagination.toPageable(pagination, sorting);
        return SpringPagination.toPage(repository.findAllByTypeAndPublicAccessTrue(type, pageable)
            .map(AssetEntityMapper::toDomain));
    }

    @Override
    public final Page<Asset> findAllPublicByFolder(final Long folderNumber, final Pagination pagination,
            final Sorting sorting) {
        final Pageable                                    pageable;
        final org.springframework.data.domain.Page<Asset> read;

        pageable = SpringPagination.toPageable(pagination, sorting);
        if (folderNumber == null) {
            read = repository.findAllByTypeAndFolderIsNullAndPublicAccessTrue(type, pageable)
                .map(AssetEntityMapper::toDomain);
        } else {
            read = repository.findAllByTypeAndFolderNumberAndPublicAccessTrue(type, folderNumber, pageable)
                .map(AssetEntityMapper::toDomain);
        }

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<Asset> findOne(final Long number) {
        final Optional<Asset> image;

        log.debug("Finding {} asset with number {}", type, number);

        image = repository.findByTypeAndNumber(type, number)
            .map(AssetEntityMapper::toDomain);

        log.debug("Found {} asset with number {}: {}", type, number, image);

        return image;
    }

    @Override
    public final boolean hasAssetsInFolder(final Long folderNumber) {
        return repository.existsByTypeAndFolderNumber(type, folderNumber);
    }

    @Override
    public final Asset move(final Long number, final Long folderNumber) {
        final AssetEntity       entity;
        final AssetFolderEntity folderEntity;

        entity = repository.findByTypeAndNumber(type, number)
            .orElseThrow();
        if (folderNumber == null) {
            folderEntity = null;
        } else {
            folderEntity = folderRepository.findByTypeAndNumber(type, folderNumber)
                .orElseThrow();
        }
        entity.setFolder(folderEntity);
        return AssetEntityMapper.toDomain(repository.save(entity));
    }

    @Override
    public final Asset save(final Asset image) {
        final Optional<AssetEntity> existing;
        final AssetEntity           entity;
        final Long                  number;
        final Asset                 toCreate;
        final Asset                 saved;
        final AssetFolderEntity     folder;

        log.debug("Saving image {}", image);

        existing = repository.findByTypeAndNumber(type, image.number());
        if (existing.isPresent()) {
            entity = AssetEntityMapper.toEntity(image);
            entity.setType(type);
            entity.setId(existing.get()
                .getId());
        } else {
            number = repository.findNextNumber(type);
            toCreate = new Asset(number, image.name(), image.description(), image.key(), image.mediaType(),
                image.size(), image.publicAccess(), image.folderNumber(), image.audit());
            entity = AssetEntityMapper.toEntity(toCreate);
            entity.setType(type);
        }

        if (image.folderNumber()
            .isEmpty()) {
            entity.setFolder(null);
        } else {
            folder = folderRepository.findByTypeAndNumber(type, image.folderNumber()
                .get())
                .orElseThrow();
            entity.setFolder(folder);
        }

        saved = AssetEntityMapper.toDomain(repository.save(entity));

        log.debug("Saved image {}", saved);

        return saved;
    }
}
