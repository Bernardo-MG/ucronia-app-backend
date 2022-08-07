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

package com.bernardomg.pagination.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Disabled paginated data request. This serves as a null object to disable pagination.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Data
public final class DisabledPagination implements Pagination {

    /**
     * Singleton for disabled pagination.
     */
    public static final Pagination INSTANCE = new DisabledPagination();

    /**
     * Default page.
     */
    @NonNull
    private final Integer    page     = -1;

    /**
     * Disabled pagination flag.
     */
    @NonNull
    private final Boolean    paged    = false;

    /**
     * Default size.
     */
    @NonNull
    private final Integer    size     = -1;

}
