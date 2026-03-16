
package com.bernardomg.test.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new JavaTimeModule());
    }

    public static final String toJson(final Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JsonUtils() {
        super();
    }

}
