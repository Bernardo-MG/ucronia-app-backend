/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.domain.repository;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.image.domain.model.ImageFolder;

public interface ImageFolderRepository {

    public void delete(final Long number);

    public boolean exists(final Long number);

    public boolean existsByNameAndParent(final String name, final Long parentNumber, final Long excludedNumber);

    public Collection<ImageFolder> findAll();

    public Optional<ImageFolder> findOne(final Long number);

    public boolean hasChildren(final Long number);

    public ImageFolder save(final ImageFolder folder);

}
