/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.file.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.file.domain.model.File;

public final class Files {

    public static File change() {
        return new File(FileConstants.NUMBER, FileConstants.NAME, FileConstants.DESCRIPTION, FileConstants.CHANGE_KEY,
            FileConstants.PDF_MEDIA_TYPE, FileConstants.DATA.length);
    }

    public static File nameChange() {
        return new File(FileConstants.NUMBER, FileConstants.ALTERNATIVE_NAME, FileConstants.DESCRIPTION,
            FileConstants.KEY, FileConstants.PDF_MEDIA_TYPE, FileConstants.DATA.length);
    }

    public static File patch() {
        return new File(FileConstants.NUMBER, FileConstants.NAME, FileConstants.DESCRIPTION, "", "", 0);
    }

    public static File privateAccess() {
        return new File(FileConstants.NUMBER, FileConstants.NAME, FileConstants.DESCRIPTION, FileConstants.KEY,
            FileConstants.PDF_MEDIA_TYPE, FileConstants.DATA.length, false, Optional.empty());
    }

    public static File privateAccessInFolder() {
        return new File(FileConstants.NUMBER, FileConstants.NAME, FileConstants.DESCRIPTION, FileConstants.KEY,
            FileConstants.PDF_MEDIA_TYPE, FileConstants.DATA.length, false, Optional.of(FileFolderConstants.NUMBER));
    }

    public static File publicAccess() {
        return new File(FileConstants.NUMBER, FileConstants.NAME, FileConstants.DESCRIPTION, FileConstants.KEY,
            FileConstants.PDF_MEDIA_TYPE, FileConstants.DATA.length, true, Optional.empty());
    }

    public static File publicAccessInFolder() {
        return new File(FileConstants.NUMBER, FileConstants.NAME, FileConstants.DESCRIPTION, FileConstants.KEY,
            FileConstants.PDF_MEDIA_TYPE, FileConstants.DATA.length, true, Optional.of(FileFolderConstants.NUMBER));
    }

    private Files() {
        super();
    }
}
