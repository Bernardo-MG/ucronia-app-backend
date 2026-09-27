/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.domain.model;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.security.domain.audit.model.AuditDetails;

public record Image(Long number, String name, String description, String key, String mediaType, long size,
        boolean publicAccess, Optional<Long> folderNumber, AuditDetails audit) {

    public Image {
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(name, "Name can't be null");
        Objects.requireNonNull(description, "Description can't be null");
        Objects.requireNonNull(key, "Key can't be null");
        Objects.requireNonNull(mediaType, "Media type can't be null");
        Objects.requireNonNull(folderNumber, "Folder number can't be null");
        Objects.requireNonNull(audit, "Audit can't be null");

        name = StringUtils.trim(name);
        description = StringUtils.trim(description);
        key = StringUtils.trim(key);
        mediaType = StringUtils.trim(mediaType);
    }

    public Image(final Long number, final String name, final String description, final String key,
            final String mediaType, final long size, final AuditDetails audit) {
        this(number, name, description, key, mediaType, size, true, Optional.empty(), audit);
    }

    public Image(final Long number, final String name, final String description, final String key,
            final String mediaType, final long size, final Optional<Long> folderNumber, final AuditDetails audit) {
        this(number, name, description, key, mediaType, size, true, folderNumber, audit);
    }

    public Image(final Long number, final String name, final String description, final String key,
            final String mediaType, final long size, final Optional<Long> folderNumber) {
        this(number, name, description, key, mediaType, size, true, folderNumber, new AuditDetails());
    }

    public Image(final Long number, final String name, final String description, final String key,
            final String mediaType, final long size, final boolean publicAccess, final Optional<Long> folderNumber) {
        this(number, name, description, key, mediaType, size, publicAccess, folderNumber, new AuditDetails());
    }

    public Image(final Long number, final String name, final String description, final String key,
            final String mediaType, final long size) {
        this(number, name, description, key, mediaType, size, true, Optional.empty(), new AuditDetails());
    }

}
