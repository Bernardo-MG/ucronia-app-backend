
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.domain.exception.MissingBookException;
import com.bernardomg.association.library.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultBookService implements BookService {

    private final AuthorRepository     authorRepository;

    private final BookRepository       bookRepository;

    private final BookTypeRepository   bookTypeRepository;

    private final GameSystemRepository gameSystemRepository;

    public DefaultBookService(final AuthorRepository authorRepo, final BookRepository bookRepo,
            final BookTypeRepository bookTypeRepo, final GameSystemRepository gameSystemRepo) {
        super();

        authorRepository = authorRepo;
        bookRepository = bookRepo;
        bookTypeRepository = bookTypeRepo;
        gameSystemRepository = gameSystemRepo;
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
    public final void deleteBook(final String isbn) {

        log.debug("Deleting book {}", isbn);

        if (!bookRepository.exists(isbn)) {
            throw new MissingBookException(isbn);
        }

        bookRepository.delete(isbn);
    }

    @Override
    public final Iterable<Book> getAllBooks(final Pageable pageable) {
        return bookRepository.findAll(pageable);
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

}
