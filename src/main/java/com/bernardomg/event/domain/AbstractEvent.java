
package com.bernardomg.event.domain;

import java.io.Serializable;
import java.time.Instant;

public abstract class AbstractEvent implements Serializable {

    private static final long serialVersionUID = 4530889924666987059L;

    private transient Object  source;

    private final Instant     timestamp;

    public AbstractEvent(final Object src) {
        super();

        source = src;
        timestamp = Instant.now();
    }

    public final Object getSource() {
        return source;
    }

    public final Instant getTimestamp() {
        return timestamp;
    }

}
