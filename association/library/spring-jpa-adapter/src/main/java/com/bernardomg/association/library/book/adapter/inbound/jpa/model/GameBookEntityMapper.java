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

package com.bernardomg.association.library.book.adapter.inbound.jpa.model;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.domain.model.BookLendingInfo;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.publisher.domain.model.Publisher;

/**
 * Author repository mapper.
 */
public final class GameBookEntityMapper {

    public static final GameBook toDomain(final GameBookEntity entity, final boolean lent,
            final Collection<BookLendingInfo> lendings) {
        final Collection<Publisher> publishers;
        final Optional<GameSystem>  gameSystem;
        final Optional<BookType>    bookType;
        final Collection<Donor>     donors;
        final Collection<Author>    authors;
        final Title                 title;
        final String                supertitle;
        final String                subtitle;
        final Optional<Donation>    donation;

        // Game system
        if (entity.getGameSystem() == null) {
            gameSystem = Optional.empty();
        } else {
            gameSystem = Optional.of(BookEntityMapper.toDomain(entity.getGameSystem()));
        }

        // Book type
        if (entity.getBookType() == null) {
            bookType = Optional.empty();
        } else {
            bookType = Optional.of(BookEntityMapper.toDomain(entity.getBookType()));
        }

        // Publishers
        if (entity.getPublishers() == null) {
            publishers = List.of();
        } else {
            publishers = entity.getPublishers()
                .stream()
                .map(BookEntityMapper::toDomain)
                .toList();
        }

        // Authors
        if (entity.getAuthors() == null) {
            authors = List.of();
        } else {
            authors = entity.getAuthors()
                .stream()
                .map(BookEntityMapper::toDomain)
                .toList();
        }

        // Donation
        if (entity.getDonors() == null) {
            donors = List.of();
        } else {
            donors = entity.getDonors()
                .stream()
                .map(BookEntityMapper::toDonorDomain)
                .toList();
        }
        if ((entity.getDonationDate() != null) && (!donors.isEmpty())) {
            donation = Optional.of(new Donation(entity.getDonationDate(), donors));
        } else if (entity.getDonationDate() != null) {
            donation = Optional.of(new Donation(entity.getDonationDate(), List.of()));
        } else if (!donors.isEmpty()) {
            donation = Optional.of(new Donation(null, donors));
        } else {
            donation = Optional.empty();
        }

        if (entity.getSupertitle() == null) {
            supertitle = "";
        } else {
            supertitle = entity.getSupertitle();
        }
        if (entity.getSubtitle() == null) {
            subtitle = "";
        } else {
            subtitle = entity.getSubtitle();
        }
        title = new Title(supertitle, entity.getTitle(), subtitle);

        return new GameBook(entity.getNumber(), title, entity.getIsbn(), entity.getLanguage(), entity.getPublishDate(),
            lent, authors, lendings, publishers, donation, bookType, gameSystem);
    }

    private GameBookEntityMapper() {
        super();
    }

}
