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

import java.util.Collections;
import java.util.Iterator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DefaultPageIterable<T> implements PageIterable<T> {

    /**
     * Actual content.
     */
    @NonNull
    private Iterable<T> content        = Collections.emptyList();

    /**
     * Number of elements in the page.
     */
    private Integer     elementsInPage = 0;

    /**
     * Flags this is as the first page.
     */
    private Boolean     first          = false;

    /**
     * Flags this is as the last page.
     */
    private Boolean     last           = false;

    /**
     * Number of this page.
     */
    private Integer     pageNumber     = 0;

    /**
     * Size of this page.
     */
    private Integer     size           = 0;

    /**
     * Total number of elements among all the pages.
     */
    private Long        totalElements  = 0L;

    /**
     * Total number of pages.
     */
    private Integer     totalPages     = 0;

    @Override
    public Boolean isFirst() {
        return first;
    }

    @Override
    public Boolean isLast() {
        return last;
    }

    @Override
    public final Iterator<T> iterator() {
        return content.iterator();
    }

}
