
package com.bernardomg.file.test.configuration.factory;

import com.bernardomg.file.adapter.inbound.jpa.model.FileEntity;

public final class FileEntities {

    public static FileEntity nameChange() {
        final FileEntity entity;

        entity = new FileEntity();
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

    public static FileEntity publicAccess() {
        final FileEntity entity;

        entity = new FileEntity();
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
