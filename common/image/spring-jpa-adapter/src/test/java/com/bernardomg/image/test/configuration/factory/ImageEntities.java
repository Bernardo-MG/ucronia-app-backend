/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.configuration.factory;

import com.bernardomg.image.adapter.inbound.jpa.model.ImageEntity;

public final class ImageEntities {

    public static ImageEntity nameChange() {
        final ImageEntity entity = valid();
        entity.setName(ImageConstants.ALTERNATIVE_NAME);
        return entity;
    }

    public static ImageEntity valid() {
        final ImageEntity entity;

        entity = new ImageEntity();
        entity.setNumber(ImageConstants.NUMBER);
        entity.setName(ImageConstants.NAME);
        entity.setDescription(ImageConstants.DESCRIPTION);
        entity.setKey(ImageConstants.KEY);
        entity.setMediaType(ImageConstants.MEDIA_TYPE);
        entity.setSize((long) ImageConstants.DATA.length);
        return entity;
    }

    private ImageEntities() {
        super();
    }
}
