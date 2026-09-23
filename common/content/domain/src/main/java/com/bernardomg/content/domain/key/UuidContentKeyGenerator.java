
package com.bernardomg.content.domain.key;

import java.util.Objects;
import java.util.UUID;

public final class UuidContentKeyGenerator implements ContentKeyGenerator {

    @Override
    public final String generate(final String namespace) {
        Objects.requireNonNull(namespace, "Namespace can't be null");
        if (namespace.isBlank()) {
            throw new IllegalArgumentException("Namespace can't be blank");
        }
        return "%s/%s".formatted(namespace, UUID.randomUUID());
    }

}
