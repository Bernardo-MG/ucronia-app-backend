/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.asset.domain.repository;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.asset.domain.model.AssetFolder;

public interface AssetFolderRepository {

    public void delete(final Long number);

    public boolean exists(final Long number);

    public boolean existsByNameAndParent(final String name, final Long parentNumber, final Long excludedNumber);

    public Collection<AssetFolder> findAll();

    public Optional<AssetFolder> findOne(final Long number);

    public boolean hasChildren(final Long number);

    public AssetFolder save(final AssetFolder folder);

}
