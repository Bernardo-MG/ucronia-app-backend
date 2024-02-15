
package com.bernardomg.association.library.usecase.service;

import com.bernardomg.association.library.domain.exception.MissingAuthorException;
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
        return authorRepository.save(author);
    }

    @Override
    public final Book createBook(final Book book) {
        final boolean gameSystemExists;
        final boolean bookTypeExists;

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
        return bookTypeRepository.save(type);
    }

    @Override
    public final GameSystem createGameSystem(final GameSystem system) {
        return gameSystemRepository.save(system);
    }

}
