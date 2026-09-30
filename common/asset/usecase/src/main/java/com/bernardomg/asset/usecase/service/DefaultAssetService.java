/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo MartÃ­nez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.asset.usecase.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.asset.domain.exception.AssetAlreadyExistsException;
import com.bernardomg.asset.domain.exception.AssetNotExistingException;
import com.bernardomg.asset.domain.key.ContentKeyGenerator;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.AssetType;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.asset.domain.policy.ContentPolicy;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

import jakarta.transaction.Transactional;

/**
 * Loads assets from an S3-compatible object store.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Transactional
public final class DefaultAssetService implements AssetService {

    /**
     * Logger for the class.
     */
    private static final Logger       log = LoggerFactory.getLogger(DefaultAssetService.class);

    private final AssetRepository     assetRepository;

    private final ContentKeyGenerator contentKeyGenerator;

    private final ContentPolicy       contentPolicy;

    private final ContentRepository   contentRepository;

    private final String              namespace;

    private final AssetType           type;

    public DefaultAssetService(final AssetType assetType, final String contentNamespace,
            final AssetRepository assetRepo, final ContentRepository contentRepo, final ContentPolicy policy,
            final ContentKeyGenerator keyGenerator) {
        super();

        type = Objects.requireNonNull(assetType);
        namespace = Objects.requireNonNull(contentNamespace);
        assetRepository = Objects.requireNonNull(assetRepo);
        contentRepository = Objects.requireNonNull(contentRepo);
        contentPolicy = Objects.requireNonNull(policy);
        contentKeyGenerator = Objects.requireNonNull(keyGenerator);
    }

    @Override
    public final Asset create(final Asset asset, final Content content) {
        final Asset toCreate;
        final Asset created;

        log.debug("Creating asset {}", asset);

        if (assetRepository.existsByNameAndFolder(type, asset.name(), asset.folderNumber()
            .orElse(null))) {
            log.error("Asset {} already exists", asset.name());
            throw new AssetAlreadyExistsException(asset.name());
        }

        contentPolicy.validate(content.size(), content.mediaType());

        toCreate = new Asset(asset.number(), asset.name(), asset.description(), contentKeyGenerator.generate(namespace),
            content.mediaType(), content.size(), asset.publicAccess(), asset.folderNumber());
        contentRepository.save(toCreate.key(), content);
        try {
            created = assetRepository.save(type, toCreate);
        } catch (final RuntimeException ex) {
            deleteContent(toCreate.key());
            throw ex;
        }

        log.debug("Created asset {}", created);

        return created;
    }

    @Override
    public final Asset delete(final Long number) {
        final Asset deleted;

        log.debug("Deleting asset {}", number);

        deleted = getOne(number);

        assetRepository.delete(type, number);
        deleteContent(deleted.key());

        log.debug("Deleted asset {}", deleted);

        return deleted;
    }

    @Override
    public final Page<Asset> getAll(final Pagination pagination, final Sorting sorting) {
        final Page<Asset> page;

        log.debug("Reading all assets with pagination {} and sorting {}", pagination, sorting);

        page = assetRepository.findAll(type, pagination, sorting);

        log.debug("Read all assets with pagination {} and sorting {}: {}", pagination, sorting, page);

        return page;
    }

    @Override
    public final Page<Asset> getAllPublic(final Pagination pagination, final Sorting sorting) {
        return assetRepository.findAllPublic(type, pagination, sorting);
    }

    @Override
    public final Asset getOne(final Long number) {
        final Asset asset;

        log.debug("Reading asset {}", number);

        asset = assetRepository.findOne(type, number)
            .orElseThrow(() -> {
                log.error("Asset {} doesn't exist", number);
                return new AssetNotExistingException(number);
            });

        log.debug("Read asset {}", asset);

        return asset;
    }

    @Override
    public final Asset update(final Asset asset, final Content content) {
        final Asset  existing;
        final String key;
        final Asset  toUpdate;
        final Asset  updated;

        log.debug("Updating asset {}", asset);

        existing = assetRepository.findOne(type, asset.number())
            .orElseThrow(() -> {
                log.error("Asset {} doesn't exist", asset.number());
                return new AssetNotExistingException(asset.number());
            });
        if (assetRepository.existsByNameAndFolder(type, asset.name(), existing.folderNumber()
            .orElse(null), asset.number())) {
            log.error("Asset {} already exists", asset.name());
            throw new AssetAlreadyExistsException(asset.name());
        }

        contentPolicy.validate(content.size(), content.mediaType());

        key = contentKeyGenerator.generate(namespace);
        contentRepository.save(key, content);
        try {
            toUpdate = new Asset(asset.number(), asset.name(), asset.description(), key, content.mediaType(),
                content.size(), asset.publicAccess(), existing.folderNumber(), existing.audit());
            updated = assetRepository.save(type, toUpdate);
        } catch (final RuntimeException ex) {
            deleteContent(key);
            throw ex;
        }
        deleteContent(existing.key());

        log.debug("Updated asset {}", updated);

        return updated;
    }

    @Override
    public final Asset updateMetadata(final Asset asset) {
        final Asset existing;
        final Asset toUpdate;
        final Asset updated;

        log.debug("Updating metadata for asset {}", asset);

        existing = assetRepository.findOne(type, asset.number())
            .orElseThrow(() -> {
                log.error("Asset {} doesn't exist", asset.number());
                return new AssetNotExistingException(asset.number());
            });
        if (assetRepository.existsByNameAndFolder(type, asset.name(), existing.folderNumber()
            .orElse(null), asset.number())) {
            log.error("Asset {} already exists", asset.name());
            throw new AssetAlreadyExistsException(asset.name());
        }
        toUpdate = new Asset(existing.number(), asset.name(), asset.description(), existing.key(), existing.mediaType(),
            existing.size(), asset.publicAccess(), existing.folderNumber(), existing.audit());
        updated = assetRepository.save(type, toUpdate);

        log.debug("Updated metadata for asset {}", updated);

        return updated;
    }

    private final void deleteContent(final String key) {
        try {
            contentRepository.delete(key);
        } catch (final RuntimeException ex) {
            log.warn("Failed to delete content {}", key, ex);
        }
    }

}
