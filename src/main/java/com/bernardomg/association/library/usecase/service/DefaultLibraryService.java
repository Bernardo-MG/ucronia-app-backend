
package com.bernardomg.association.library.usecase.service;

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

    private final GameSystemRepository systemRepository;

    public DefaultLibraryService(final AuthorRepository authorRepo, final BookRepository bookRepo,
            final BookTypeRepository bookTypeRepo, final GameSystemRepository systemRepo) {
        super();

        authorRepository = authorRepo;
        bookRepository = bookRepo;
        bookTypeRepository = bookTypeRepo;
        systemRepository = systemRepo;
    }

    @Override
    public final Author createAuthor(final Author author) {
        return authorRepository.save(author);
    }

    @Override
    public final Book createBook(final Book book) {
        return bookRepository.save(book);
    }

    @Override
    public final BookType createBookType(final BookType type) {
        return bookTypeRepository.save(type);
    }

    @Override
    public final GameSystem createGameSystem(final GameSystem system) {
        return systemRepository.save(system);
    }

}
