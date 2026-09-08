/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.configuration.factory;

import com.bernardomg.image.domain.model.Image;

public final class Images {

    public static Image nameChange() {
        return new Image(ImageConstants.NUMBER, ImageConstants.ALTERNATIVE_NAME, ImageConstants.DESCRIPTION,
            ImageConstants.KEY, ImageConstants.MEDIA_TYPE, ImageConstants.DATA.length);
    }

    public static Image valid() {
        return new Image(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION, ImageConstants.KEY,
            ImageConstants.MEDIA_TYPE, ImageConstants.DATA.length);
    }

    private Images() {
        super();
    }
}
