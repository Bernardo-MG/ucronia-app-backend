
package com.bernardomg.asset.domain.repository;

import java.util.Optional;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.AssetType;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

public interface AssetRepository {

    public void delete(final AssetType type, final Long number);

    public void delete(final Long number);

    public boolean exists(final Long number);

    public boolean existsByNameAndFolder(final String name, final Long folderNumber);

    public boolean existsByNameAndFolder(final String name, final Long folderNumber, final long excludedNumber);

    public Page<Asset> findAll(final AssetType type, final Pagination pagination, final Sorting sorting);

    public Page<Asset> findAll(final Pagination pagination, final Sorting sorting);

    public Page<Asset> findAllByFolder(final Long folderNumber, final Pagination pagination, final Sorting sorting);

    public Page<Asset> findAllPublic(final AssetType type, final Pagination pagination, final Sorting sorting);

    public Page<Asset> findAllPublic(final Pagination pagination, final Sorting sorting);

    public Page<Asset> findAllPublicByFolder(final Long folderNumber, final Pagination pagination,
            final Sorting sorting);

    public Optional<Asset> findOne(final AssetType type, final Long number);

    public Optional<Asset> findOne(final Long number);

    public boolean hasAssetsInFolder(final Long folderNumber);

    public Asset move(final Long number, final Long folderNumber);

    public Asset save(final AssetType type, final Asset asset);

}
