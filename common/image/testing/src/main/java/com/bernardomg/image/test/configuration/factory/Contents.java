
package com.bernardomg.image.test.configuration.factory;

import java.io.ByteArrayInputStream;

import com.bernardomg.content.domain.model.Content;

public final class Contents {

    public static final Content image() {
        return new Content(new ByteArrayInputStream(ImageConstants.DATA), ImageConstants.DATA.length,
            ImageConstants.PNG_MEDIA_TYPE);
    }

    private Contents() {
        super();
    }

}
