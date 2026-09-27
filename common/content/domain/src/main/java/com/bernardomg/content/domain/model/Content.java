
package com.bernardomg.content.domain.model;

import java.io.InputStream;
import java.util.Objects;

public record Content(InputStream data, long size, String mediaType) {

    public Content {
        Objects.requireNonNull(data, "Data can't be null");
        Objects.requireNonNull(mediaType, "Media type can't be null");

        if (size < 0) {
            throw new IllegalArgumentException("Size can't be negative");
        }
    }

}
