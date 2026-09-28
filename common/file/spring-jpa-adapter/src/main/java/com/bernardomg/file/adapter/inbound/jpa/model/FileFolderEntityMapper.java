
package com.bernardomg.file.adapter.inbound.jpa.model;

import java.util.Optional;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetFolderEntity;
import com.bernardomg.file.domain.model.FileFolder;
import com.bernardomg.security.domain.audit.model.AuditDetails;

public final class FileFolderEntityMapper {

    public static FileFolder toDomain(final AssetFolderEntity entity) {
        final Optional<Long> parent;

        if (entity.getParent() == null) {
            parent = Optional.empty();
        } else {
            parent = Optional.of(entity.getParent()
                .getNumber());
        }

        return new FileFolder(entity.getNumber(), entity.getName(), parent, new AuditDetails());
    }

    private FileFolderEntityMapper() {
        super();
    }

}
