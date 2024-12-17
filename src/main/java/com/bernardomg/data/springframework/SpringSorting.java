
package com.bernardomg.data.springframework;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;

public final class SpringSorting {

    public static final Sort toSort(final Sorting sorting) {
        return Sort.by(sorting.properties()
            .stream()
            .map(SpringSorting::toOrder)
            .toList());
    }

    private static final Order toOrder(final Property property) {
        final Order order;

        if (Direction.ASC.equals(property.direction())) {
            order = Order.asc(property.name());
        } else {
            order = Order.desc(property.name());
        }

        return order;
    }

}
