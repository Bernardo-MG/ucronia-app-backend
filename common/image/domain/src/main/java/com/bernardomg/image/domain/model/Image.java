/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.domain.model;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.security.domain.audit.model.AuditDetails;

public record Image(Long number, String name, String description, String key, String mediaType, long size,
        AuditDetails audit) {

    public Image(final Long number, final String name, final String description, final String key,
            final String mediaType, final long size, final AuditDetails audit) {
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(name, "Name can't be null");
        Objects.requireNonNull(description, "Description can't be null");
        Objects.requireNonNull(key, "Key can't be null");
        Objects.requireNonNull(mediaType, "Media type can't be null");
        Objects.requireNonNull(audit, "Audit can't be null");

        this.number = number;
        this.name = StringUtils.trim(name);
        this.description = StringUtils.trim(description);
        this.key = StringUtils.trim(key);
        this.mediaType = StringUtils.trim(mediaType);
        this.size = size;
        this.audit = audit;
    }

    public Image(final Long number, final String name, final String description, final String key,
            final String mediaType, final long size) {
        this(number, name, description, key, mediaType, size, new AuditDetails());
    }

}
