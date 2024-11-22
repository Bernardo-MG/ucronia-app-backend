
package com.bernardomg.data.domain;

import java.util.Collection;
import java.util.List;

public record Sorting(Collection<Property> properties) {

    public static final Sorting unsorted() {
        return new Sorting(List.of());
    }

    public static final Sorting asc(final String property) {
        return new Sorting(List.of(Property.asc(property)));
    }

    public static final Sorting desc(final String property) {
        return new Sorting(List.of(Property.desc(property)));
    }

    public record Property(String name, Direction direction) {

        public static final Property asc(final String property) {
            return new Property(property, Direction.ASC);
        }

        public static final Property desc(final String property) {
            return new Property(property, Direction.DESC);
        }
    }

    public enum Direction {
        ASC, DESC
    }

}
