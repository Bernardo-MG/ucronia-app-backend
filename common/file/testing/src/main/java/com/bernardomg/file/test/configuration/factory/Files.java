/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.file.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.asset.domain.model.Asset;

public final class Files {

    public static Asset change() {
        return new Asset(FileConstants.NUMBER, FileConstants.NAME, FileConstants.DESCRIPTION, FileConstants.CHANGE_KEY,
            FileConstants.PDF_MEDIA_TYPE, FileConstants.DATA.length);
    }

    public static Asset nameChange() {
        return new Asset(FileConstants.NUMBER, FileConstants.ALTERNATIVE_NAME, FileConstants.DESCRIPTION,
            FileConstants.KEY, FileConstants.PDF_MEDIA_TYPE, FileConstants.DATA.length);
    }

    public static Asset patch() {
        return new Asset(FileConstants.NUMBER, FileConstants.NAME, FileConstants.DESCRIPTION, "", "", 0);
    }

    public static Asset privateAccess() {
        return new Asset(FileConstants.NUMBER, FileConstants.NAME, FileConstants.DESCRIPTION, FileConstants.KEY,
            FileConstants.PDF_MEDIA_TYPE, FileConstants.DATA.length, false, Optional.empty());
    }

    public static Asset privateAccessInFolder() {
        return new Asset(FileConstants.NUMBER, FileConstants.NAME, FileConstants.DESCRIPTION, FileConstants.KEY,
            FileConstants.PDF_MEDIA_TYPE, FileConstants.DATA.length, false, Optional.of(FileFolderConstants.NUMBER));
    }

    public static Asset publicAccess() {
        return new Asset(FileConstants.NUMBER, FileConstants.NAME, FileConstants.DESCRIPTION, FileConstants.KEY,
            FileConstants.PDF_MEDIA_TYPE, FileConstants.DATA.length, true, Optional.empty());
    }

    public static Asset publicAccessInFolder() {
        return new Asset(FileConstants.NUMBER, FileConstants.NAME, FileConstants.DESCRIPTION, FileConstants.KEY,
            FileConstants.PDF_MEDIA_TYPE, FileConstants.DATA.length, true, Optional.of(FileFolderConstants.NUMBER));
    }

    private Files() {
        super();
    }
}
