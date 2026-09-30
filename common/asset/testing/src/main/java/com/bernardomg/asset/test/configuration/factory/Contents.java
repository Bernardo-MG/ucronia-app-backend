
package com.bernardomg.asset.test.configuration.factory;

import java.io.ByteArrayInputStream;

import com.bernardomg.asset.domain.model.Content;

public final class Contents {

    public static final Content pdf() {
        return new Content(new ByteArrayInputStream(AssetConstants.DATA), AssetConstants.DATA.length,
            AssetConstants.PDF_MEDIA_TYPE);
    }

    private Contents() {
        super();
    }

}
