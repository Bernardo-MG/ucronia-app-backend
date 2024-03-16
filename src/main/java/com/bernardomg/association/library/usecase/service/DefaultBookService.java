
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.domain.exception.MissingBookException;
import com.bernardomg.association.library.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.usecase.validation.CreateBookValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class DefaultBookService implements BookService {

    private final AuthorRepository     authorRepository;

    private final BookRepository       bookRepository;

    private final BookTypeRepository   bookTypeRepository;

    private final GameSystemRepository gameSystemRepository;
    
    private final CreateBookValidator createBookValidator = new CreateBookValidator();

    public DefaultBookService(final AuthorRepository authorRepo, final BookRepository bookRepo,
            final BookTypeRepository bookTypeRepo, final GameSystemRepository gameSystemRepo) {
        super();

        authorRepository = authorRepo;
        bookRepository = bookRepo;
        bookTypeRepository = bookTypeRepo;
        gameSystemRepository = gameSystemRepo;
    }

    @Override
    public final Book create(final Book book) {
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

        if (book.getGameSystem().getName() != null) {
            gameSystemExists = gameSystemRepository.exists(book.getGameSystem()
                .getName());
            if (!gameSystemExists) {
                throw new MissingGameSystemException(book.getGameSystem()
                    .getName());
            }
        }
        if (book.getBookType().getName() != null) {
            bookTypeExists = bookTypeRepository.exists(book.getBookType()
                .getName());
            if (!bookTypeExists) {
                throw new MissingBookTypeException(book.getBookType()
                    .getName());
            }
        }
        
        createBookValidator.validate(book);

        return bookRepository.save(book);
    }

    @Override
    public final void delete(final String isbn) {

        log.debug("Deleting book {}", isbn);

        if (!bookRepository.exists(isbn)) {
            throw new MissingBookException(isbn);
        }

        bookRepository.delete(isbn);
    }

    @Override
    public final Iterable<Book> getAll(final Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public final Optional<Book> getOne(final String isbn) {
        final Optional<Book> book;

        log.debug("Reading book {}", isbn);

        book = bookRepository.findOne(isbn);
        if (book.isEmpty()) {
            throw new MissingBookException(isbn);
        }

        return book;
    }

}
