/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.asset.usecase.service;

import java.util.Objects;

import com.bernardomg.asset.domain.exception.AssetNotExistingException;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.repository.ContentRepository;

public final class DefaultAssetContentService implements AssetContentService {

    private final AssetRepository   assetRepository;

    private final ContentRepository contentRepository;

    public DefaultAssetContentService(final AssetRepository assetRepo, final ContentRepository contentRepo) {
        super();

        assetRepository = Objects.requireNonNull(assetRepo);
        contentRepository = Objects.requireNonNull(contentRepo);
    }

    @Override
    public final Content getContent(final Asset asset) {
        return contentRepository.getOne(asset.key());
    }

    @Override
    public final Asset getOne(final Long number) {
        return assetRepository.findOne(number)
            .orElseThrow(() -> new AssetNotExistingException(number));
    }

}
