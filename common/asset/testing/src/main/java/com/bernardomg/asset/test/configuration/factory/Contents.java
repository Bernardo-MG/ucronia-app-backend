
package com.bernardomg.asset.test.configuration.factory;

import java.io.ByteArrayInputStream;

import com.bernardomg.content.domain.model.Content;

public final class Contents {

    public static final Content image() {
        return new Content(new ByteArrayInputStream(AssetConstants.DATA), AssetConstants.DATA.length,
            AssetConstants.PNG_MEDIA_TYPE);
    }

    private Contents() {
        super();
    }

}
