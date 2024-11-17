
package com.bernardomg.association.library.lending.usecase.service;

import java.time.LocalDate;
import java.util.Objects;

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
    public final BookLending lendBook(final long book, final long personNumber, final LocalDate date) {
        final BookLending lending;
        final Person      person;
        final BookLending created;

        log.debug("Lending book {} to {}", book, personNumber);

        if (!bookRepository.exists(book)) {
            log.debug("Missing book {}", book);
            throw new MissingBookException(book);
        }

        person = personRepository.findOne(personNumber)
            .orElseThrow(() -> {
                log.debug("Missing person {}", personNumber);
                throw new MissingPersonException(personNumber);
            });

        lending = new BookLending(book, person, date);

        lendBookValidator.validate(lending);

        created = bookLendingRepository.save(lending);

        log.debug("Lent book {} to {}", book, personNumber);

        return created;
    }

    @Override
    public final BookLending returnBook(final long book, final long personNumber, final LocalDate date) {
        final BookLending read;
        final BookLending lending;
        final BookLending returned;

        log.debug("Returning book {} from {}", book, personNumber);

        read = bookLendingRepository.findOne(book, personNumber)
            .orElseThrow(() -> {
                log.error("Missing book {}", book + "-" + personNumber);
                throw new MissingBookLendingException(book + "-" + personNumber);
            });

        lending = read.returned(date);

        // TODO: not allow returning a book lent to another
        returnBookValidator.validate(lending);

        returned = bookLendingRepository.save(lending);

        log.debug("Returned book {} from {}", book, personNumber);

        return returned;
    }

}
