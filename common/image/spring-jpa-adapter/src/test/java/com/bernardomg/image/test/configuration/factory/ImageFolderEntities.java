
package com.bernardomg.image.test.configuration.factory;

import com.bernardomg.image.adapter.inbound.jpa.model.ImageFolderEntity;

public final class ImageFolderEntities {

    public static ImageFolderEntity nameChange() {
        final ImageFolderEntity entity;

        entity = valid();
        entity.setName(ImageFolderConstants.ALTERNATIVE_NAME);

        return entity;
    }

    public static ImageFolderEntity valid() {
        final ImageFolderEntity entity;

        entity = new ImageFolderEntity();
        entity.setNumber(ImageFolderConstants.NUMBER);
        entity.setName(ImageFolderConstants.NAME);

        return entity;
    }

    private ImageFolderEntities() {
        super();
    }

}
