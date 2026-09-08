/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.domain.repository;

import java.util.Optional;

import com.bernardomg.image.domain.model.Image;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

public interface ImageRepository {

    public void delete(final Long number);

    public boolean existsByName(final String name);

    public boolean existsByNameForAnother(final String name, final Long number);

    public Page<Image> findAll(final Pagination pagination, final Sorting sorting);

    public Optional<Image> findOne(final Long number);

    public Image save(final Image image);

}
