
package com.bernardomg.association.library.book.usecase.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.author.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.book.domain.exception.MissingBookException;
import com.bernardomg.association.library.book.domain.exception.MissingDonorException;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.model.Book.Donation;
import com.bernardomg.association.library.book.domain.model.Book.Donor;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.book.usecase.validation.BookIsbnNotExistsForAnotherRule;
import com.bernardomg.association.library.book.usecase.validation.BookIsbnNotExistsRule;
import com.bernardomg.association.library.book.usecase.validation.BookIsbnValidRule;
import com.bernardomg.association.library.book.usecase.validation.BookLanguageCodeValidRule;
import com.bernardomg.association.library.book.usecase.validation.BookTitleNotEmptyRule;
import com.bernardomg.association.library.booktype.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.gamesystem.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.publisher.domain.exception.MissingPublisherException;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.person.domain.repository.PersonRepository;
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

    private final GameSystemRepository gameSystemRepository;

    private final PersonRepository     personRepository;

    private final PublisherRepository  publisherRepository;

    private final Validator<Book>      updateBookValidator;

    public DefaultBookService(final BookRepository bookRepo, final AuthorRepository authorRepo,
            final PublisherRepository publisherRepo, final BookTypeRepository bookTypeRepo,
            final GameSystemRepository gameSystemRepo, final PersonRepository personRepo) {
        super();

        bookRepository = Objects.requireNonNull(bookRepo);
        authorRepository = Objects.requireNonNull(authorRepo);
        publisherRepository = Objects.requireNonNull(publisherRepo);
        bookTypeRepository = Objects.requireNonNull(bookTypeRepo);
        gameSystemRepository = Objects.requireNonNull(gameSystemRepo);
        personRepository = Objects.requireNonNull(personRepo);

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
        final Optional<Donation>    donation;
        final Book                  created;

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
        toCreate = Book.builder()
            .withNumber(number)
            .withAuthors(authors)
            .withPublishers(publishers)
            .withBookType(book.bookType())
            .withGameSystem(book.gameSystem())
            .withDonation(donation)
            .withIsbn(book.isbn())
            .withLanguage(book.language())
            .withPublishDate(book.publishDate())
            .withTitle(book.title())
            .withLendings(List.of())
            .withLent(false)
            .build();

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
    public final Iterable<Book> getAll(final Pageable pageable) {
        final Iterable<Book> books;

        log.debug("Reading books with pagination {}", pageable);

        books = bookRepository.findAll(pageable);

        log.debug("Read books with pagination {}", pageable);

        return books;
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

        log.debug("Read book {}", number);

        return book;
    }

    @Override
    public final Book update(final long number, final Book book) {
        final Book                  toUpdate;
        final Collection<Author>    authors;
        final Collection<Publisher> publishers;
        final Collection<Donor>     donors;
        final Optional<Donation>    donation;
        final Book                  updated;

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
        toUpdate = Book.builder()
            .withNumber(number)
            .withAuthors(authors)
            .withPublishers(publishers)
            .withBookType(book.bookType())
            .withGameSystem(book.gameSystem())
            .withDonation(donation)
            .withIsbn(book.isbn())
            .withLanguage(book.language())
            .withPublishDate(book.publishDate())
            .withTitle(book.title())
            .withLendings(List.of())
            .withLent(false)
            .build();

        updateBookValidator.validate(toUpdate);

        updated = bookRepository.save(toUpdate);

        log.debug("Updated book with number {} using data {}", number, book);

        return updated;
    }

    private final void validateRelationships(final Book book) {
        final Optional<GameSystem> gameSystem;
        final Optional<BookType>   bookType;
        final Optional<Author>     invalidAuthor;
        final Optional<Publisher>  invalidPublisher;
        final Optional<Donor>      invalidDonor;

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

        // Check game system exist
        gameSystem = book.gameSystem();
        if (gameSystem.isPresent() && !gameSystemRepository.exists(gameSystem.get()
            .number())) {
            log.error("Missing game system {}", gameSystem.get()
                .number());
            throw new MissingGameSystemException(gameSystem.get()
                .number());
        }

        // Check book type exist
        bookType = book.bookType();
        if (bookType.isPresent() && !bookTypeRepository.exists(bookType.get()
            .number())) {
            log.error("Missing book type {}", bookType.get()
                .number());
            throw new MissingBookTypeException(bookType.get()
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
