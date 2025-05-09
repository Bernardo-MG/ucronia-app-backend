
package com.bernardomg.association.library.lending.adapter.inbound.jpa.repository;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.book.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.BookSpringRepository;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.model.BookLending.Borrower;
import com.bernardomg.association.library.lending.domain.model.BookLending.LentBook;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public final class JpaBookLendingRepository implements BookLendingRepository {

    private final BookLendingSpringRepository bookLendingSpringRepository;

    private final BookSpringRepository        bookSpringRepository;

    private final PersonSpringRepository      personSpringRepository;

    public JpaBookLendingRepository(final BookLendingSpringRepository bookLendingSpringRepo,
            final BookSpringRepository bookSpringRepo, final PersonSpringRepository personSpringRepo) {
        super();

        bookLendingSpringRepository = Objects.requireNonNull(bookLendingSpringRepo);
        bookSpringRepository = Objects.requireNonNull(bookSpringRepo);
        personSpringRepository = Objects.requireNonNull(personSpringRepo);
    }

    @Override
    public final Iterable<BookLending> findAll(final Pagination pagination, final Sorting sorting) {
        final Page<BookLending> lendings;
        final Pageable          pageable;

        log.debug("Finding all the book lendings");

        // TODO: test pagination and sorting
        pageable = SpringPagination.toPageable(pagination, sorting);
        lendings = bookLendingSpringRepository.findAllReturned(pageable)
            .map(this::toDomain);

        log.debug("Found all the book lendings: {}", lendings);

        return lendings;
    }

    @Override
    public final Optional<BookLending> findLent(final long bookNumber) {
        final Optional<BookLending> lending;
        final Pageable              pageable;

        log.debug("Finding lent book lending for book {}", bookNumber);

        pageable = Pageable.ofSize(1);
        lending = bookLendingSpringRepository.findAllForBookLent(bookNumber, pageable)
            .stream()
            .findFirst()
            .map(this::toDomain);

        log.debug("Found lent book lending for book {}: {}", bookNumber, lending);

        return lending;
    }

    @Override
    public final Optional<BookLending> findOne(final long bookNumber, final long personNumber) {
        final Optional<BookLending> lending;
        final Pageable              pageable;

        log.debug("Finding book lending for book {} and person {}", bookNumber, personNumber);

        pageable = Pageable.ofSize(1);
        lending = bookLendingSpringRepository.find(bookNumber, personNumber, pageable)
            .stream()
            .findFirst()
            .map(this::toDomain);

        log.debug("Found lending for book {} and person {}: {}", bookNumber, personNumber, lending);

        return lending;
    }

    @Override
    public final Optional<BookLending> findReturned(final long bookNumber) {
        final Optional<BookLending> lending;
        final Pageable              pageable;

        log.debug("Finding returned book lending for book {}", bookNumber);

        pageable = Pageable.ofSize(1);
        lending = bookLendingSpringRepository.findAllForBookReturned(bookNumber, pageable)
            .stream()
            .findFirst()
            .map(this::toDomain);

        log.debug("Found returned book lending for book {}: {}", bookNumber, lending);

        return lending;
    }

    @Override
    public final Optional<BookLending> findReturned(final long bookNumber, final long personNumber,
            final LocalDate lendingDate) {
        final Optional<BookLending> lending;
        final Pageable              pageable;

        log.debug("Finding returned book {} for person {} and date {}", bookNumber, personNumber, lendingDate);

        pageable = Pageable.ofSize(1);
        lending = bookLendingSpringRepository.findAllReturned(bookNumber, personNumber, lendingDate, pageable)
            .stream()
            .findFirst()
            .map(this::toDomain);

        log.debug("Found returned book lending for book {}: {}", bookNumber, lending);

        return lending;
    }

    @Override
    public final BookLending save(final BookLending lending) {
        final BookLendingEntity      toCreate;
        final BookLendingEntity      created;
        final BookLending            saved;
        final Optional<BookEntity>   bookEntity;
        final Optional<PersonEntity> personEntity;

        log.debug("Saving book lending {}", lending);

        bookEntity = bookSpringRepository.findByNumber(lending.book()
            .number());
        personEntity = personSpringRepository.findByNumber(lending.borrower()
            .number());

        if ((bookEntity.isPresent()) && (personEntity.isPresent())) {
            toCreate = toEntity(lending, bookEntity.get(), personEntity.get());

            created = bookLendingSpringRepository.save(toCreate);
            saved = toDomain(created, bookEntity.get(), personEntity.get());

            log.debug("Saved book lending {}", lending);
        } else {
            log.debug("Couldn't save book lending {}", lending);
            saved = null;
        }

        return saved;
    }

    private final BookLending toDomain(final BookLendingEntity entity) {
        final Optional<Borrower>   borrower;
        final Optional<BookEntity> bookEntity;
        final LentBook             lentBook;
        final Title                title;

        bookEntity = bookSpringRepository.findById(entity.getBookId());
        borrower = personSpringRepository.findById(entity.getPersonId())
            .map(this::toDomain);
        title = new Title(bookEntity.get()
            .getSupertitle(),
            bookEntity.get()
                .getTitle(),
            bookEntity.get()
                .getSubtitle());
        lentBook = new LentBook(bookEntity.get()
            .getNumber(), title);
        return new BookLending(lentBook, borrower.get(), entity.getLendingDate(), entity.getReturnDate());
    }

    private final BookLending toDomain(final BookLendingEntity entity, final BookEntity bookEntity,
            final PersonEntity personEntity) {
        final Borrower borrower;
        final LentBook lentBook;
        final Title    title;

        borrower = toDomain(personEntity);
        title = new Title(bookEntity.getSupertitle(), bookEntity.getTitle(), bookEntity.getSubtitle());
        lentBook = new LentBook(bookEntity.getNumber(), title);
        return new BookLending(lentBook, borrower, entity.getLendingDate(), entity.getReturnDate());
    }

    private final Borrower toDomain(final PersonEntity entity) {
        final PersonName name;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        return new Borrower(entity.getNumber(), name);
    }

    private final BookLendingEntity toEntity(final BookLending domain, final BookEntity bookEntity,
            final PersonEntity personEntity) {
        return BookLendingEntity.builder()
            .withBookId(bookEntity.getId())
            .withPersonId(personEntity.getId())
            .withLendingDate(domain.lendingDate())
            .withReturnDate(domain.returnDate())
            .build();
    }

}
