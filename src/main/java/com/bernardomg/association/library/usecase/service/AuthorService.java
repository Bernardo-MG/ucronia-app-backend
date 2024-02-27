
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.Author;

public interface AuthorService {

    public Author createAuthor(final Author author);

    public void deleteAuthor(final String name);

    public Iterable<Author> getAllAuthors(final Pageable pageable);

    public Optional<Author> getOneAuthor(final String name);

}
