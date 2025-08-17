/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023-2025 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.data.web;

import java.util.Collection;
import java.util.List;

import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;

/**
 * Utilities to transform web params into {@link Sorting}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 * @deprecated move to the web library
 *
 */
public final class WebSorting {

    /**
     * Parses the params into a {@code Sorting}.
     *
     * @param sort
     *            params to parse
     * @return parsed {@code Sorting}
     */
    public static final Sorting toSorting(final List<String> sort) {
        final Collection<Property> properties;

        if (sort == null) {
            properties = List.of();
        } else {
            properties = sort.stream()
                .map(p -> p.split(","))
                .filter(p -> p.length >= 2)
                .map(WebSorting::toProperty)
                .toList();
        }

        return new Sorting(properties);
    }

    /**
     * Parses the parts into a {@code Property}.
     *
     * @param parts
     *            parts to parse
     * @return parsed {@code Property}
     */
    private static final Property toProperty(final String[] parts) {
        final String    propertyName;
        final String    directionName;
        final Direction direction;

        directionName = parts[1].trim()
            .toUpperCase();
        if ("desc".equalsIgnoreCase(directionName)) {
            direction = Direction.DESC;
        } else {
            direction = Direction.ASC;
        }

        propertyName = parts[0].trim();

        return new Property(propertyName, direction);
    }

}
