
package com.bernardomg.asset.adapter.inbound.jpa.model;

import java.util.Optional;

import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.security.domain.audit.model.AuditDetails;

public final class AssetFolderEntityMapper {

    public static AssetFolder toDomain(final AssetFolderEntity entity) {
        final Optional<Long> parent;

        if (entity.getParent() == null) {
            parent = Optional.empty();
        } else {
            parent = Optional.of(entity.getParent()
                .getNumber());
        }

        return new AssetFolder(entity.getNumber(), entity.getName(), parent, new AuditDetails());
    }

    private AssetFolderEntityMapper() {
        super();
    }

}
