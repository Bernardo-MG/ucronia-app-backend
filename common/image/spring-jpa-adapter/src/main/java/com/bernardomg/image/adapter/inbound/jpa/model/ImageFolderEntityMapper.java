/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.inbound.jpa.model;

import java.util.Optional;

import com.bernardomg.image.domain.model.ImageFolder;
import com.bernardomg.security.domain.audit.model.AuditDetails;

public final class ImageFolderEntityMapper {

    public static ImageFolder toDomain(final ImageFolderEntity entity) {
        final Optional<Long> parent;

        if (entity.getParent() == null) {
            parent = Optional.empty();
        } else {
            parent = Optional.of(entity.getParent()
                .getNumber());
        }

        return new ImageFolder(entity.getNumber(), entity.getName(), parent, new AuditDetails());
    }

    private ImageFolderEntityMapper() {
        super();
    }

}
