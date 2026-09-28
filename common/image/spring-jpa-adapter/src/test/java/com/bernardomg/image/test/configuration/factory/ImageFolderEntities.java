
package com.bernardomg.image.test.configuration.factory;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetFolderEntity;

public final class ImageFolderEntities {

    public static AssetFolderEntity nameChange() {
        final AssetFolderEntity entity;

        entity = valid();
        entity.setName(ImageFolderConstants.ALTERNATIVE_NAME);

        return entity;
    }

    public static AssetFolderEntity valid() {
        final AssetFolderEntity entity;

        entity = new AssetFolderEntity();
        entity.setNumber(ImageFolderConstants.NUMBER);
        entity.setName(ImageFolderConstants.NAME);

        return entity;
    }

    private ImageFolderEntities() {
        super();
    }

}
