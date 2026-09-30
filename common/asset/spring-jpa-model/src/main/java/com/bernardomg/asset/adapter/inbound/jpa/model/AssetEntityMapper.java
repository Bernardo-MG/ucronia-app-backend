
package com.bernardomg.asset.adapter.inbound.jpa.model;

import java.util.Optional;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditMetadata;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditUserEntity;
import com.bernardomg.security.domain.audit.model.AuditDetails;
import com.bernardomg.security.domain.audit.model.AuditDetails.AuditUser;

public final class AssetEntityMapper {

    public static Asset toDomain(final AssetEntity entity) {
        final Optional<Long> folder;

        if (entity.getFolder() == null) {
            folder = Optional.empty();
        } else {
            folder = Optional.ofNullable(entity.getFolder()
                .getNumber());
        }

        return new Asset(entity.getType(), entity.getNumber(), entity.getName(), entity.getDescription(),
            entity.getKey(), entity.getMediaType(), entity.getSize(), entity.isPublicAccess(), folder,
            toDomain(entity.getAudit()));
    }

    public static AssetEntity toEntity(final Asset asset) {
        final AssetEntity entity;

        entity = new AssetEntity();
        entity.setType(asset.type());
        entity.setNumber(asset.number());
        entity.setName(asset.name());
        entity.setDescription(asset.description());
        entity.setKey(asset.key());
        entity.setMediaType(asset.mediaType());
        entity.setSize(asset.size());
        entity.setPublicAccess(asset.publicAccess());

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

    private AssetEntityMapper() {
        super();
    }
}
