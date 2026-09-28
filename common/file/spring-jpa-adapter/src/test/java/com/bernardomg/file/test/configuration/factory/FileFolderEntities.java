
package com.bernardomg.file.test.configuration.factory;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetFolderEntity;

public final class FileFolderEntities {

    public static AssetFolderEntity nameChange() {
        final AssetFolderEntity entity;

        entity = valid();
        entity.setName(FileFolderConstants.ALTERNATIVE_NAME);

        return entity;
    }

    public static AssetFolderEntity valid() {
        final AssetFolderEntity entity;

        entity = new AssetFolderEntity();
        entity.setNumber(FileFolderConstants.NUMBER);
        entity.setName(FileFolderConstants.NAME);

        return entity;
    }

    private FileFolderEntities() {
        super();
    }

}
