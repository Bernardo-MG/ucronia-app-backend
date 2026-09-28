/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.usecase.service;

import java.util.Collection;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

public interface ImageFolderService {

    public AssetFolder create(final AssetFolder folder);

    public AssetFolder delete(final Long number);

    public Collection<AssetFolder> getAll();

    public Page<Asset> getImages(final Long folderNumber, final Pagination pagination, final Sorting sorting);

    public AssetFolder getOne(final Long number);

    public Page<Asset> getPublicImages(final Long folderNumber, final Pagination pagination, final Sorting sorting);

    public Page<Asset> getPublicRootImages(final Pagination pagination, final Sorting sorting);

    public Page<Asset> getRootImages(final Pagination pagination, final Sorting sorting);

    public Asset moveImage(final Long imageNumber, final Long folderNumber);

    public Asset moveImageToRoot(final Long imageNumber);

    public AssetFolder update(final AssetFolder folder);

}
