/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.asset.domain.model;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.security.domain.audit.model.AuditDetails;

public final class Asset {

    private final AuditDetails   audit;

    private final String         description;

    private final Optional<Long> folderNumber;

    private final String         key;

    private final String         mediaType;

    private final String         name;

    private final Long           number;

    private final boolean        publicAccess;

    private final long           size;

    private final AssetType      type;

    public Asset(final AssetType type, final Long number, final String name, final String description, final String key,
            final String mediaType, final long size) {
        this(type, number, name, description, key, mediaType, size, true, Optional.empty(), new AuditDetails());
    }

    public Asset(final AssetType type, final Long number, final String name, final String description, final String key,
            final String mediaType, final long size, final AuditDetails audit) {
        this(type, number, name, description, key, mediaType, size, true, Optional.empty(), audit);
    }

    public Asset(final AssetType type, final Long number, final String name, final String description, final String key,
            final String mediaType, final long size, final boolean publicAccess, final Optional<Long> folderNumber) {
        this(type, number, name, description, key, mediaType, size, publicAccess, folderNumber, new AuditDetails());
    }

    public Asset(final AssetType type, final Long number, final String name, final String description, final String key,
            final String mediaType, final long size, final boolean publicAccess, final Optional<Long> folderNumber,
            final AuditDetails audit) {
        this.type = Objects.requireNonNull(type, "Type can't be null");
        this.number = Objects.requireNonNull(number, "Number can't be null");
        this.name = StringUtils.trim(Objects.requireNonNull(name, "Name can't be null"));
        this.description = StringUtils.trim(Objects.requireNonNull(description, "Description can't be null"));
        this.key = StringUtils.trim(Objects.requireNonNull(key, "Key can't be null"));
        this.mediaType = StringUtils.trim(Objects.requireNonNull(mediaType, "Media type can't be null"));
        this.size = size;
        this.publicAccess = publicAccess;
        this.folderNumber = Objects.requireNonNull(folderNumber, "Folder number can't be null");
        this.audit = Objects.requireNonNull(audit, "Audit can't be null");
    }

    public Asset(final AssetType type, final Long number, final String name, final String description, final String key,
            final String mediaType, final long size, final Optional<Long> folderNumber) {
        this(type, number, name, description, key, mediaType, size, true, folderNumber, new AuditDetails());
    }

    public Asset(final AssetType type, final Long number, final String name, final String description, final String key,
            final String mediaType, final long size, final Optional<Long> folderNumber, final AuditDetails audit) {
        this(type, number, name, description, key, mediaType, size, true, folderNumber, audit);
    }

    public final AuditDetails audit() {
        return audit;
    }

    public final String description() {
        return description;
    }

    @Override
    public final boolean equals(final Object object) {
        final boolean equal;

        if (this == object) {
            equal = true;
        } else if ((object == null) || (getClass() != object.getClass())) {
            equal = false;
        } else {
            final Asset other;

            other = (Asset) object;
            equal = (size == other.size) && (publicAccess == other.publicAccess) && (type == other.type)
                    && Objects.equals(number, other.number) && Objects.equals(name, other.name)
                    && Objects.equals(description, other.description) && Objects.equals(key, other.key)
                    && Objects.equals(mediaType, other.mediaType) && Objects.equals(folderNumber, other.folderNumber)
                    && Objects.equals(audit, other.audit);
        }

        return equal;
    }

    public final Optional<Long> folderNumber() {
        return folderNumber;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(type, number, name, description, key, mediaType, size, publicAccess, folderNumber, audit);
    }

    public final String key() {
        return key;
    }

    public final String mediaType() {
        return mediaType;
    }

    public final String name() {
        return name;
    }

    public final Long number() {
        return number;
    }

    public final boolean publicAccess() {
        return publicAccess;
    }

    public final long size() {
        return size;
    }

    @Override
    public final String toString() {
        return getClass().getSimpleName() + " [type=" + type + ", number=" + number + ", name=" + name
                + ", description=" + description + ", key=" + key + ", mediaType=" + mediaType + ", size=" + size
                + ", publicAccess=" + publicAccess + ", folderNumber=" + folderNumber + ", audit=" + audit + "]";
    }

    public final AssetType type() {
        return type;
    }

}
