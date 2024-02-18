
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.model.GameSystem;

public interface LibraryService {

    public Author createAuthor(final Author author);

    public Book createBook(final Book book);

    public BookType createBookType(final BookType type);

    public GameSystem createGameSystem(final GameSystem system);

    public void deleteAuthor(final String name);

    public void deleteBook(final String isbn);

    public void deleteBookType(final String name);

    public void deleteGameSystem(final String name);

    public Iterable<Author> getAllAuthors(final Pageable pageable);

    public Iterable<Book> getAllBooks(final Pageable pageable);

    public Iterable<BookType> getAllBookTypes(final Pageable pageable);

    public Iterable<GameSystem> getAllGameSystems(final Pageable pageable);

    public Optional<Author> getOneAuthor(final String name);

    public Optional<Book> getOneBook(final String isbn);

    public Optional<BookType> getOneBookType(final String name);

    public Optional<GameSystem> getOneGameSystem(final String name);

}
