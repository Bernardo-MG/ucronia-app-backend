
package com.bernardomg.association.library.usecase.service;

import java.time.YearMonth;
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
    public final void lendBook(final long number, final long person) {
        final BookLending lending;
        final YearMonth   now;

        if (!bookRepository.exists(number)) {
            throw new MissingBookException(number);
        }

        if (!personRepository.exists(person)) {
            throw new MissingPersonException(person);
        }

        // TODO: should receive the date
        now = YearMonth.now();
        lending = BookLending.builder()
            .withNumber(number)
            .withMember(person)
            .withLendingDate(now)
            .build();

        bookLendingRepository.save(lending);
    }

    @Override
    public final void returnBook(final long index, final long person) {
        final Optional<BookLending> read;
        final BookLending           toSave;

        read = bookLendingRepository.findOne(index, person);
        if (read.isEmpty()) {
            throw new MissingBookLendingException(index + "-" + person);
        }

        toSave = read.get();
        toSave.setReturnDate(YearMonth.now());

        bookLendingRepository.save(toSave);
    }

}
