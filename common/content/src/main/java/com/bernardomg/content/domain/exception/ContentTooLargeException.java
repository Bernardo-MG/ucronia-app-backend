package com.bernardomg.content.domain.exception;

public final class ContentTooLargeException extends RuntimeException {

    private static final long serialVersionUID = -4437834631510388980L;

    private final long        maximumSize;

    private final long        size;

    public ContentTooLargeException(final long contentSize, final long maxSize) {
        super(String.format("Content size %d exceeds the maximum allowed size %d", contentSize, maxSize));

        size = contentSize;
        maximumSize = maxSize;
    }

    public final long getMaximumSize() {
        return maximumSize;
    }

    public final long getSize() {
        return size;
    }

}
