/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.domain.repository;

import com.bernardomg.image.domain.model.ImageContent;

public interface ImageContentRepository {

    public void delete(final String key);

    public ImageContent getOne(final String key);

    public void save(final String name, final ImageContent content);

}
