
package com.bernardomg.image.test.configuration.factory;

import java.io.ByteArrayInputStream;

import com.bernardomg.image.domain.model.ImageContent;

public final class ImageContents {

    public static final ImageContent image() {
        return new ImageContent(new ByteArrayInputStream(ImageConstants.DATA), ImageConstants.DATA.length,
            ImageConstants.PNG_MEDIA_TYPE);
    }

    private ImageContents() {
        super();
    }

}
