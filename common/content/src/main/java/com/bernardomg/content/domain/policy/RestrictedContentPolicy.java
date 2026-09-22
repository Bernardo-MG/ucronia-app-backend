
package com.bernardomg.content.domain.policy;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.bernardomg.content.domain.exception.ContentEmptyException;
import com.bernardomg.content.domain.exception.ContentTooLargeException;
import com.bernardomg.content.domain.exception.ContentTypeNotAllowedException;

public final class RestrictedContentPolicy implements ContentPolicy {

    private final Set<String> allowedMediaTypes;

    private final long        maximumSize;

    public RestrictedContentPolicy(final long maxSize, final Set<String> mediaTypes) {
        Objects.requireNonNull(maxSize, "Max size types can't be null");
        Objects.requireNonNull(mediaTypes, "Allowed media types can't be null");

        if (maxSize < 1) {
            throw new IllegalArgumentException("Maximum size should be greater than zero");
        }
        if (mediaTypes.isEmpty()) {
            throw new IllegalArgumentException("Allowed media types can't be empty");
        }

        maximumSize = maxSize;
        allowedMediaTypes = mediaTypes.stream()
            .map(this::normalizeMediaType)
            .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public final void validate(final long size, final String mediaType) {
        final String normalizedMediaType;

        if (size <= 0) {
            throw new ContentEmptyException();
        }
        if (size > maximumSize) {
            throw new ContentTooLargeException(size, maximumSize);
        }

        normalizedMediaType = normalizeMediaType(mediaType);
        if (!allowedMediaTypes.contains(normalizedMediaType)) {
            throw new ContentTypeNotAllowedException(normalizedMediaType);
        }
    }

    private final String normalizeMediaType(final String mediaType) {
        final int    parameterSeparator;
        final String result;

        parameterSeparator = mediaType.indexOf(';');
        if (parameterSeparator < 0) {
            result = mediaType.trim()
                .toLowerCase(Locale.ROOT);
        } else {
            result = mediaType.substring(0, parameterSeparator)
                .trim()
                .toLowerCase(Locale.ROOT);
        }

        return result;
    }

}
