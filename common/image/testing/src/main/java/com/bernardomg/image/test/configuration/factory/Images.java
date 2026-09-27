/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.image.domain.model.Image;

public final class Images {

    public static Image change() {
        return new Image(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION,
            ImageConstants.CHANGE_KEY, ImageConstants.PNG_MEDIA_TYPE, ImageConstants.DATA.length);
    }

    public static Image nameChange() {
        return new Image(ImageConstants.NUMBER, ImageConstants.ALTERNATIVE_NAME, ImageConstants.DESCRIPTION,
            ImageConstants.KEY, ImageConstants.PNG_MEDIA_TYPE, ImageConstants.DATA.length);
    }

    public static Image patch() {
        return new Image(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION, "", "", 0);
    }

    public static Image privateAccess() {
        return new Image(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION, ImageConstants.KEY,
            ImageConstants.PNG_MEDIA_TYPE, ImageConstants.DATA.length, false, Optional.empty());
    }

    public static Image publicAccess() {
        return new Image(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION, ImageConstants.KEY,
            ImageConstants.PNG_MEDIA_TYPE, ImageConstants.DATA.length, true, Optional.empty());
    }

    public static Image privateAccessInFolder() {
        return new Image(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION, ImageConstants.KEY,
            ImageConstants.PNG_MEDIA_TYPE, ImageConstants.DATA.length, false, Optional.of(ImageFolderConstants.NUMBER));
    }

    public static Image publicAccessInFolder() {
        return new Image(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION, ImageConstants.KEY,
            ImageConstants.PNG_MEDIA_TYPE, ImageConstants.DATA.length, true, Optional.of(ImageFolderConstants.NUMBER));
    }

    private Images() {
        super();
    }
}
