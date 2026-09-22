
package com.bernardomg.image.domain.model;

import java.io.InputStream;
import java.util.Objects;

public record ImageContent(InputStream data, long size, String mediaType) {

    public ImageContent {
        Objects.requireNonNull(data, "Data can't be null");
        Objects.requireNonNull(size, "Size can't be null");
        Objects.requireNonNull(mediaType, "Media type can't be null");

        if (size < 0) {
            throw new IllegalArgumentException("Size can't be negative");
        }
    }

}
