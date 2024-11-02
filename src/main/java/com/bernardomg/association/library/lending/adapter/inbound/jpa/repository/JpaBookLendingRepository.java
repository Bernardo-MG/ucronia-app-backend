
package com.bernardomg.association.library.lending.adapter.inbound.jpa.repository;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.book.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.BookSpringRepository;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.PersonName;

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

        bookEntity = bookSpringRepository.findByNumber(lending.number());
        personEntity = personSpringRepository.findByNumber(lending.person()
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
        final Optional<Person>     person;
        final Optional<BookEntity> bookEntity;

        bookEntity = bookSpringRepository.findById(entity.getBookId());
        person = personSpringRepository.findById(entity.getPersonId())
            .map(this::toDomain);
        return new BookLending(bookEntity.get()
            .getNumber(), person.get(), entity.getLendingDate(), entity.getReturnDate());
    }

    private final BookLending toDomain(final BookLendingEntity entity, final BookEntity bookEntity,
            final PersonEntity personEntity) {
        final Person person;

        person = toDomain(personEntity);
        return new BookLending(bookEntity.getNumber(), person, entity.getLendingDate(), entity.getReturnDate());
    }

    private final Person toDomain(final PersonEntity entity) {
        final PersonName name;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        // TODO: Load membership
        return new Person(entity.getIdentifier(), entity.getNumber(), name, entity.getPhone(), Optional.empty());
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
