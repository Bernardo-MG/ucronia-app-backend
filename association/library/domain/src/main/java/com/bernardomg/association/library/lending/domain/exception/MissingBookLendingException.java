/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

package com.bernardomg.association.library.lending.domain.exception;

/**
 * Missing book lending exception.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class MissingBookLendingException extends RuntimeException {

    private static final long serialVersionUID = 8880489951078991743L;

    private final Long        bookNumber;

    private final Long        borrower;

    public MissingBookLendingException(final long bookNumber, final long borrower) {
        super(String.format("Missing book lending for book %d and borrower %d", bookNumber, borrower));

        this.bookNumber = bookNumber;
        this.borrower = borrower;
    }

    public Long getBookNumber() {
        return bookNumber;
    }

    public Long getBorrower() {
        return borrower;
    }

}
