/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.mvc.pagination.model;

/**
 * Sorted data request.
 * <p>
 * Includes a flag to mark is this request is actually sorted, to ease disabled sorting.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface Sort {

    /**
     * Creates a {@code Sort} for the property and ascending direction.
     *
     * @param property
     *            property to sort
     * @return an ascending {@code Sort} for the arguments
     */
    public static Sort asc(final String property) {
        return new ImmutableSort(property, Direction.ASC);
    }

    /**
     * Creates a {@code Sort} for the property and descending direction.
     *
     * @param property
     *            property to sort
     * @return a descending {@code Sort} for the arguments
     */
    public static Sort desc(final String property) {
        return new ImmutableSort(property, Direction.DESC);
    }

    /**
     * Creates a {@code Sort} which represents disabled sorting.
     *
     * @return a disabled {@code Sort}
     */
    public static Sort disabled() {
        return DisabledSort.INSTANCE;
    }

    /**
     * Creates a {@code Sort} for the property and direction.
     *
     * @param property
     *            property to sort
     * @param direction
     *            sorting direction
     * @return a {@code Sort} for the arguments
     */
    public static Sort of(final String property, final Direction direction) {
        return new ImmutableSort(property, direction);
    }

    /**
     * Direction in which the data will be sorted.
     *
     * @return the direction for sorting
     */
    public Direction getDirection();

    /**
     * Property to sort.
     *
     * @return the property to sort
     */
    public String getProperty();

    /**
     * Flags if sorting should be applied. This makes it easier disabling sorting.
     *
     * @return {@code true} if this is sorted, {@code false} otherwise
     */
    public Boolean getSorted();

}
