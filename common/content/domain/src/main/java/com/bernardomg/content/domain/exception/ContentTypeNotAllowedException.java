
package com.bernardomg.content.domain.exception;

public final class ContentTypeNotAllowedException extends RuntimeException {

    private static final long serialVersionUID = -574209136400005901L;

    private final String      mediaType;

    public ContentTypeNotAllowedException(final String contentMediaType) {
        super(String.format("Content type %s is not allowed", contentMediaType));

        mediaType = contentMediaType;
    }

    public final String getMediaType() {
        return mediaType;
    }

}
