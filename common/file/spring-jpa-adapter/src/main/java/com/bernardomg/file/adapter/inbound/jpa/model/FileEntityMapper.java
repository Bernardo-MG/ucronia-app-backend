
package com.bernardomg.file.adapter.inbound.jpa.model;

import java.util.Optional;

import com.bernardomg.file.domain.model.File;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditMetadata;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditUserEntity;
import com.bernardomg.security.domain.audit.model.AuditDetails;
import com.bernardomg.security.domain.audit.model.AuditDetails.AuditUser;

public final class FileEntityMapper {

    public static File toDomain(final FileEntity entity) {
        final Optional<Long> folder;

        if (entity.getFolder() == null) {
            folder = Optional.empty();
        } else {
            folder = Optional.ofNullable(entity.getFolder()
                .getNumber());
        }

        return new File(entity.getNumber(), entity.getName(), entity.getDescription(), entity.getKey(),
            entity.getMediaType(), entity.getSize(), entity.isPublicAccess(), folder, toDomain(entity.getAudit()));
    }

    public static FileEntity toEntity(final File file) {
        final FileEntity entity;

        entity = new FileEntity();
        entity.setNumber(file.number());
        entity.setName(file.name());
        entity.setDescription(file.description());
        entity.setKey(file.key());
        entity.setMediaType(file.mediaType());
        entity.setSize(file.size());
        entity.setPublicAccess(file.publicAccess());

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

    private FileEntityMapper() {
        super();
    }
}
