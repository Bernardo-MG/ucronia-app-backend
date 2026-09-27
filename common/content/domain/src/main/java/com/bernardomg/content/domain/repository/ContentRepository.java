/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.content.domain.repository;

import com.bernardomg.content.domain.model.Content;

public interface ContentRepository {

    public void delete(final String key);

    public Content getOne(final String key);

    public void save(final String key, final Content content);

}
