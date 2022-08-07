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

package com.bernardomg.association.pagination.model;

/**
 * Paginated data request.
 * <p>
 * Includes a flag to mark is this request is actually paged, to ease disabled pagination.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface Pagination {

    /**
     * Default page size.
     */
    Integer DEFAULT_SIZE = 20;

    /**
     * Creates a {@code Pagination} which represents disabled pagination.
     *
     * @return a disabled {@code Pagination}
     */
    public static Pagination disabled() {
        return DisabledPagination.INSTANCE;
    }

    /**
     * Creates a {@code Pagination} which represents the first page.
     *
     * @return a {@code Pagination} for the first page
     */
    public static Pagination first() {
        return of(0);
    }

    /**
     * Creates a {@code Pagination} for the received page and default size.
     *
     * @param page
     *            zero-based page number
     * @return {@code Pagination} for the page and size
     */
    public static Pagination of(final Integer page) {
        return new ImmutablePagination(page);
    }

    /**
     * Creates a {@code Pagination} for the received page and size.
     *
     * @param page
     *            zero-based page number
     * @param size
     *            page size
     * @return {@code Pagination} for the page and size
     */
    public static Pagination of(final Integer page, final Integer size) {
        return new ImmutablePagination(page, size);
    }

    /**
     * Page index to read.
     *
     * @return the page index
     */
    public Integer getPage();

    /**
     * Flags if pagination should be applied. This makes it easier disabling pagination.
     *
     * @return {@code true} if this is paged, {@code false} otherwise
     */
    public Boolean getPaged();

    /**
     * Number of elements to read per page.
     *
     * @return the page size
     */
    public Integer getSize();

}
