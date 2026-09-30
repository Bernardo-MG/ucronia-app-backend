/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.asset.usecase.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.asset.domain.exception.AssetAlreadyExistsException;
import com.bernardomg.asset.domain.exception.AssetFolderAlreadyExistsException;
import com.bernardomg.asset.domain.exception.AssetFolderCantBeMovedException;
import com.bernardomg.asset.domain.exception.AssetFolderNotEmptyException;
import com.bernardomg.asset.domain.exception.AssetFolderNotExistingException;
import com.bernardomg.asset.domain.exception.AssetNotExistingException;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.asset.domain.repository.AssetFolderRepository;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

import jakarta.transaction.Transactional;

@Transactional
public final class DefaultAssetFolderService implements AssetFolderService {

    /**
     * Logger for the class.
     */
    private static final Logger         log = LoggerFactory.getLogger(DefaultAssetFolderService.class);

    private final AssetRepository       assetRepository;

    private final AssetFolderRepository folderRepository;

    public DefaultAssetFolderService(final AssetFolderRepository folderRepo, final AssetRepository assetRepo) {
        super();

        folderRepository = Objects.requireNonNull(folderRepo);
        assetRepository = Objects.requireNonNull(assetRepo);
    }

    @Override
    public final AssetFolder create(final AssetFolder folder) {
        final AssetFolder created;

        log.debug("Creating asset folder {}", folder);

        validateParent(null, folder.parentNumber()
            .orElse(null));

        if (folderRepository.existsByNameAndParent(folder.name(), folder.parentNumber()
            .orElse(null), null)) {
            log.error("Asset folder with name {} already exists below parent {}", folder.name(), folder.parentNumber()
                .orElse(null));
            throw new AssetFolderAlreadyExistsException(folder.name());
        }

        created = folderRepository.save(new AssetFolder(-1L, folder.name(), folder.parentNumber()));

        log.debug("Created asset folder {}", created);

        return created;
    }

    @Override
    public final AssetFolder delete(final Long number) {
        final AssetFolder deleted;

        log.debug("Deleting asset folder {}", number);

        deleted = folderRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing asset folder {}", number);
                return new AssetFolderNotExistingException(number);
            });

        if (folderRepository.hasChildren(number) || assetRepository.hasAssetsInFolder(number)) {
            log.error("Asset folder {} is not empty", number);
            throw new AssetFolderNotEmptyException(number);
        }

        folderRepository.delete(number);

        log.debug("Deleted asset folder {}", deleted);

        return deleted;
    }

    @Override
    public final Collection<AssetFolder> getAll() {
        final Collection<AssetFolder> folders;

        log.debug("Reading all asset folders");

        folders = folderRepository.findAll();

        log.debug("Read {} asset folders", folders.size());

        return folders;
    }

    @Override
    public final Page<Asset> getAssets(final Long folderNumber, final Pagination pagination, final Sorting sorting) {
        final Page<Asset> assets;

        log.debug("Reading assets in folder {} with pagination {} and sorting {}", folderNumber, pagination, sorting);

        if (!folderRepository.exists(folderNumber)) {
            log.error("Missing asset folder {}", folderNumber);
            throw new AssetFolderNotExistingException(folderNumber);
        }

        assets = assetRepository.findAllByFolder(folderNumber, pagination, sorting);

        log.debug("Read assets in folder {} with pagination {} and sorting {}", folderNumber, pagination, sorting);

        return assets;
    }

    @Override
    public final AssetFolder getOne(final Long number) {
        final AssetFolder folder;

        log.debug("Reading asset folder {}", number);

        folder = folderRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing asset folder {}", number);
                return new AssetFolderNotExistingException(number);
            });

        log.debug("Read asset folder {}", folder);

        return folder;
    }

    @Override
    public final Page<Asset> getPublicAssets(final Long folderNumber, final Pagination pagination,
            final Sorting sorting) {
        if (!folderRepository.exists(folderNumber)) {
            throw new AssetFolderNotExistingException(folderNumber);
        }
        return assetRepository.findAllPublicByFolder(folderNumber, pagination, sorting);
    }

    @Override
    public final Page<Asset> getPublicRootAssets(final Pagination pagination, final Sorting sorting) {
        return assetRepository.findAllPublicByFolder(null, pagination, sorting);
    }

    @Override
    public final Page<Asset> getRootAssets(final Pagination pagination, final Sorting sorting) {
        final Page<Asset> assets;

        log.debug("Reading root assets with pagination {} and sorting {}", pagination, sorting);

        assets = assetRepository.findAllByFolder(null, pagination, sorting);

        log.debug("Read root assets with pagination {} and sorting {}", pagination, sorting);

        return assets;
    }

    @Override
    public final Asset moveAsset(final Long assetNumber, final Long folderNumber) {
        final Asset asset;
        final Asset moved;

        log.debug("Moving asset {} to folder {}", assetNumber, folderNumber);

        if (!folderRepository.exists(folderNumber)) {
            log.error("Missing asset folder {}", folderNumber);
            throw new AssetFolderNotExistingException(folderNumber);
        }

        asset = assetRepository.findOne(assetNumber)
            .orElseThrow(() -> {
                log.error("Missing asset {}", assetNumber);
                return new AssetNotExistingException(assetNumber);
            });
        validateAssetName(asset, folderNumber);

        moved = assetRepository.move(assetNumber, folderNumber);

        log.debug("Moved asset {} to folder {}", assetNumber, folderNumber);

        return moved;
    }

    @Override
    public final Asset moveAssetToRoot(final Long assetNumber) {
        final Asset asset;
        final Asset moved;

        log.debug("Moving asset {} to the root folder", assetNumber);

        asset = assetRepository.findOne(assetNumber)
            .orElseThrow(() -> {
                log.error("Missing asset {}", assetNumber);
                return new AssetNotExistingException(assetNumber);
            });
        validateAssetName(asset, null);

        moved = assetRepository.move(assetNumber, null);

        log.debug("Moved asset {} to the root folder", assetNumber);

        return moved;
    }

    @Override
    public final AssetFolder update(final AssetFolder folder) {
        final AssetFolder existing;
        final AssetFolder updated;

        log.debug("Updating asset folder {}", folder);

        if (!folderRepository.exists(folder.number())) {
            log.error("Missing asset folder {}", folder.number());
            throw new AssetFolderNotExistingException(folder.number());
        }

        existing = folderRepository.findOne(folder.number())
            .orElseThrow(() -> {
                log.error("Missing asset folder {}", folder.number());
                return new AssetFolderNotExistingException(folder.number());
            });

        validateParent(folder.number(), folder.parentNumber()
            .orElse(null));

        if (folderRepository.existsByNameAndParent(folder.name(), folder.parentNumber()
            .orElse(null), folder.number())) {
            log.error("Asset folder with name {} already exists below parent {}", folder.name(), folder.parentNumber()
                .orElse(null));
            throw new AssetFolderAlreadyExistsException(folder.name());
        }

        updated = folderRepository
            .save(new AssetFolder(folder.number(), folder.name(), folder.parentNumber(), existing.audit()));

        log.debug("Updated asset folder {}", updated);

        return updated;
    }

    private final void validateAssetName(final Asset asset, final Long folderNumber) {
        if (assetRepository.existsByNameAndFolder(asset.name(), folderNumber, asset.number())) {
            log.error("Asset {} already exists in folder {}", asset.name(), folderNumber);
            throw new AssetAlreadyExistsException(asset.name());
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
                throw new AssetFolderCantBeMovedException(folderNumber);
            }

            if (!folderRepository.exists(current)) {
                log.error("Missing asset folder {}", current);
                throw new AssetFolderNotExistingException(current);
            }

            current = folderRepository.findOne(current)
                .orElseThrow()
                .parentNumber()
                .orElse(null);
        }
    }

}
