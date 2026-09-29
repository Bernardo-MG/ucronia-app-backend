/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.asset.domain.repository;

import com.bernardomg.asset.domain.model.Content;

public interface ContentRepository {

    public void delete(final String key);

    public Content getOne(final String key);

    public void save(final String key, final Content content);

}
