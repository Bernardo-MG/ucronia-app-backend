
package com.bernardomg.image.test.configuration.factory;

import com.bernardomg.image.adapter.inbound.jpa.model.ImageEntity;

public final class ImageEntities {

    public static ImageEntity nameChange() {
        final ImageEntity entity;

        entity = new ImageEntity();
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

    public static ImageEntity publicAccess() {
        final ImageEntity entity;

        entity = new ImageEntity();
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
