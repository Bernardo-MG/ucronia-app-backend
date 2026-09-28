/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.asset.domain.model.Asset;

public final class Images {

    public static Asset change() {
        return new Asset(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION,
            ImageConstants.CHANGE_KEY, ImageConstants.PNG_MEDIA_TYPE, ImageConstants.DATA.length);
    }

    public static Asset nameChange() {
        return new Asset(ImageConstants.NUMBER, ImageConstants.ALTERNATIVE_NAME, ImageConstants.DESCRIPTION,
            ImageConstants.KEY, ImageConstants.PNG_MEDIA_TYPE, ImageConstants.DATA.length);
    }

    public static Asset patch() {
        return new Asset(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION, "", "", 0);
    }

    public static Asset privateAccess() {
        return new Asset(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION, ImageConstants.KEY,
            ImageConstants.PNG_MEDIA_TYPE, ImageConstants.DATA.length, false, Optional.empty());
    }

    public static Asset privateAccessInFolder() {
        return new Asset(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION, ImageConstants.KEY,
            ImageConstants.PNG_MEDIA_TYPE, ImageConstants.DATA.length, false, Optional.of(ImageFolderConstants.NUMBER));
    }

    public static Asset publicAccess() {
        return new Asset(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION, ImageConstants.KEY,
            ImageConstants.PNG_MEDIA_TYPE, ImageConstants.DATA.length, true, Optional.empty());
    }

    public static Asset publicAccessInFolder() {
        return new Asset(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION, ImageConstants.KEY,
            ImageConstants.PNG_MEDIA_TYPE, ImageConstants.DATA.length, true, Optional.of(ImageFolderConstants.NUMBER));
    }

    private Images() {
        super();
    }
}
