
package com.bernardomg.association.library.book.usecase.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.author.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.book.domain.exception.MissingBookException;
import com.bernardomg.association.library.book.domain.exception.MissingDonorException;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.usecase.validation.FictionBookIsbnNotExistsForAnotherRule;
import com.bernardomg.association.library.book.usecase.validation.FictionBookIsbnNotExistsRule;
import com.bernardomg.association.library.book.usecase.validation.FictionBookIsbnValidRule;
import com.bernardomg.association.library.book.usecase.validation.FictionBookLanguageCodeValidRule;
import com.bernardomg.association.library.book.usecase.validation.FictionBookTitleNotEmptyRule;
import com.bernardomg.association.library.publisher.domain.exception.MissingPublisherException;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class DefaultFictionBookService implements FictionBookService {

    private final AuthorRepository       authorRepository;

    private final FictionBookRepository  bookRepository;

    private final Validator<FictionBook> createBookValidator;

    private final PersonRepository       personRepository;

    private final PublisherRepository    publisherRepository;

    private final Validator<FictionBook> updateBookValidator;

    public DefaultFictionBookService(final FictionBookRepository bookRepo, final AuthorRepository authorRepo,
            final PublisherRepository publisherRepo, final PersonRepository personRepo) {
        super();

        bookRepository = Objects.requireNonNull(bookRepo);
        authorRepository = Objects.requireNonNull(authorRepo);
        publisherRepository = Objects.requireNonNull(publisherRepo);
        personRepository = Objects.requireNonNull(personRepo);

        createBookValidator = new FieldRuleValidator<>(new FictionBookTitleNotEmptyRule(),
            new FictionBookLanguageCodeValidRule(), new FictionBookIsbnValidRule(),
            new FictionBookIsbnNotExistsRule(bookRepository));
        updateBookValidator = new FieldRuleValidator<>(new FictionBookTitleNotEmptyRule(),
            new FictionBookLanguageCodeValidRule(), new FictionBookIsbnValidRule(),
            new FictionBookIsbnNotExistsForAnotherRule(bookRepository));
    }

    @Override
    public final FictionBook create(final FictionBook book) {
        final FictionBook           toCreate;
        final Long                  number;
        final Collection<Author>    authors;
        final Collection<Publisher> publishers;
        final Collection<Donor>     donors;
        final Optional<Donation>    donation;
        final FictionBook           created;

        log.debug("Creating book {}", book);

        // TODO: verify the language is a valid code

        validateRelationships(book);

        // Get number
        number = bookRepository.findNextNumber();

        // TODO: relationships are no longer received on create
        // Remove duplicates
        authors = book.authors()
            .stream()
            .distinct()
            .toList();
        publishers = book.publishers()
            .stream()
            .distinct()
            .toList();
        if (book.donation()
            .isPresent()) {
            donors = book.donation()
                .get()
                .donors()
                .stream()
                .distinct()
                .toList();

            donation = Optional.of(new Donation(book.donation()
                .get()
                .date(), donors));
        } else {
            donation = Optional.empty();
        }
        toCreate = new FictionBook(number, book.title(), book.isbn(), book.language(), book.publishDate(), false,
            authors, List.of(), publishers, donation);

        createBookValidator.validate(book);

        created = bookRepository.save(toCreate);

        log.debug("Created book {}", book);

        return created;
    }

    @Override
    public final void delete(final long number) {

        log.debug("Deleting book {}", number);

        if (!bookRepository.exists(number)) {
            log.error("Missing book {}", number);
            throw new MissingBookException(number);
        }

        bookRepository.delete(number);

        log.debug("Deleted book {}", number);
    }

    @Override
    public final Iterable<FictionBook> getAll(final Pagination pagination, final Sorting sorting) {
        final Iterable<FictionBook> books;

        log.debug("Reading books with pagination {} and sorting {}", pagination, sorting);

        books = bookRepository.findAll(pagination, sorting);

        log.debug("Read books with pagination {} and sorting {}", pagination, sorting);

        return books;
    }

    @Override
    public final Optional<FictionBook> getOne(final long number) {
        final Optional<FictionBook> book;

        log.debug("Reading book {}", number);

        book = bookRepository.findOne(number);
        if (book.isEmpty()) {
            log.error("Missing book {}", number);
            throw new MissingBookException(number);
        }

        log.debug("Read book {}", number);

        return book;
    }

    @Override
    public final FictionBook update(final long number, final FictionBook book) {
        final FictionBook           toUpdate;
        final Collection<Author>    authors;
        final Collection<Publisher> publishers;
        final Collection<Donor>     donors;
        final Optional<Donation>    donation;
        final FictionBook           updated;

        log.debug("Updating book with number {} using data {}", number, book);

        // TODO: verify the language is a valid code
        // TODO: validate isbn

        // Check book exists
        if (!bookRepository.exists(number)) {
            log.error("Missing book {}", number);
            throw new MissingBookException(number);
        }

        validateRelationships(book);

        // Remove duplicates
        authors = book.authors()
            .stream()
            .distinct()
            .toList();
        publishers = book.publishers()
            .stream()
            .distinct()
            .toList();
        if (book.donation()
            .isPresent()) {
            donors = book.donation()
                .get()
                .donors()
                .stream()
                .distinct()
                .toList();

            donation = Optional.of(new Donation(book.donation()
                .get()
                .date(), donors));
        } else {
            donation = Optional.empty();
        }
        toUpdate = new FictionBook(number, book.title(), book.isbn(), book.language(), book.publishDate(), false,
            authors, List.of(), publishers, donation);

        updateBookValidator.validate(toUpdate);

        updated = bookRepository.save(toUpdate);

        log.debug("Updated book with number {} using data {}", number, book);

        return updated;
    }

    private final void validateRelationships(final FictionBook book) {
        final Optional<Author>    invalidAuthor;
        final Optional<Publisher> invalidPublisher;
        final Optional<Donor>     invalidDonor;

        // TODO: add an exception for multiple missing ids
        // Check authors exist
        invalidAuthor = book.authors()
            .stream()
            .filter(d -> !authorRepository.exists(d.number()))
            .findAny();
        if (invalidAuthor.isPresent()) {
            log.error("Missing author {}", invalidAuthor.get()
                .number());
            throw new MissingAuthorException(invalidAuthor.get()
                .number());
        }

        // TODO: add an exception for multiple missing ids
        // Check publishers exist
        invalidPublisher = book.publishers()
            .stream()
            .filter(d -> !publisherRepository.exists(d.number()))
            .findAny();
        if (invalidPublisher.isPresent()) {
            log.error("Missing publisher {}", invalidPublisher.get()
                .number());
            throw new MissingPublisherException(invalidPublisher.get()
                .number());
        }

        // Check donors exist
        invalidDonor = book.donation()
            .map(Donation::donors)
            .orElse(List.of())
            .stream()
            .filter(d -> !personRepository.exists(d.number()))
            .findAny();
        if (invalidDonor.isPresent()) {
            log.error("Missing donor {}", invalidDonor.get()
                .number());
            throw new MissingDonorException(invalidDonor.get()
                .number());
        }

    }

}
