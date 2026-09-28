/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.asset.usecase.service;

import java.util.Collection;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

public interface AssetFolderService {

    public AssetFolder create(final AssetFolder folder);

    public AssetFolder delete(final Long number);

    public Collection<AssetFolder> getAll();

    public Page<Asset> getAssets(final Long folderNumber, final Pagination pagination, final Sorting sorting);

    public AssetFolder getOne(final Long number);

    public Page<Asset> getPublicAssets(final Long folderNumber, final Pagination pagination, final Sorting sorting);

    public Page<Asset> getPublicRootAssets(final Pagination pagination, final Sorting sorting);

    public Page<Asset> getRootAssets(final Pagination pagination, final Sorting sorting);

    public Asset moveAsset(final Long assetNumber, final Long folderNumber);

    public Asset moveAssetToRoot(final Long assetNumber);

    public AssetFolder update(final AssetFolder folder);

}
