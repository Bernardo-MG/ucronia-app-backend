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

package com.bernardomg.file.usecase.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.asset.domain.exception.AssetAlreadyExistsException;
import com.bernardomg.asset.domain.exception.AssetNotExistingException;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.key.ContentKeyGenerator;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.asset.domain.policy.ContentPolicy;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

import jakarta.transaction.Transactional;

/**
 * Loads files from an S3-compatible object store.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Transactional
public final class DefaultFileService implements FileService {

    /**
     * Logger for the class.
     */
    private static final Logger       log       = LoggerFactory.getLogger(DefaultFileService.class);

    private final ContentKeyGenerator contentKeyGenerator;

    private final ContentRepository   contentRepository;

    private final ContentPolicy       fileContentPolicy;

    private final AssetRepository     fileRepository;

    private final String              namespace = "files";

    public DefaultFileService(final AssetRepository fileRepo, final ContentRepository contentRepo,
            final ContentPolicy contentPolicy, final ContentKeyGenerator keyGenerator) {
        super();

        fileRepository = Objects.requireNonNull(fileRepo);
        contentRepository = Objects.requireNonNull(contentRepo);
        fileContentPolicy = Objects.requireNonNull(contentPolicy);
        contentKeyGenerator = Objects.requireNonNull(keyGenerator);
    }

    @Override
    public final Asset create(final Asset file, final Content content) {
        final Asset toCreate;
        final Asset created;

        log.debug("Creating file {}", file);

        if (fileRepository.existsByNameAndFolder(file.name(), file.folderNumber()
            .orElse(null))) {
            log.error("Asset {} already exists", file.name());
            throw new AssetAlreadyExistsException(file.name());
        }

        fileContentPolicy.validate(content.size(), content.mediaType());

        toCreate = new Asset(file.number(), file.name(), file.description(), contentKeyGenerator.generate(namespace),
            content.mediaType(), content.size(), file.publicAccess(), file.folderNumber());
        contentRepository.save(toCreate.key(), content);
        try {
            created = fileRepository.save(toCreate);
        } catch (final RuntimeException ex) {
            deleteContent(toCreate.key());
            throw ex;
        }

        log.debug("Created file {}", created);

        return created;
    }

    @Override
    public final Asset delete(final Long number) {
        final Asset deleted;

        log.debug("Deleting file {}", number);

        deleted = getOne(number);

        fileRepository.delete(number);
        deleteContent(deleted.key());

        log.debug("Deleted file {}", deleted);

        return deleted;
    }

    @Override
    public final Page<Asset> getAll(final Pagination pagination, final Sorting sorting) {
        final Page<Asset> page;

        log.debug("Reading all files with pagination {} and sorting {}", pagination, sorting);

        page = fileRepository.findAll(pagination, sorting);

        log.debug("Read all files with pagination {} and sorting {}: {}", pagination, sorting, page);

        return page;
    }

    @Override
    public final Page<Asset> getAllPublic(final Pagination pagination, final Sorting sorting) {
        return fileRepository.findAllPublic(pagination, sorting);
    }

    @Override
    public final Content getContent(final Long number) {
        final Asset   file;
        final Content fileContent;

        log.debug("Reading file content for {}", number);

        file = fileRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Asset {} doesn't exist", number);
                return new AssetNotExistingException(number);
            });

        fileContent = contentRepository.getOne(file.key());

        log.debug("Read file content for {}", number);

        return fileContent;
    }

    @Override
    public final Asset getOne(final Long number) {
        final Asset file;

        log.debug("Reading file {}", number);

        file = fileRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Asset {} doesn't exist", number);
                return new AssetNotExistingException(number);
            });

        log.debug("Read file {}", file);

        return file;
    }

    @Override
    public final Asset update(final Asset file, final Content content) {
        final Asset  existing;
        final String key;
        final Asset  toUpdate;
        final Asset  updated;

        log.debug("Updating file {}", file);

        existing = fileRepository.findOne(file.number())
            .orElseThrow(() -> {
                log.error("Asset {} doesn't exist", file.number());
                return new AssetNotExistingException(file.number());
            });
        if (fileRepository.existsByNameAndFolder(file.name(), existing.folderNumber()
            .orElse(null), file.number())) {
            log.error("Asset {} already exists", file.name());
            throw new AssetAlreadyExistsException(file.name());
        }

        fileContentPolicy.validate(content.size(), content.mediaType());

        key = contentKeyGenerator.generate(namespace);
        contentRepository.save(key, content);
        try {
            toUpdate = new Asset(file.number(), file.name(), file.description(), key, content.mediaType(),
                content.size(), file.publicAccess(), existing.folderNumber(), existing.audit());
            updated = fileRepository.save(toUpdate);
        } catch (final RuntimeException ex) {
            deleteContent(key);
            throw ex;
        }
        deleteContent(existing.key());

        log.debug("Updated file {}", updated);

        return updated;
    }

    @Override
    public final Asset updateMetadata(final Asset file) {
        final Asset existing;
        final Asset toUpdate;
        final Asset updated;

        log.debug("Updating metadata for file {}", file);

        existing = fileRepository.findOne(file.number())
            .orElseThrow(() -> {
                log.error("Asset {} doesn't exist", file.number());
                return new AssetNotExistingException(file.number());
            });
        if (fileRepository.existsByNameAndFolder(file.name(), existing.folderNumber()
            .orElse(null), file.number())) {
            log.error("Asset {} already exists", file.name());
            throw new AssetAlreadyExistsException(file.name());
        }
        toUpdate = new Asset(existing.number(), file.name(), file.description(), existing.key(), existing.mediaType(),
            existing.size(), file.publicAccess(), existing.folderNumber(), existing.audit());
        updated = fileRepository.save(toUpdate);

        log.debug("Updated metadata for file {}", updated);

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
