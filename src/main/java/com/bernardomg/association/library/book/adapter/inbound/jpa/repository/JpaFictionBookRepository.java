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

package com.bernardomg.association.library.book.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.repository.ContactSpringRepository;
import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.BookEntityMapper;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.FictionBookEntity;
import com.bernardomg.association.library.book.domain.model.BookLendingInfo;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.BookLendingSpringRepository;
import com.bernardomg.association.library.lending.domain.model.BookLending.Borrower;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.PublisherSpringRepository;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;
import com.bernardomg.data.springframework.SpringSorting;

@Repository
@Transactional
public final class JpaFictionBookRepository implements FictionBookRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaFictionBookRepository.class);

    private final AuthorSpringRepository      authorSpringRepository;

    private final BookLendingSpringRepository bookLendingSpringRepository;

    private final FictionBookSpringRepository bookSpringRepository;

    private final ContactSpringRepository     contactSpringRepository;

    private final PublisherSpringRepository   publisherSpringRepository;

    public JpaFictionBookRepository(final FictionBookSpringRepository bookSpringRepo,
            final AuthorSpringRepository authorSpringRepo, final PublisherSpringRepository publisherSpringRepo,
            final ContactSpringRepository contactSpringRepo, final BookLendingSpringRepository bookLendingSpringRepo) {
        super();

        bookSpringRepository = Objects.requireNonNull(bookSpringRepo);
        authorSpringRepository = Objects.requireNonNull(authorSpringRepo);
        publisherSpringRepository = Objects.requireNonNull(publisherSpringRepo);
        // TODO: maybe should be members only
        contactSpringRepository = Objects.requireNonNull(contactSpringRepo);
        bookLendingSpringRepository = Objects.requireNonNull(bookLendingSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting book {}", number);

        bookSpringRepository.deleteByNumber(number);

        log.debug("Deleted book {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if book {} exists", number);

        exists = bookSpringRepository.existsByNumber(number);

        log.debug("Book {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByIsbn(final String isbn) {
        final boolean exists;

        log.debug("Checking if book with ISBN {} exists", isbn);

        exists = bookSpringRepository.existsByIsbn(isbn);

        log.debug("Book with ISBN {} exists: {}", isbn, exists);

        return exists;
    }

    @Override
    public final boolean existsByIsbnForAnother(final long number, final String isbn) {
        final boolean exists;

        log.debug("Checking if book with ISBN {} and number not {} exists", isbn, number);

        exists = bookSpringRepository.existsByIsbnAndNumberNot(isbn, number);

        log.debug("Book with ISBN {} and number not {} exists: {}", isbn, number, exists);

        return exists;
    }

    @Override
    public final Page<FictionBook> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<FictionBook> read;
        final Pageable                                          pageable;

        log.debug("Finding books with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = bookSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found books {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Collection<FictionBook> findAll(final Sorting sorting) {
        final Collection<FictionBook> read;
        final Sort                    sort;

        log.debug("Finding books with sorting {}", sorting);

        sort = SpringSorting.toSort(sorting);
        read = bookSpringRepository.findAll(sort)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found books {}", read);

        return read;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the books");

        number = bookSpringRepository.findNextNumber();

        log.debug("Found number {}", number);

        return number;
    }

    @Override
    public final Optional<FictionBook> findOne(final long number) {
        final Optional<FictionBook> book;

        log.debug("Finding book {}", number);

        book = bookSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found book {}: {}", number, book);

        return book;
    }

    @Override
    public final FictionBook save(final FictionBook book) {
        final Optional<FictionBookEntity> existing;
        final FictionBookEntity           entity;
        final FictionBookEntity           created;
        final FictionBook                 saved;

        log.debug("Saving book {}", book);

        entity = toEntity(book);

        existing = bookSpringRepository.findByNumber(book.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = bookSpringRepository.save(entity);
        saved = toDomain(created);

        log.debug("Saved book {}", saved);

        return saved;
    }

    private final FictionBook toDomain(final FictionBookEntity entity) {
        final Collection<Publisher>       publishers;
        final Collection<Donor>           donors;
        final Collection<Author>          authors;
        final boolean                     lent;
        final Collection<BookLendingInfo> lendings;
        final Title                       title;
        final String                      supertitle;
        final String                      subtitle;
        final Optional<Donation>          donation;

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

        // Lendings
        lendings = bookLendingSpringRepository.findAllByBookId(entity.getId())
            .stream()
            .map(l -> toDomain(entity, l))
            .toList();

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

        lent = bookSpringRepository.isLent(entity.getId());
        return new FictionBook(entity.getNumber(), title, entity.getIsbn(), entity.getLanguage(),
            entity.getPublishDate(), lent, authors, lendings, publishers, donation);
    }

    private final BookLendingInfo toDomain(final FictionBookEntity bookEntity, final BookLendingEntity entity) {
        final Optional<Borrower> borrower;
        // TODO: should not contain all the member data
        borrower = contactSpringRepository.findById(entity.getContactId())
            .map(BookEntityMapper::toDomain);
        new Title(bookEntity.getSupertitle(), bookEntity.getTitle(), bookEntity.getSubtitle());
        return new BookLendingInfo(borrower.get(), entity.getLendingDate(), entity.getReturnDate());
    }

    private final FictionBookEntity toEntity(final FictionBook domain) {
        final Collection<Long>            authorNumbers;
        final Collection<Long>            publisherNumbers;
        final Collection<Long>            donorNumbers;
        final Collection<PublisherEntity> publishers;
        final Collection<ContactEntity>   donors;
        final Collection<AuthorEntity>    authors;
        final FictionBookEntity           entity;

        // Publishers
        publisherNumbers = domain.publishers()
            .stream()
            .map(Publisher::number)
            .toList();
        publishers = publisherSpringRepository.findAllByNumberIn(publisherNumbers);

        // Donors
        if (domain.donation()
            .isPresent()) {
            donorNumbers = domain.donation()
                .get()
                .donors()
                .stream()
                .map(Donor::number)
                .toList();
            donors = contactSpringRepository.findAllByNumberIn(donorNumbers);
        } else {
            donors = List.of();
        }

        // Authors
        authorNumbers = domain.authors()
            .stream()
            .map(Author::number)
            .toList();
        authors = authorSpringRepository.findAllByNumberIn(authorNumbers);

        entity = new FictionBookEntity();
        entity.setNumber(domain.number());
        entity.setIsbn(domain.isbn());
        entity.setSupertitle(domain.title()
            .supertitle());
        entity.setTitle(domain.title()
            .title());
        entity.setSubtitle(domain.title()
            .subtitle());
        entity.setLanguage(domain.language());
        entity.setPublishDate(domain.publishDate());
        entity.setDonationDate(domain.donation()
            .map(Donation::date)
            .orElse(null));
        entity.setAuthors(authors);
        entity.setPublishers(publishers);
        entity.setDonors(donors);

        return entity;
    }

}
