/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.file.domain.repository;

import java.util.Optional;

import com.bernardomg.file.domain.model.File;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

public interface FileRepository {

    public void delete(final Long number);

    public boolean exists(final Long number);

    public boolean existsByNameAndFolder(final String name, final Long folderNumber);

    public boolean existsByNameAndFolder(final String name, final Long folderNumber, final long excludedNumber);

    public Page<File> findAll(final Pagination pagination, final Sorting sorting);

    public Page<File> findAllByFolder(final Long folderNumber, final Pagination pagination, final Sorting sorting);

    public Page<File> findAllPublic(final Pagination pagination, final Sorting sorting);

    public Page<File> findAllPublicByFolder(final Long folderNumber, final Pagination pagination,
            final Sorting sorting);

    public Optional<File> findOne(final Long number);

    public boolean hasFilesInFolder(final Long folderNumber);

    public File move(final Long number, final Long folderNumber);

    public File save(final File file);

}
