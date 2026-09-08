/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.inbound.jpa.model;

import com.bernardomg.image.domain.model.Image;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditMetadata;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditUserEntity;
import com.bernardomg.security.domain.audit.model.AuditDetails;
import com.bernardomg.security.domain.audit.model.AuditDetails.AuditUser;

public final class ImageEntityMapper {

    public static Image toDomain(final ImageEntity entity) {
        return new Image(entity.getNumber(), entity.getName(), entity.getDescription(), entity.getKey(),
            entity.getMediaType(), entity.getSize(), toDomain(entity.getAudit()));
    }

    public static ImageEntity toEntity(final Image image) {
        final ImageEntity entity;

        entity = new ImageEntity();
        entity.setNumber(image.number());
        entity.setName(image.name());
        entity.setDescription(image.description());
        entity.setKey(image.key());
        entity.setMediaType(image.mediaType());
        entity.setSize(image.size());

        return entity;
    }

    private static AuditUser toAuditDomain(final AuditUserEntity user) {
        final AuditUser auditUser;

        if (user == null) {
            auditUser = null;
        } else {
            auditUser = new AuditUser(user.getEmail(), user.getUsername(), user.getName());
        }

        return auditUser;
    }

    private static AuditDetails toDomain(final AuditMetadata audit) {
        final AuditDetails auditDetails;

        if (audit == null) {
            auditDetails = new AuditDetails();
        } else {
            auditDetails = new AuditDetails(audit.getCreatedAt(), toAuditDomain(audit.getCreatedBy()),
                audit.getUpdatedAt(), toAuditDomain(audit.getUpdatedBy()));
        }

        return auditDetails;
    }

    private ImageEntityMapper() {
        super();
    }
}
