
package com.bernardomg.association.library.usecase.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.domain.exception.MissingBookException;
import com.bernardomg.association.library.domain.exception.MissingBookLendingException;
import com.bernardomg.association.library.domain.model.BookLending;
import com.bernardomg.association.library.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class DefaultBookLendingService implements BookLendingService {

    private final BookLendingRepository bookLendingRepository;

    private final BookRepository        bookRepository;

    private final PersonRepository      personRepository;

    public DefaultBookLendingService(final BookLendingRepository bookLendingRepo, final BookRepository bookRepo,
            final PersonRepository personRepo) {
        super();

        bookLendingRepository = Objects.requireNonNull(bookLendingRepo);
        bookRepository = Objects.requireNonNull(bookRepo);
        personRepository = Objects.requireNonNull(personRepo);
    }

    @Override
    public final void lendBook(final long book, final long person, final LocalDate date) {
        final BookLending lending;

        log.debug("Lending book {} to {}", book, person);

        if (!bookRepository.exists(book)) {
            throw new MissingBookException(book);
        }

        if (!personRepository.exists(person)) {
            throw new MissingPersonException(person);
        }

        // TODO: Validate. What if it is already lent?
        // TODO: Can't lend in the future

        lending = BookLending.builder()
            .withNumber(book)
            .withMember(person)
            .withLendingDate(date)
            .build();

        bookLendingRepository.save(lending);
    }

    @Override
    public final void returnBook(final long book, final long person, final LocalDate date) {
        final Optional<BookLending> read;

        log.debug("Returning book {} from {}", book, person);

        read = bookLendingRepository.findOne(book, person);
        if (read.isEmpty()) {
            throw new MissingBookLendingException(book + "-" + person);
        }

        // TODO: Validate. What if it is already returned?
        // TODO: Can't return before lending

        bookLendingRepository.returnAt(book, person, date);
    }

}
