/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.file.usecase.service;

import java.util.Collection;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

public interface FileFolderService {

    public AssetFolder create(final AssetFolder folder);

    public AssetFolder delete(final Long number);

    public Collection<AssetFolder> getAll();

    public Page<Asset> getFiles(final Long folderNumber, final Pagination pagination, final Sorting sorting);

    public AssetFolder getOne(final Long number);

    public Page<Asset> getPublicFiles(final Long folderNumber, final Pagination pagination, final Sorting sorting);

    public Page<Asset> getPublicRootFiles(final Pagination pagination, final Sorting sorting);

    public Page<Asset> getRootFiles(final Pagination pagination, final Sorting sorting);

    public Asset moveFile(final Long fileNumber, final Long folderNumber);

    public Asset moveFileToRoot(final Long fileNumber);

    public AssetFolder update(final AssetFolder folder);

}
