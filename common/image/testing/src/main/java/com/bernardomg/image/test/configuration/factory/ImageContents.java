
package com.bernardomg.image.test.configuration.factory;

import com.bernardomg.image.domain.model.ImageContent;

public final class ImageContents {

    public static final ImageContent image() {
        return new ImageContent(ImageConstants.DATA, ImageConstants.PNG_MEDIA_TYPE);
    }

    private ImageContents() {
        super();
    }

}
