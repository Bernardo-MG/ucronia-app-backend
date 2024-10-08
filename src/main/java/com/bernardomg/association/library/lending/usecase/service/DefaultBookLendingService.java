
package com.bernardomg.association.library.lending.usecase.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.book.domain.exception.MissingBookException;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.lending.domain.exception.MissingBookLendingException;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotAlreadyLentRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotAlreadyReturnedRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotLentBeforeLastReturnRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotLentInFutureRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotReturnedBeforeLastReturnRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotReturnedBeforeLentRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotReturnedInFutureRule;
import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
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
            new BookLendingNotReturnedBeforeLastReturnRule(bookLendingRepo), new BookLendingNotReturnedBeforeLentRule(),
            new BookLendingNotReturnedInFutureRule());
    }

    @Override
    public final void lendBook(final long book, final long personNumber, final LocalDate date) {
        final BookLending      lending;
        final Optional<Person> person;

        log.debug("Lending book {} to {}", book, personNumber);

        if (!bookRepository.exists(book)) {
            throw new MissingBookException(book);
        }

        if (!personRepository.exists(personNumber)) {
            throw new MissingPersonException(personNumber);
        }

        person = personRepository.findOne(personNumber);

        lending = new BookLending(book, person.orElse(new Person(null, null, null, null)), date, null);

        lendBookValidator.validate(lending);

        bookLendingRepository.save(lending);
    }

    @Override
    public final void returnBook(final long book, final long personNumber, final LocalDate date) {
        final BookLending read;
        final BookLending lending;

        log.debug("Returning book {} from {}", book, personNumber);

        read = bookLendingRepository.findOne(book, personNumber)
            .orElseThrow(() -> {
                log.error("Missing book {}", book + "-" + personNumber);
                throw new MissingBookLendingException(book + "-" + personNumber);
            });

        // Used just for validation
        lending = new BookLending(read.number(), read.person(), read.lendingDate(), date);

        // TODO: not allow returning a book lent to another
        returnBookValidator.validate(lending);

        bookLendingRepository.returnAt(book, personNumber, date);
    }

}
