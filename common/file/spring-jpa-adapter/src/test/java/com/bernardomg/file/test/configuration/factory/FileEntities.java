
package com.bernardomg.file.test.configuration.factory;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetEntity;

public final class FileEntities {

    public static AssetEntity nameChange() {
        final AssetEntity entity;

        entity = new AssetEntity();
        entity.setNumber(FileConstants.NUMBER);
        entity.setName(FileConstants.NAME);
        entity.setDescription(FileConstants.DESCRIPTION);
        entity.setKey(FileConstants.KEY);
        entity.setMediaType(FileConstants.PDF_MEDIA_TYPE);
        entity.setSize((long) FileConstants.DATA.length);
        entity.setName(FileConstants.ALTERNATIVE_NAME);
        entity.setPublicAccess(true);

        return entity;
    }

    public static AssetEntity publicAccess() {
        final AssetEntity entity;

        entity = new AssetEntity();
        entity.setNumber(FileConstants.NUMBER);
        entity.setName(FileConstants.NAME);
        entity.setDescription(FileConstants.DESCRIPTION);
        entity.setKey(FileConstants.KEY);
        entity.setMediaType(FileConstants.PDF_MEDIA_TYPE);
        entity.setSize((long) FileConstants.DATA.length);
        entity.setPublicAccess(true);

        return entity;
    }

    private FileEntities() {
        super();
    }
}
