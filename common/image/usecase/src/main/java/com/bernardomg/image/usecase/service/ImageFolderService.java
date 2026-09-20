/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.usecase.service;

import java.util.Collection;

import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.domain.model.ImageFolder;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

public interface ImageFolderService {

    public ImageFolder create(final ImageFolder folder);

    public ImageFolder delete(final Long number);

    public Collection<ImageFolder> getAll();

    public Page<Image> getImages(final Long folderNumber, final Pagination pagination, final Sorting sorting);

    public ImageFolder getOne(final Long number);

    public Page<Image> getRootImages(final Pagination pagination, final Sorting sorting);

    public Image moveImage(final Long imageNumber, final Long folderNumber);

    public Image moveImageToRoot(final Long imageNumber);

    public ImageFolder update(final ImageFolder folder);

}
