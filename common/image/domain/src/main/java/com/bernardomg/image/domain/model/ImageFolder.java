/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.domain.model;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.security.domain.audit.model.AuditDetails;

public record ImageFolder(Long number, String name, Optional<Long> parentNumber, AuditDetails audit) {

    public ImageFolder {
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(name, "Name can't be null");
        Objects.requireNonNull(parentNumber, "Parent number can't be null");
        Objects.requireNonNull(audit, "Audit can't be null");

        name = StringUtils.trim(name);
    }

    public ImageFolder(final Long number, final String name, final Optional<Long> parentNumber) {
        this(number, name, parentNumber, new AuditDetails());
    }

}
