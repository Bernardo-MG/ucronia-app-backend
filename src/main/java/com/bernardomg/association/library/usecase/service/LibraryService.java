
package com.bernardomg.association.library.usecase.service;

import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.model.BookType;

public interface LibraryService {

    public Author createAuthor(final Author author);

    public Book createBook(final Book book);

    public BookType createBookType(final BookType type);

    public System createSystem(final System system);

    public Author updateAuthor(final Author author);

    public Book updateBook(final Book book);

    public BookType updateBookType(final BookType type);

    public System updateSystem(final System system);

}
