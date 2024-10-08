
package com.bernardomg.association.library.book.usecase.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.inventory.domain.exception.MissingDonorException;
import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.library.author.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.book.domain.exception.MissingBookException;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.book.usecase.validation.BookIsbnNotExistsForAnotherRule;
import com.bernardomg.association.library.book.usecase.validation.BookIsbnNotExistsRule;
import com.bernardomg.association.library.book.usecase.validation.BookIsbnValidRule;
import com.bernardomg.association.library.book.usecase.validation.BookLanguageCodeValidRule;
import com.bernardomg.association.library.book.usecase.validation.BookTitleNotEmptyRule;
import com.bernardomg.association.library.booktype.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.gamesystem.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.publisher.domain.exception.MissingPublisherException;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class DefaultBookService implements BookService {

    private final AuthorRepository     authorRepository;

    private final BookRepository       bookRepository;

    private final BookTypeRepository   bookTypeRepository;

    private final Validator<Book>      createBookValidator;

    private final DonorRepository      donorRepository;

    private final GameSystemRepository gameSystemRepository;

    private final PublisherRepository  publisherRepository;

    private final Validator<Book>      updateBookValidator;

    public DefaultBookService(final BookRepository bookRepo, final AuthorRepository authorRepo,
            final PublisherRepository publisherRepo, final BookTypeRepository bookTypeRepo,
            final GameSystemRepository gameSystemRepo, final DonorRepository donorRepo) {
        super();

        bookRepository = Objects.requireNonNull(bookRepo);
        authorRepository = Objects.requireNonNull(authorRepo);
        publisherRepository = Objects.requireNonNull(publisherRepo);
        bookTypeRepository = Objects.requireNonNull(bookTypeRepo);
        gameSystemRepository = Objects.requireNonNull(gameSystemRepo);
        donorRepository = Objects.requireNonNull(donorRepo);

        createBookValidator = new FieldRuleValidator<>(new BookTitleNotEmptyRule(), new BookLanguageCodeValidRule(),
            new BookIsbnValidRule(), new BookIsbnNotExistsRule(bookRepository));
        updateBookValidator = new FieldRuleValidator<>(new BookTitleNotEmptyRule(), new BookLanguageCodeValidRule(),
            new BookIsbnValidRule(), new BookIsbnNotExistsForAnotherRule(bookRepository));
    }

    @Override
    public final Book create(final Book book) {
        final Book                  toCreate;
        final Long                  number;
        final Collection<Author>    authors;
        final Collection<Publisher> publishers;
        final Collection<Donor>     donors;

        log.debug("Creating book {}", book);

        // TODO: verify the language is a valid code

        validateRelationships(book);

        // Get number
        number = bookRepository.findNextNumber();

        // Remove duplicates
        authors = book.authors()
            .stream()
            .distinct()
            .toList();
        publishers = book.publishers()
            .stream()
            .distinct()
            .toList();
        donors = book.donors()
            .stream()
            .distinct()
            .toList();

        toCreate = Book.builder()
            .withNumber(number)
            .withAuthors(authors)
            .withPublishers(publishers)
            .withBookType(book.bookType())
            .withGameSystem(book.gameSystem())
            .withDonors(donors)
            .withIsbn(book.isbn())
            .withLanguage(book.language())
            .withTitle(book.title())
            .withLendings(List.of())
            .withLent(false)
            .build();

        createBookValidator.validate(book);

        return bookRepository.save(toCreate);
    }

    @Override
    public final void delete(final long number) {

        log.debug("Deleting book {}", number);

        if (!bookRepository.exists(number)) {
            throw new MissingBookException(number);
        }

        bookRepository.delete(number);
    }

    @Override
    public final Iterable<Book> getAll(final Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public final Optional<Book> getOne(final long number) {
        final Optional<Book> book;

        log.debug("Reading book {}", number);

        book = bookRepository.findOne(number);
        if (book.isEmpty()) {
            log.error("Missing book {}", number);
            throw new MissingBookException(number);
        }

        return book;
    }

    @Override
    public final Book update(final long number, final Book book) {
        final Book                  toUpdate;
        final Collection<Author>    authors;
        final Collection<Publisher> publishers;
        final Collection<Donor>     donors;

        log.debug("Updating book with number {} using data {}", number, book);

        // TODO: verify the language is a valid code
        // TODO: validate isbn

        // Check book exists
        if (!bookRepository.exists(number)) {
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
        donors = book.donors()
            .stream()
            .distinct()
            .toList();

        toUpdate = Book.builder()
            .withNumber(number)
            .withAuthors(authors)
            .withPublishers(publishers)
            .withBookType(book.bookType())
            .withGameSystem(book.gameSystem())
            .withDonors(donors)
            .withIsbn(book.isbn())
            .withLanguage(book.language())
            .withTitle(book.title())
            .withLendings(List.of())
            .withLent(false)
            .build();

        updateBookValidator.validate(toUpdate);

        return bookRepository.save(toUpdate);
    }

    private final void validateRelationships(final Book book) {
        boolean donorExists;

        // TODO: add an exception for multiple missing ids
        // Check authors exist
        book.authors()
            .forEach(a -> {
                if (!authorRepository.exists(a.name())) {
                    throw new MissingAuthorException(a.name());
                }
            });

        // TODO: add an exception for multiple missing ids
        // Check publishers exist
        book.publishers()
            .forEach(p -> {
                if (!publisherRepository.exists(p.name())) {
                    throw new MissingPublisherException(p.name());
                }
            });

        // Check game system exist
        if (book.gameSystem()
            .isPresent()
                && !gameSystemRepository.exists(book.gameSystem()
                    .get()
                    .name())) {
            throw new MissingGameSystemException(book.gameSystem()
                .get()
                .name());
        }

        // Check book type exist
        if (book.bookType()
            .isPresent()
                && !bookTypeRepository.exists(book.bookType()
                    .get()
                    .name())) {
            throw new MissingBookTypeException(book.bookType()
                .get()
                .name());
        }

        // Check donor exist
        for (final Donor donor : book.donors()) {
            donorExists = donorRepository.exists(donor.number());
            if (!donorExists) {
                throw new MissingDonorException(donor.number());
            }
        }

    }

}
