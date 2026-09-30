/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.asset.usecase.service;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.Content;

public interface AssetContentService {

    public Content getContent(final Asset asset);

    public Asset getOne(final Long number);

}
