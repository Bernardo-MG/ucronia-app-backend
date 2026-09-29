
package com.bernardomg.file.test.configuration.factory;

import java.io.ByteArrayInputStream;

import com.bernardomg.asset.domain.model.Content;

public final class Contents {

    public static final Content file() {
        return new Content(new ByteArrayInputStream(FileConstants.DATA), FileConstants.DATA.length,
            FileConstants.PDF_MEDIA_TYPE);
    }

    private Contents() {
        super();
    }

}
