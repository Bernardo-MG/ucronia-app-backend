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

package com.bernardomg.association.library.lending.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "BookLending")
@IdClass(BookLendingId.class)
@Table(schema = "inventory", name = "book_lendings")
public class BookLendingEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Id
    @Column(name = "book_id", nullable = false)
    private Long              bookId;

    @Id
    @Column(name = "lending_date", nullable = false)
    private Instant           lendingDate;

    @Id
    @Column(name = "person_id", nullable = false)
    private Long              personId;

    @Column(name = "return_date", nullable = false)
    private Instant           returnDate;

    public Long getBookId() {
        return bookId;
    }

    public Instant getLendingDate() {
        return lendingDate;
    }

    public Long getPersonId() {
        return personId;
    }

    public Instant getReturnDate() {
        return returnDate;
    }

    public void setBookId(final Long bookId) {
        this.bookId = bookId;
    }

    public void setLendingDate(final Instant lendingDate) {
        this.lendingDate = lendingDate;
    }

    public void setPersonId(final Long personId) {
        this.personId = personId;
    }

    public void setReturnDate(final Instant returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BookLendingEntity [bookId=" + bookId + ", lendingDate=" + lendingDate + ", personId=" + personId
                + ", returnDate=" + returnDate + "]";
    }

}
