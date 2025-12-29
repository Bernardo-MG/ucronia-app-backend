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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.BookEntityMapper;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.GameBookEntity;
import com.bernardomg.association.library.book.domain.model.BookLendingInfo;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.booktype.adapter.inbound.jpa.model.BookTypeEntity;
import com.bernardomg.association.library.booktype.adapter.inbound.jpa.repository.BookTypeSpringRepository;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.model.GameSystemEntity;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.repository.GameSystemSpringRepository;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.BookLendingSpringRepository;
import com.bernardomg.association.library.lending.domain.model.BookLending.Borrower;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.PublisherSpringRepository;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;
import com.bernardomg.data.springframework.SpringSorting;

@Repository
@Transactional
public final class JpaGameBookRepository implements GameBookRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaGameBookRepository.class);

    private final AuthorSpringRepository      authorSpringRepository;

    private final BookLendingSpringRepository bookLendingSpringRepository;

    private final GameBookSpringRepository    bookSpringRepository;

    private final BookTypeSpringRepository    bookTypeSpringRepository;

    private final GameSystemSpringRepository  gameSystemSpringRepository;

    private final ProfileSpringRepository     profileSpringRepository;

    private final PublisherSpringRepository   publisherSpringRepository;

    public JpaGameBookRepository(final GameBookSpringRepository bookSpringRepo,
            final AuthorSpringRepository authorSpringRepo, final PublisherSpringRepository publisherSpringRepo,
            final BookTypeSpringRepository bookTypeSpringRepo, final GameSystemSpringRepository gameSystemSpringRepo,
            final ProfileSpringRepository profileSpringRepo, final BookLendingSpringRepository bookLendingSpringRepo) {
        super();

        bookSpringRepository = Objects.requireNonNull(bookSpringRepo);
        authorSpringRepository = Objects.requireNonNull(authorSpringRepo);
        publisherSpringRepository = Objects.requireNonNull(publisherSpringRepo);
        bookTypeSpringRepository = Objects.requireNonNull(bookTypeSpringRepo);
        gameSystemSpringRepository = Objects.requireNonNull(gameSystemSpringRepo);
        // TODO: maybe should be members only
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
    public final Page<GameBook> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<GameBook> read;
        final Pageable                                       pageable;

        log.debug("Finding books with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = bookSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found books {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Collection<GameBook> findAll(final Sorting sorting) {
        final Collection<GameBook> read;
        final Sort                 sort;

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
    public final Optional<GameBook> findOne(final long number) {
        final Optional<GameBook> book;

        log.debug("Finding book {}", number);

        book = bookSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found book {}: {}", number, book);

        return book;
    }

    @Override
    public final GameBook save(final GameBook book) {
        final Optional<GameBookEntity> existing;
        final GameBookEntity           entity;
        final GameBookEntity           created;
        final GameBook                 saved;

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

    private final GameBook toDomain(final GameBookEntity entity) {
        final Collection<Publisher>       publishers;
        final Optional<GameSystem>        gameSystem;
        final Optional<BookType>          bookType;
        final Collection<Donor>           donors;
        final Collection<Author>          authors;
        final boolean                     lent;
        final Collection<BookLendingInfo> lendings;
        final Title                       title;
        final String                      supertitle;
        final String                      subtitle;
        final Optional<Donation>          donation;

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
        return new GameBook(entity.getNumber(), title, entity.getIsbn(), entity.getLanguage(), entity.getPublishDate(),
            lent, authors, lendings, publishers, donation, bookType, gameSystem);
    }

    private final BookLendingInfo toDomain(final GameBookEntity bookEntity, final BookLendingEntity entity) {
        final Optional<Borrower> borrower;
        // TODO: should not contain all the member data
        borrower = profileSpringRepository.findById(entity.getProfileId())
            .map(BookEntityMapper::toDomain);
        new Title(bookEntity.getSupertitle(), bookEntity.getTitle(), bookEntity.getSubtitle());
        return new BookLendingInfo(borrower.get(), entity.getLendingDate(), entity.getReturnDate());
    }

    private final GameBookEntity toEntity(final GameBook domain) {
        final Collection<Long>            authorNumbers;
        final Collection<Long>            publisherNumbers;
        final Collection<Long>            donorNumbers;
        final Collection<PublisherEntity> publishers;
        final Optional<BookTypeEntity>    bookType;
        final Optional<GameSystemEntity>  gameSystem;
        final Collection<ProfileEntity>   donors;
        final Collection<AuthorEntity>    authors;
        final GameBookEntity              entity;

        // Book type
        if (domain.bookType()
            .isPresent()) {
            bookType = bookTypeSpringRepository.findByNumber(domain.bookType()
                .get()
                .number());
        } else {
            bookType = Optional.empty();
        }

        // Game system
        if (domain.gameSystem()
            .isPresent()) {
            gameSystem = gameSystemSpringRepository.findByNumber(domain.gameSystem()
                .get()
                .number());
        } else {
            gameSystem = Optional.empty();
        }

        // Publishers
        publisherNumbers = domain.publishers()
            .stream()
            .map(Publisher::number)
            .collect(Collectors.toCollection(ArrayList::new));
        publishers = publisherSpringRepository.findAllByNumberIn(publisherNumbers);

        // Donors
        if (domain.donation()
            .isPresent()) {
            donorNumbers = domain.donation()
                .get()
                .donors()
                .stream()
                .map(Donor::number)
                .collect(Collectors.toCollection(ArrayList::new));
            donors = profileSpringRepository.findAllByNumberIn(donorNumbers);
        } else {
            donors = new ArrayList<>();
        }

        // Authors
        authorNumbers = domain.authors()
            .stream()
            .map(Author::number)
            .toList();
        authors = authorSpringRepository.findAllByNumberIn(authorNumbers);

        entity = new GameBookEntity();
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
        entity.setBookType(bookType.orElse(null));
        entity.setGameSystem(gameSystem.orElse(null));
        entity.setAuthors(authors);
        entity.setPublishers(publishers);
        entity.setDonors(donors);

        return entity;
    }

}
