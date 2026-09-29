
package com.bernardomg.asset.test.configuration.factory;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetEntity;
import com.bernardomg.asset.domain.model.AssetType;

public final class AssetEntities {

    public static AssetEntity nameChange() {
        final AssetEntity entity;

        entity = new AssetEntity();
        entity.setNumber(AssetConstants.NUMBER);
        entity.setName(AssetConstants.NAME);
        entity.setDescription(AssetConstants.DESCRIPTION);
        entity.setKey(AssetConstants.KEY);
        entity.setMediaType(AssetConstants.PDF_MEDIA_TYPE);
        entity.setSize((long) AssetConstants.DATA.length);
        entity.setName(AssetConstants.ALTERNATIVE_NAME);
        entity.setPublicAccess(true);
        entity.setType(AssetType.FILE);

        return entity;
    }

    public static AssetEntity publicAccess() {
        final AssetEntity entity;

        entity = new AssetEntity();
        entity.setNumber(AssetConstants.NUMBER);
        entity.setName(AssetConstants.NAME);
        entity.setDescription(AssetConstants.DESCRIPTION);
        entity.setKey(AssetConstants.KEY);
        entity.setMediaType(AssetConstants.PDF_MEDIA_TYPE);
        entity.setSize((long) AssetConstants.DATA.length);
        entity.setPublicAccess(true);
        entity.setType(AssetType.FILE);

        return entity;
    }

    private AssetEntities() {
        super();
    }
}
