
package com.bernardomg.image.test.configuration.factory;

import com.bernardomg.asset.adapter.inbound.jpa.model.AssetEntity;

public final class ImageEntities {

    public static AssetEntity nameChange() {
        final AssetEntity entity;

        entity = new AssetEntity();
        entity.setNumber(ImageConstants.NUMBER);
        entity.setName(ImageConstants.NAME);
        entity.setDescription(ImageConstants.DESCRIPTION);
        entity.setKey(ImageConstants.KEY);
        entity.setMediaType(ImageConstants.PNG_MEDIA_TYPE);
        entity.setSize((long) ImageConstants.DATA.length);
        entity.setName(ImageConstants.ALTERNATIVE_NAME);
        entity.setPublicAccess(true);

        return entity;
    }

    public static AssetEntity publicAccess() {
        final AssetEntity entity;

        entity = new AssetEntity();
        entity.setNumber(ImageConstants.NUMBER);
        entity.setName(ImageConstants.NAME);
        entity.setDescription(ImageConstants.DESCRIPTION);
        entity.setKey(ImageConstants.KEY);
        entity.setMediaType(ImageConstants.PNG_MEDIA_TYPE);
        entity.setSize((long) ImageConstants.DATA.length);
        entity.setPublicAccess(true);

        return entity;
    }

    private ImageEntities() {
        super();
    }
}
