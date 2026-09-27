/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.file.domain.repository;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.file.domain.model.FileFolder;

public interface FileFolderRepository {

    public void delete(final Long number);

    public boolean exists(final Long number);

    public boolean existsByNameAndParent(final String name, final Long parentNumber, final Long excludedNumber);

    public Collection<FileFolder> findAll();

    public Optional<FileFolder> findOne(final Long number);

    public boolean hasChildren(final Long number);

    public FileFolder save(final FileFolder folder);

}
