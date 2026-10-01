
package com.bernardomg.asset.test.configuration.factory;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetFolderEntity;

public final class AssettFolderEntities {

    public static AssetFolderEntity nameChange() {
        final AssetFolderEntity entity;

        entity = valid();
        entity.setName(AssetFolderConstants.ALTERNATIVE_NAME);

        return entity;
    }

    public static AssetFolderEntity valid() {
        final AssetFolderEntity entity;

        entity = new AssetFolderEntity();
        entity.setNumber(AssetFolderConstants.NUMBER);
        entity.setName(AssetFolderConstants.NAME);

        return entity;
    }

    private AssettFolderEntities() {
        super();
    }

}
