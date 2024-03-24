
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class DefaultBookService implements BookService {

    private final AuthorRepository     authorRepository;

    private final BookRepository       bookRepository;

    private final BookTypeRepository   bookTypeRepository;

    private final CreateBookValidator  createBookValidator;

    private final GameSystemRepository gameSystemRepository;

    private final PublisherRepository  publisherRepository;

    public DefaultBookService(final BookRepository bookRepo, final AuthorRepository authorRepo,
            final PublisherRepository publisherRepo, final BookTypeRepository bookTypeRepo,
            final GameSystemRepository gameSystemRepo) {
        super();

        bookRepository = bookRepo;
        authorRepository = authorRepo;
        publisherRepository = publisherRepo;
        bookTypeRepository = bookTypeRepo;
        gameSystemRepository = gameSystemRepo;

        createBookValidator = new CreateBookValidator(bookRepository);
    }

    @Override
    public final Book create(final Book book) {
        final boolean publisherExists;
        final boolean gameSystemExists;
        final boolean bookTypeExists;
        final Book    toCreate;
        final Long    number;

        log.debug("Creating book {}", book);

        // TODO: verify the language is a valid code

        book.getAuthors()
            .forEach(a -> {
                final boolean authorExists;

                authorExists = authorRepository.exists(a.getName());
                if (!authorExists) {
                    throw new MissingAuthorException(a.getName());
                }
            });

        if (StringUtils.isNotBlank(book.getPublisher()
            .getName())) {
            publisherExists = publisherRepository.exists(book.getPublisher()
                .getName());
            if (!publisherExists) {
                throw new MissingPublisherException(book.getPublisher()
                    .getName());
            }
        }

        if (StringUtils.isNotBlank(book.getGameSystem()
            .getName())) {
            gameSystemExists = gameSystemRepository.exists(book.getGameSystem()
                .getName());
            if (!gameSystemExists) {
                throw new MissingGameSystemException(book.getGameSystem()
                    .getName());
            }
        }

        if (StringUtils.isNotBlank(book.getBookType()
            .getName())) {
            bookTypeExists = bookTypeRepository.exists(book.getBookType()
                .getName());
            if (!bookTypeExists) {
                throw new MissingBookTypeException(book.getBookType()
                    .getName());
            }
        }

        toCreate = Book.builder()
            .withAuthors(book.getAuthors())
            .withPublisher(book.getPublisher())
            .withBookType(book.getBookType())
            .withGameSystem(book.getGameSystem())
            .withIsbn(book.getIsbn())
            .withLanguage(book.getLanguage())
            .withTitle(book.getTitle())
            .build();

        // Set number
        number = bookRepository.findNextNumber();
        toCreate.setNumber(number);

        createBookValidator.validate(toCreate);

        return bookRepository.save(toCreate);
    }

    @Override
    public final void delete(final long index) {

        log.debug("Deleting book {}", index);

        if (!bookRepository.exists(index)) {
            throw new MissingBookException(index);
        }

        bookRepository.delete(index);
    }

    @Override
    public final Iterable<Book> getAll(final Pageable pageable) {
        return bookRepository.getAll(pageable);
    }

    @Override
    public final Optional<Book> getOne(final long index) {
        final Optional<Book> book;

        log.debug("Reading book {}", index);

        book = bookRepository.getOne(index);
        if (book.isEmpty()) {
            throw new MissingBookException(index);
        }

        return book;
    }

}
