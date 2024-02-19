
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.domain.exception.MissingBookException;
import com.bernardomg.association.library.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultLibraryService implements LibraryService {

    private final AuthorRepository     authorRepository;

    private final BookRepository       bookRepository;

    private final BookTypeRepository   bookTypeRepository;

    private final GameSystemRepository gameSystemRepository;

    public DefaultLibraryService(final AuthorRepository authorRepo, final BookRepository bookRepo,
            final BookTypeRepository bookTypeRepo, final GameSystemRepository gameSystemRepo) {
        super();

        authorRepository = authorRepo;
        bookRepository = bookRepo;
        bookTypeRepository = bookTypeRepo;
        gameSystemRepository = gameSystemRepo;
    }

    @Override
    public final Author createAuthor(final Author author) {
        log.debug("Creating author {}", author);

        return authorRepository.save(author);
    }

    @Override
    public final Book createBook(final Book book) {
        final boolean gameSystemExists;
        final boolean bookTypeExists;

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

        gameSystemExists = gameSystemRepository.exists(book.getGameSystem()
            .getName());
        if (!gameSystemExists) {
            throw new MissingGameSystemException(book.getGameSystem()
                .getName());
        }

        bookTypeExists = bookTypeRepository.exists(book.getBookType()
            .getName());
        if (!bookTypeExists) {
            throw new MissingBookTypeException(book.getBookType()
                .getName());
        }

        return bookRepository.save(book);
    }

    @Override
    public final BookType createBookType(final BookType type) {
        log.debug("Creating book type {}", type);

        return bookTypeRepository.save(type);
    }

    @Override
    public final GameSystem createGameSystem(final GameSystem system) {
        log.debug("Creating game system {}", system);

        return gameSystemRepository.save(system);
    }

    @Override
    public final void deleteAuthor(final String name) {

        log.debug("Deleting author {}", name);

        if (!authorRepository.exists(name)) {
            throw new MissingAuthorException(name);
        }

        authorRepository.delete(name);
    }

    @Override
    public final void deleteBook(final String isbn) {

        log.debug("Deleting book {}", isbn);

        if (!bookRepository.exists(isbn)) {
            throw new MissingBookException(isbn);
        }

        bookRepository.delete(isbn);
    }

    @Override
    public final void deleteBookType(final String name) {

        log.debug("Deleting book type {}", name);

        if (!bookTypeRepository.exists(name)) {
            throw new MissingBookTypeException(name);
        }

        bookTypeRepository.delete(name);
    }

    @Override
    public final void deleteGameSystem(final String name) {

        log.debug("Deleting game system {}", name);

        if (!gameSystemRepository.exists(name)) {
            throw new MissingGameSystemException(name);
        }

        gameSystemRepository.delete(name);
    }

    @Override
    public final Iterable<Author> getAllAuthors(final Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public final Iterable<Book> getAllBooks(final Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public final Iterable<BookType> getAllBookTypes(final Pageable pageable) {
        return bookTypeRepository.findAll(pageable);
    }

    @Override
    public final Iterable<GameSystem> getAllGameSystems(final Pageable pageable) {
        return gameSystemRepository.findAll(pageable);
    }

    @Override
    public final Optional<Author> getOneAuthor(final String name) {
        final Optional<Author> author;

        log.debug("Reading author {}", name);

        author = authorRepository.findOne(name);
        if (author.isEmpty()) {
            throw new MissingAuthorException(name);
        }

        return author;
    }

    @Override
    public final Optional<Book> getOneBook(final String isbn) {
        final Optional<Book> book;

        log.debug("Reading book {}", isbn);

        book = bookRepository.findOne(isbn);
        if (book.isEmpty()) {
            throw new MissingBookException(isbn);
        }

        return book;
    }

    @Override
    public final Optional<BookType> getOneBookType(final String name) {
        final Optional<BookType> bookType;

        log.debug("Reading book type {}", name);

        bookType = bookTypeRepository.findOne(name);
        if (bookType.isEmpty()) {
            throw new MissingBookTypeException(name);
        }

        return bookType;
    }

    @Override
    public final Optional<GameSystem> getOneGameSystem(final String name) {
        final Optional<GameSystem> gameSystem;

        log.debug("Reading game system {}", name);

        gameSystem = gameSystemRepository.findOne(name);
        if (gameSystem.isEmpty()) {
            throw new MissingGameSystemException(name);
        }

        return gameSystem;
    }

}
