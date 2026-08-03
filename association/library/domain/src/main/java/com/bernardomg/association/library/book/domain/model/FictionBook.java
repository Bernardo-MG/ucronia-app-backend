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

package com.bernardomg.association.library.book.domain.model;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.security.domain.audit.model.AuditDetails;

public record FictionBook(long number, Title title, String isbn, String language, Optional<Instant> publishDate,
        boolean lent, Collection<Author> authors, Collection<BookLendingInfo> lendings,
        Collection<Publisher> publishers, Optional<Donation> donation, AuditDetails audit) {

    public FictionBook(final long number, final Title title, final String isbn, final String language,
            final Optional<Instant> publishDate, final boolean lent, final Collection<Author> authors,
            final Collection<BookLendingInfo> lendings, final Collection<Publisher> publishers,
            final Optional<Donation> donation) {
        this(number, title, isbn, language, publishDate, lent, authors, lendings, publishers, donation,
            new AuditDetails());
    }

    public FictionBook(final long number, final Title title, final String isbn, final String language,
            final Optional<Instant> publishDate, final boolean lent, final Collection<Author> authors,
            final Collection<BookLendingInfo> lendings, final Collection<Publisher> publishers,
            final Optional<Donation> donation, final AuditDetails audit) {
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(title, "Title can't be null");
        Objects.requireNonNull(isbn, "ISBN can't be null");
        Objects.requireNonNull(language, "Language can't be null");
        Objects.requireNonNull(publishDate, "Publish date can't be null");
        Objects.requireNonNull(lent, "Lent flag can't be null");
        Objects.requireNonNull(authors, "Authors can't be null");
        Objects.requireNonNull(lendings, "Lendings can't be null");
        Objects.requireNonNull(publishers, "Publishers can't be null");
        Objects.requireNonNull(donation, "Donation can't be null");
        Objects.requireNonNull(audit, "Audit can't be null");

        this.number = number;
        this.title = title;
        this.isbn = StringUtils.trim(isbn);
        this.language = StringUtils.trim(language);
        this.publishDate = publishDate;
        this.lent = lent;
        this.authors = List.copyOf(authors);
        this.lendings = List.copyOf(lendings);
        this.publishers = List.copyOf(publishers);
        this.donation = donation;
        this.audit = audit;
    }

}
