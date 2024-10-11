
package com.bernardomg.association.library.lending.adapter.inbound.jpa.repository;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

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
    public final Optional<BookLending> findLent(final long book) {
        final Optional<BookLending> lending;
        final Optional<BookEntity>  bookEntity;

        log.debug("Finding lent book lending for book {}", book);

        bookEntity = bookSpringRepository.findByNumber(book);

        if (bookEntity.isPresent()) {
            lending = bookLendingSpringRepository.findFirstByBookIdAndReturnDateIsNull(bookEntity.get()
                .getId())
                .map(m -> toDomain(m, bookEntity.get()));

            log.debug("Found lent book lending for book {}: {}", book, lending);
        } else {
            log.debug("Book {} not found", book);
            lending = Optional.empty();
        }

        return lending;
    }

    @Override
    public final Optional<BookLending> findOne(final long book, final long person) {
        final Optional<BookLending>  lending;
        final Optional<BookEntity>   bookEntity;
        final Optional<PersonEntity> personEntity;

        log.debug("Finding book lending for book {} and person {}", book, person);

        bookEntity = bookSpringRepository.findByNumber(book);
        personEntity = personSpringRepository.findByNumber(person);

        if ((bookEntity.isPresent()) && (personEntity.isPresent())) {
            lending = bookLendingSpringRepository.findFirstByBookIdAndPersonIdOrderByReturnDateDesc(bookEntity.get()
                .getId(),
                personEntity.get()
                    .getId())
                .map(m -> toDomain(m, bookEntity.get(), personEntity.get()));

            log.debug("Found book lending for book {} and person {}: {}", book, person, lending);
        } else {
            log.debug("Book {} or person {} not found", book, person);
            lending = Optional.empty();
        }

        return lending;
    }

    @Override
    public final Optional<BookLending> findReturned(final long book) {
        final Optional<BookLending> lending;
        final Optional<BookEntity>  bookEntity;

        log.debug("Finding returned book lending for book {}", book);

        bookEntity = bookSpringRepository.findByNumber(book);

        if (bookEntity.isPresent()) {
            lending = bookLendingSpringRepository
                .findFirstByBookIdAndReturnDateIsNotNullOrderByReturnDateDesc(bookEntity.get()
                    .getId())
                .map(m -> toDomain(m, bookEntity.get()));

            log.debug("Found returned book lending for book {}: {}", book, lending);
        } else {
            log.debug("Book {} not found", book);
            lending = Optional.empty();
        }

        return lending;
    }

    @Override
    public final Optional<BookLending> findReturned(final long book, final long person, final LocalDate date) {
        final Optional<BookLending>  lending;
        final Optional<BookEntity>   bookEntity;
        final Optional<PersonEntity> personEntity;

        log.debug("Finding returned book {} for person {} and date {}", book, person, date);

        bookEntity = bookSpringRepository.findByNumber(book);
        personEntity = personSpringRepository.findByNumber(person);

        if ((bookEntity.isPresent()) && (personEntity.isPresent())) {
            lending = bookLendingSpringRepository
                .findFirstByBookIdAndPersonIdAndLendingDateAndReturnDateIsNotNullOrderByReturnDateDesc(bookEntity.get()
                    .getId(),
                    personEntity.get()
                        .getId(),
                    date)
                .map(m -> toDomain(m, bookEntity.get(), personEntity.get()));

            log.debug("Found returned book lending for book {}: {}", book, lending);
        } else {
            log.debug("Book {} not found", book);
            lending = Optional.empty();
        }

        return lending;
    }

    @Override
    public final Optional<BookLending> returnAt(final long book, final long person, final LocalDate date) {
        final Optional<BookLendingEntity> readLending;
        final Optional<BookLending>       lending;
        final BookLendingEntity           lendingEntity;
        final BookLendingEntity           lentEntity;
        final Optional<BookEntity>        bookEntity;
        final Optional<PersonEntity>      personEntity;

        log.debug("Returning book {} from person {} at {}", book, person, date);

        bookEntity = bookSpringRepository.findByNumber(book);
        personEntity = personSpringRepository.findByNumber(person);

        if ((bookEntity.isPresent()) && (personEntity.isPresent())) {
            readLending = bookLendingSpringRepository.findFirstByBookIdAndPersonIdOrderByReturnDateDesc(bookEntity.get()
                .getId(),
                personEntity.get()
                    .getId());
            if (readLending.isEmpty()) {
                log.warn("Missing book lending for book {} and person {} at {}", book, person, date);
                lending = Optional.empty();
            } else {
                lendingEntity = readLending.get();
                lendingEntity.setReturnDate(date);
                lentEntity = bookLendingSpringRepository.save(lendingEntity);
                lending = Optional.of(lentEntity)
                    .map(m -> toDomain(m, bookEntity.get(), personEntity.get()));

                log.debug("Returned book {} from person {} at {}: {}", book, person, date, lending);
            }
        } else {
            log.debug("Book {} or person {} not found", book, person);
            lending = Optional.empty();
        }

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

    private final BookLending toDomain(final BookLendingEntity entity, final BookEntity bookEntity) {
        final Optional<Person> person;

        person = personSpringRepository.findById(entity.getPersonId())
            .map(this::toDomain);
        return new BookLending(bookEntity.getNumber(), person.orElse(new Person(null, null, null, null)),
            entity.getLendingDate(), entity.getReturnDate());
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
        return new Person(entity.getIdentifier(), entity.getNumber(), name, entity.getPhone());
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
