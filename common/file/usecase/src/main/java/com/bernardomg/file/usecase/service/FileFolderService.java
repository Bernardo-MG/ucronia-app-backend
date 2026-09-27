/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.file.usecase.service;

import java.util.Collection;

import com.bernardomg.file.domain.model.File;
import com.bernardomg.file.domain.model.FileFolder;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

public interface FileFolderService {

    public FileFolder create(final FileFolder folder);

    public FileFolder delete(final Long number);

    public Collection<FileFolder> getAll();

    public Page<File> getFiles(final Long folderNumber, final Pagination pagination, final Sorting sorting);

    public FileFolder getOne(final Long number);

    public Page<File> getPublicFiles(final Long folderNumber, final Pagination pagination, final Sorting sorting);

    public Page<File> getPublicRootFiles(final Pagination pagination, final Sorting sorting);

    public Page<File> getRootFiles(final Pagination pagination, final Sorting sorting);

    public File moveFile(final Long fileNumber, final Long folderNumber);

    public File moveFileToRoot(final Long fileNumber);

    public FileFolder update(final FileFolder folder);

}
