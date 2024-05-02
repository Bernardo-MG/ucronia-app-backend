
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.inventory.domain.exception.MissingDonorException;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.library.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.domain.exception.MissingBookException;
import com.bernardomg.association.library.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.domain.exception.MissingPublisherException;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.domain.repository.PublisherRepository;
import com.bernardomg.association.library.usecase.validation.CreateBookValidator;
import com.bernardomg.association.library.usecase.validation.UpdateBookValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class DefaultBookService implements BookService {

    private final AuthorRepository     authorRepository;

    private final BookRepository       bookRepository;

    private final BookTypeRepository   bookTypeRepository;

    private final CreateBookValidator  createBookValidator;

    private final DonorRepository      donorRepository;

    private final GameSystemRepository gameSystemRepository;

    private final PublisherRepository  publisherRepository;

    private final UpdateBookValidator  updateBookValidator;

    public DefaultBookService(final BookRepository bookRepo, final AuthorRepository authorRepo,
            final PublisherRepository publisherRepo, final BookTypeRepository bookTypeRepo,
            final GameSystemRepository gameSystemRepo, final DonorRepository donorRepo) {
        super();

        bookRepository = bookRepo;
        authorRepository = authorRepo;
        publisherRepository = publisherRepo;
        bookTypeRepository = bookTypeRepo;
        gameSystemRepository = gameSystemRepo;
        donorRepository = donorRepo;

        createBookValidator = new CreateBookValidator(bookRepository);
        updateBookValidator = new UpdateBookValidator(bookRepository);
    }

    @Override
    public final Book create(final Book book) {
        final Book toCreate;
        final Long number;

        log.debug("Creating book {}", book);

        // TODO: verify the language is a valid code

        validateRelationships(book);

        // Get number
        number = bookRepository.findNextNumber();

        toCreate = Book.builder()
            .withNumber(number)
            .withAuthors(book.getAuthors())
            .withPublisher(book.getPublisher())
            .withBookType(book.getBookType())
            .withGameSystem(book.getGameSystem())
            .withDonor(book.getDonor())
            .withIsbn(book.getIsbn())
            .withLanguage(book.getLanguage())
            .withTitle(book.getTitle())
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
        return bookRepository.getAll(pageable);
    }

    @Override
    public final Optional<Book> getOne(final long number) {
        final Optional<Book> book;

        log.debug("Reading book {}", number);

        book = bookRepository.getOne(number);
        if (book.isEmpty()) {
            throw new MissingBookException(number);
        }

        return book;
    }

    @Override
    public final Book update(final long number, final Book book) {
        log.debug("Updating book with number {} using data {}", number, book);

        // TODO: verify the language is a valid code
        // TODO: validate isbn

        // Check book exists
        if (!bookRepository.exists(number)) {
            throw new MissingBookException(number);
        }

        validateRelationships(book);

        updateBookValidator.validate(book);

        return bookRepository.save(book);
    }

    private final void validateRelationships(final Book book) {
        final boolean publisherExists;
        final boolean gameSystemExists;
        final boolean bookTypeExists;
        final boolean donorExists;

        // Check authors exist
        book.getAuthors()
            .forEach(a -> {
                final boolean authorExists;

                authorExists = authorRepository.exists(a.getName());
                if (!authorExists) {
                    throw new MissingAuthorException(a.getName());
                }
            });

        // Check publisher exist
        if (StringUtils.isNotBlank(book.getPublisher()
            .getName())) {
            publisherExists = publisherRepository.exists(book.getPublisher()
                .getName());
            if (!publisherExists) {
                throw new MissingPublisherException(book.getPublisher()
                    .getName());
            }
        }

        // Check game system exist
        if (StringUtils.isNotBlank(book.getGameSystem()
            .getName())) {
            gameSystemExists = gameSystemRepository.exists(book.getGameSystem()
                .getName());
            if (!gameSystemExists) {
                throw new MissingGameSystemException(book.getGameSystem()
                    .getName());
            }
        }

        // Check book type exist
        if (StringUtils.isNotBlank(book.getBookType()
            .getName())) {
            bookTypeExists = bookTypeRepository.exists(book.getBookType()
                .getName());
            if (!bookTypeExists) {
                throw new MissingBookTypeException(book.getBookType()
                    .getName());
            }
        }

        // Check donor exist
        if (book.getDonor()
            .getNumber() >= 0) {
            donorExists = donorRepository.exists(book.getDonor()
                .getNumber());
            if (!donorExists) {
                throw new MissingDonorException(book.getDonor()
                    .getNumber());
            }
        }

    }

}
