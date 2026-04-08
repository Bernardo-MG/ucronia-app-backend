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
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.BookEntityMapper;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.FictionBookEntity;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.FictionBookEntityMapper;
import com.bernardomg.association.library.book.domain.model.BookLendingInfo;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.BookLendingSpringRepository;
import com.bernardomg.association.library.lending.domain.model.Borrower;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.PublisherSpringRepository;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberProfileSpringRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.domain.Sorting.Property;
import com.bernardomg.pagination.springframework.SpringPagination;
import com.bernardomg.pagination.springframework.SpringSorting;

@Transactional
public final class JpaFictionBookRepository implements FictionBookRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                 log = LoggerFactory.getLogger(JpaFictionBookRepository.class);

    private final AuthorSpringRepository        authorSpringRepository;

    private final BookLendingSpringRepository   bookLendingSpringRepository;

    private final FictionBookSpringRepository   bookSpringRepository;

    private final MemberProfileSpringRepository memberProfileSpringRepository;

    private final ProfileSpringRepository       profileSpringRepository;

    private final PublisherSpringRepository     publisherSpringRepository;

    public JpaFictionBookRepository(final FictionBookSpringRepository bookSpringRepo,
            final AuthorSpringRepository authorSpringRepo, final PublisherSpringRepository publisherSpringRepo,
            final MemberProfileSpringRepository memberProfileSpringRepo,
            final ProfileSpringRepository profileSpringRepo, final BookLendingSpringRepository bookLendingSpringRepo) {
        super();

        bookSpringRepository = Objects.requireNonNull(bookSpringRepo);
        authorSpringRepository = Objects.requireNonNull(authorSpringRepo);
        publisherSpringRepository = Objects.requireNonNull(publisherSpringRepo);
        memberProfileSpringRepository = Objects.requireNonNull(memberProfileSpringRepo);
        profileSpringRepository = Objects.requireNonNull(profileSpringRepo);
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
        final Sorting                                           fixedSorting;

        // TODO: test sorting
        log.debug("Finding books with pagination {} and sorting {}", pagination, sorting);

        fixedSorting = fixSorting(sorting);
        pageable = SpringPagination.toPageable(pagination, fixedSorting);
        read = bookSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found books {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Collection<FictionBook> findAll(final Sorting sorting) {
        final Collection<FictionBook> read;
        final Sort                    sort;
        final Sorting                 fixedSorting;

        // TODO: test sorting
        log.debug("Finding books with sorting {}", sorting);

        fixedSorting = fixSorting(sorting);
        sort = SpringSorting.toSort(fixedSorting);
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

    private final Sorting fixSorting(final Sorting sorting) {
        final Collection<Property> properties;

        properties = sorting.properties()
            .stream()
            .map(prop -> {
                if (prop.name()
                    .startsWith("title.")) {
                    return new Property(prop.name()
                        .replaceFirst("title\\.", ""), prop.direction());
                }
                return prop;
            })
            .toList();

        return new Sorting(properties);
    }

    private final FictionBook toDomain(final FictionBookEntity entity) {
        final boolean                     lent;
        final Collection<BookLendingInfo> lendings;

        // Lendings
        lendings = bookLendingSpringRepository.findAllByBookIdOrderByReturnDate(entity.getId())
            .stream()
            .map(l -> toDomain(entity, l))
            .toList();
        lent = bookSpringRepository.isLent(entity.getId());

        return FictionBookEntityMapper.toDomain(entity, lent, lendings);
    }

    private final BookLendingInfo toDomain(final FictionBookEntity bookEntity, final BookLendingEntity entity) {
        final Borrower borrower;
        borrower = memberProfileSpringRepository.findByProfileId(entity.getProfileId())
            .map(BookEntityMapper::toDomain)
            .orElseThrow(() -> {
                log.error("Missing profile {}", entity.getProfileId());
                throw new MissingProfileException(entity.getProfileId());
            });

        new Title(bookEntity.getSupertitle(), bookEntity.getTitle(), bookEntity.getSubtitle());
        return new BookLendingInfo(borrower, entity.getLendingDate(), entity.getReturnDate());
    }

    private final FictionBookEntity toEntity(final FictionBook domain) {
        final Collection<Long>            authorNumbers;
        final Collection<Long>            publisherNumbers;
        final Collection<Long>            donorNumbers;
        final Collection<PublisherEntity> publishers;
        final Collection<ProfileEntity>   donors;
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
            donors = profileSpringRepository.findAllByNumberIn(donorNumbers);
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
