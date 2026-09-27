
package com.bernardomg.content.domain.key;

import java.util.UUID;

public final class UuidContentKeyGenerator implements ContentKeyGenerator {

    public UuidContentKeyGenerator() {
        super();
    }

    @Override
    public final String generate(final String namespace) {
        return "%s/%s".formatted(namespace, UUID.randomUUID());
    }

}
