/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.asset.domain.model;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.security.domain.audit.model.AuditDetails;

public final class AssetFolder {

    private final AuditDetails   audit;

    private final String         name;

    private final Long           number;

    private final Optional<Long> parentNumber;

    public AssetFolder(final Long number, final String name, final Optional<Long> parentNumber) {
        this(number, name, parentNumber, new AuditDetails());
    }

    public AssetFolder(final Long number, final String name, final Optional<Long> parentNumber,
            final AuditDetails audit) {
        this.number = Objects.requireNonNull(number, "Number can't be null");
        this.name = StringUtils.trim(Objects.requireNonNull(name, "Name can't be null"));
        this.parentNumber = Objects.requireNonNull(parentNumber, "Parent number can't be null");
        this.audit = Objects.requireNonNull(audit, "Audit can't be null");
    }

    public final AuditDetails audit() {
        return audit;
    }

    @Override
    public final boolean equals(final Object object) {
        final boolean equal;

        if (this == object) {
            equal = true;
        } else if ((object == null) || (getClass() != object.getClass())) {
            equal = false;
        } else {
            final AssetFolder other;

            other = (AssetFolder) object;
            equal = Objects.equals(number, other.number) && Objects.equals(name, other.name)
                    && Objects.equals(parentNumber, other.parentNumber) && Objects.equals(audit, other.audit);
        }

        return equal;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(number, name, parentNumber, audit);
    }

    public final String name() {
        return name;
    }

    public final Long number() {
        return number;
    }

    public final Optional<Long> parentNumber() {
        return parentNumber;
    }

    @Override
    public final String toString() {
        return getClass().getSimpleName() + " [number=" + number + ", name=" + name + ", parentNumber=" + parentNumber
                + ", audit=" + audit + "]";
    }

}
