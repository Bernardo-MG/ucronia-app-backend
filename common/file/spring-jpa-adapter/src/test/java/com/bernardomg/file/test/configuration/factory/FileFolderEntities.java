
package com.bernardomg.file.test.configuration.factory;

import com.bernardomg.file.adapter.inbound.jpa.model.FileFolderEntity;

public final class FileFolderEntities {

    public static FileFolderEntity nameChange() {
        final FileFolderEntity entity;

        entity = valid();
        entity.setName(FileFolderConstants.ALTERNATIVE_NAME);

        return entity;
    }

    public static FileFolderEntity valid() {
        final FileFolderEntity entity;

        entity = new FileFolderEntity();
        entity.setNumber(FileFolderConstants.NUMBER);
        entity.setName(FileFolderConstants.NAME);

        return entity;
    }

    private FileFolderEntities() {
        super();
    }

}
