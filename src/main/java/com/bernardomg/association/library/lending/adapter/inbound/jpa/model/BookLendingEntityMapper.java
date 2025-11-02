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

import com.bernardomg.association.library.book.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.model.BookLending.Borrower;
import com.bernardomg.association.library.lending.domain.model.BookLending.LentBook;
import com.bernardomg.association.person.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.person.domain.model.ContactName;

/**
 * Author repository mapper.
 */
public final class BookLendingEntityMapper {

    public static final BookLending toDomain(final BookLendingEntity entity, final BookEntity bookEntity,
            final ContactEntity contactEntity) {
        final Borrower borrower;
        final LentBook lentBook;
        final Title    title;

        borrower = toDomain(contactEntity);
        title = new Title(bookEntity.getSupertitle(), bookEntity.getTitle(), bookEntity.getSubtitle());
        lentBook = new LentBook(bookEntity.getNumber(), title);
        return new BookLending(lentBook, borrower, entity.getLendingDate(), entity.getReturnDate());
    }

    public static final Borrower toDomain(final ContactEntity entity) {
        final ContactName name;

        name = new ContactName(entity.getFirstName(), entity.getLastName());
        return new Borrower(entity.getNumber(), name);
    }

    public static final BookLendingEntity toEntity(final BookLending domain, final BookEntity bookEntity,
            final ContactEntity contactEntity) {
        final BookLendingEntity entity;

        entity = new BookLendingEntity();
        entity.setBookId(bookEntity.getId());
        entity.setContactId(contactEntity.getId());
        entity.setLendingDate(domain.lendingDate());
        entity.setReturnDate(domain.returnDate());

        return entity;
    }

    private BookLendingEntityMapper() {
        super();
    }

}
