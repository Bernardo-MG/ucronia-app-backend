
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
import com.bernardomg.association.library.usecase.validation.BookLendingNotAlreadyLentRule;
import com.bernardomg.association.library.usecase.validation.BookLendingNotAlreadyReturnedRule;
import com.bernardomg.association.library.usecase.validation.BookLendingNotLentBeforeLastReturnRule;
import com.bernardomg.association.library.usecase.validation.BookLendingNotLentInFutureRule;
import com.bernardomg.association.library.usecase.validation.BookLendingNotReturnedBeforeLentRule;
import com.bernardomg.association.library.usecase.validation.BookLendingNotReturnedInFutureRule;
import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class DefaultBookLendingService implements BookLendingService {

    private final BookLendingRepository  bookLendingRepository;

    private final BookRepository         bookRepository;

    private final Validator<BookLending> lendBookValidator;

    private final PersonRepository       personRepository;

    private final Validator<BookLending> returnBookValidator;

    public DefaultBookLendingService(final BookLendingRepository bookLendingRepo, final BookRepository bookRepo,
            final PersonRepository personRepo) {
        super();

        bookLendingRepository = Objects.requireNonNull(bookLendingRepo);
        bookRepository = Objects.requireNonNull(bookRepo);
        personRepository = Objects.requireNonNull(personRepo);

        lendBookValidator = new FieldRuleValidator<>(new BookLendingNotAlreadyLentRule(bookLendingRepo),
            new BookLendingNotLentBeforeLastReturnRule(bookLendingRepo), new BookLendingNotLentInFutureRule());
        returnBookValidator = new FieldRuleValidator<>(new BookLendingNotAlreadyReturnedRule(bookLendingRepo),
            new BookLendingNotReturnedBeforeLentRule(), new BookLendingNotReturnedInFutureRule());
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

        lending = BookLending.builder()
            .withNumber(book)
            .withPerson(person)
            .withLendingDate(date)
            .build();

        lendBookValidator.validate(lending);

        bookLendingRepository.save(lending);
    }

    @Override
    public final void returnBook(final long book, final long person, final LocalDate date) {
        final Optional<BookLending> read;
        final BookLending           lending;

        log.debug("Returning book {} from {}", book, person);

        read = bookLendingRepository.findOne(book, person);
        if (read.isEmpty()) {
            throw new MissingBookLendingException(book + "-" + person);
        }

        lending = BookLending.builder()
            .withNumber(book)
            .withPerson(person)
            .withLendingDate(read.get()
                .getLendingDate())
            .withReturnDate(date)
            .build();

        // TODO: not allow returning a book lent to another
        returnBookValidator.validate(lending);

        bookLendingRepository.returnAt(book, person, date);
    }

}
